package com.temportalist.morphadditions.common

import java.util

import com.temportalist.morphadditions.api.{AbilityAction, MorphActionEvent}
import com.temportalist.origin.api.common.utility.WorldHelper
import com.temportalist.origin.foundation.common.extended.ExtendedEntity
import com.temportalist.origin.foundation.common.network.PacketExtendedSync
import cpw.mods.fml.relauncher.Side
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.{NBTTagCompound, NBTTagList}
import net.minecraftforge.common.MinecraftForge

/**
 *
 *
 * @author TheTemportalist
 */
class MorphedPlayer(player: EntityPlayer) extends ExtendedEntity(player) {

	private val data: util.HashMap[String, NBTTagCompound] =
		new util.HashMap[String, NBTTagCompound]()
	private var totalCoolDownTicks: Int = 0
	private var coolDownTicks: Int = 0

	def updateTag(key: String, newTag: NBTTagCompound): Unit = {
		this.data.put(key, newTag)
		this.syncEntityFull()
	}

	def hasTag(key: String): Boolean = {
		this.data.containsKey(key)
	}

	def getTag(key: String): NBTTagCompound = {
		if (this.hasTag(key)) this.data.get(key)
		else new NBTTagCompound()
	}

	def trigger(entity: EntityLivingBase, ability: AbilityAction): Unit = {
		//MorphAdditions.log("trigger: Ticks at " + this.cooldownTicks)
		if (WorldHelper.isServer && this.canTrigger && !MinecraftForge.EVENT_BUS.post(
					new MorphActionEvent(this.player, entity, ability))) {
			ability.trigger(this.player)
			this.setCoolDownTicks(ability.getCoolDown)
		}
	}

	def syncCoolDown(): Unit = {
		//this.syncEntity("cooldown", this.cooldownTicks)
		new PacketExtendedSync(this.getClass, "cooldown").
				add(this.coolDownTicks, this.totalCoolDownTicks).sendToBoth()
	}

	def canTrigger: Boolean = {
		this.player.capabilities.isCreativeMode || this.coolDownTicks < 0
	}

	def tick(): Unit = {
		//MorphAdditions.log("tick: Ticks at " + this.cooldownTicks)
		if (this.coolDownTicks >= 0) {
			this.coolDownTicks = if (this.player.capabilities.isCreativeMode) -1
			else this.coolDownTicks - 1
			//MorphAdditions.log("tick: Ticks set to " + this.cooldownTicks)
			this.syncCoolDown()
		}
	}

	def getCoolDown: Int = {
		this.coolDownTicks
	}

	def getCoolDownTime: String = {
		//this.printCooldown("")
		val totalSeconds: Int = this.coolDownTicks / 20
		val ticks: Int = this.coolDownTicks % 20
		val minutes: Int = totalSeconds / 60
		val seconds: Int = totalSeconds % 60

		var seconds_str: String = seconds + ""
		while (seconds_str.length < 2) {
			seconds_str = "0" + seconds_str
		}
		var ticks_str: String = ticks + ""
		while (ticks_str.length < 2) {
			ticks_str = "0" + ticks_str
		}

		//this.printCooldown(minutes + ":" + seconds_str + ":" + ticks_str)

		minutes + ":" + seconds_str + ":" + ticks_str
	}

	def getTotalCoolDownTicks: Int = this.totalCoolDownTicks

	def clearCoolDown(): Unit = this.setCoolDownTicks(-1)

	def setCoolDownTicks(amt: Int): Unit = {
		this.coolDownTicks = amt
		this.totalCoolDownTicks = amt
		this.syncCoolDown()
	}

	override def saveNBTData(tagCom: NBTTagCompound): Unit = {
		val morphedTagList: NBTTagList = new NBTTagList()
		val iterator: util.Iterator[String] = this.data.keySet().iterator()
		while (iterator.hasNext) {
			val key: String = iterator.next()
			val tag: NBTTagCompound = new NBTTagCompound
			tag.setString("key", key)
			tag.setTag("value", this.getTag(key))
			morphedTagList.appendTag(tag)
		}
		tagCom.setTag("morphedPlayerTag", morphedTagList)

		//this.printCooldown("saving ticks")
		tagCom.setInteger("cool_down", this.coolDownTicks)
		tagCom.setInteger("cool_down_total", this.totalCoolDownTicks)

	}

	override def loadNBTData(tagCom: NBTTagCompound): Unit = {
		val morphedTagList: NBTTagList = tagCom.getTagList("morphedPlayerTag", 10)
		this.data.clear()
		for (i <- 0 until morphedTagList.tagCount()) {
			val tag: NBTTagCompound = morphedTagList.getCompoundTagAt(i)
			this.data.put(tag.getString("key"), tag.getCompoundTag("value"))
		}

		this.coolDownTicks = tagCom.getInteger("cool_down")
		this.totalCoolDownTicks = tagCom.getInteger("cool_down_total")
		//this.printCooldown("loaded ticks")

	}

	override def handleSyncPacketData(uniqueIdentifier: String, packet: PacketExtendedSync,
			side: Side): Unit = {
		uniqueIdentifier match {
			case "cooldown" =>
				val passedTicks = packet.get[Int]
				this.coolDownTicks = passedTicks
				this.totalCoolDownTicks = packet.get[Int]
			case _ =>
		}
	}
}
