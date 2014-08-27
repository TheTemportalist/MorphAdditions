package com.countrygamer.morphadditions.common

import java.util

import com.countrygamer.cgo.common.lib.LogHelper
import com.countrygamer.cgo.wrapper.common.extended.ExtendedEntity
import com.countrygamer.morphadditions.api.{AbilityAction, MorphActionEvent}
import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.relauncher.Side
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.{NBTTagCompound, NBTTagList}
import net.minecraftforge.common.MinecraftForge

/**
 *
 *
 * @author CountryGamer
 */
class MorphedPlayer(player: EntityPlayer) extends ExtendedEntity(player) {

	private val data: util.HashMap[String, NBTTagCompound] = new
					util.HashMap[String, NBTTagCompound]()
	private var cooldownTicks: Int = 0

	def updateTag(key: String, newTag: NBTTagCompound): Unit = {
		this.data.put(key, newTag)
		this.syncEntity()
	}

	def hasTag(key: String): Boolean = {
		this.data.containsKey(key)
	}

	def getTag(key: String): NBTTagCompound = {
		if (this.hasTag(key))
			this.data.get(key)
		else
			new NBTTagCompound()
	}

	def trigger(entity: EntityLivingBase, ability: AbilityAction): Unit = {
		if (this.canTrigger() && !MinecraftForge.EVENT_BUS
				.post(new MorphActionEvent(this.player, entity, ability))) {
			//ability.copy().trigger(this.player)

			this.cooldownTicks = 20 * 60 //ability.getCooldown
			this.syncEntity()

		}
	}

	def canTrigger(): Boolean = {
		this.player.capabilities.isCreativeMode || this.cooldownTicks < 0
	}

	def tick(): Unit = {
		if (this.cooldownTicks >= 0) {
			this.cooldownTicks = this.cooldownTicks - 1
			this.printCooldown("")
			//this.syncEntity()
		}
	}

	def getCooldown(): Int = {
		this.cooldownTicks
	}

	def getCooldownTime(): String = {
		//this.printCooldown("")
		val totalSeconds: Int = this.cooldownTicks / 20
		val ticks: Int = this.cooldownTicks % 20
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

	def clearCooldown(): Unit = {
		this.cooldownTicks = -1
		this.syncEntity()
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
		tagCom.setInteger("cool_down", this.cooldownTicks)

	}

	override def loadNBTData(tagCom: NBTTagCompound): Unit = {
		val morphedTagList: NBTTagList = tagCom.getTagList("morphedPlayerTag", 10)
		this.data.clear()
		for (i <- 0 until morphedTagList.tagCount()) {
			val tag: NBTTagCompound = morphedTagList.getCompoundTagAt(i)
			this.data.put(tag.getString("key"), tag.getCompoundTag("value"))
		}

		this.cooldownTicks = tagCom.getInteger("cool_down")
		//this.printCooldown("loaded ticks")

	}

	def getSide(): Side = {
		FMLCommonHandler.instance().getEffectiveSide
	}

	def printCooldown(message: String): Unit = {
		LogHelper.info(MorphAdditions.pluginName,
			this.getSide() + " " + message + " " + this.cooldownTicks)
	}

}
