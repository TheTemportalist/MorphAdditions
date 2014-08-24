package com.countrygamer.morphadditions.common

import java.util

import com.countrygamer.cgo.wrapper.common.extended.ExtendedEntity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.{NBTTagCompound, NBTTagList}

/**
 *
 *
 * @author CountryGamer
 */
class MorphedPlayer(player: EntityPlayer) extends ExtendedEntity(player) {

	private val data: util.HashMap[String, NBTTagCompound] = new
					util.HashMap[String, NBTTagCompound]()

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
	}

	override def loadNBTData(tagCom: NBTTagCompound): Unit = {
		val morphedTagList: NBTTagList = tagCom.getTagList("morphedPlayerTag", 10)
		this.data.clear()
		for (i <- 0 until morphedTagList.tagCount()) {
			val tag: NBTTagCompound = morphedTagList.getCompoundTagAt(i)
			this.data.put(tag.getString("key"), tag.getCompoundTag("value"))
		}
	}

}
