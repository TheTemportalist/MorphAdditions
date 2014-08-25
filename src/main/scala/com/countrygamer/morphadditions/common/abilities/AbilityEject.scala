package com.countrygamer.morphadditions.common.abilities

import java.lang.reflect.InvocationTargetException

import com.countrygamer.cgo.common.lib.LogHelper
import com.countrygamer.morphadditions.api.AbilityAction
import com.countrygamer.morphadditions.common.MorphAdditions
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World

/**
 *
 *
 * @author CountryGamer
 */
abstract class AbilityEject() extends AbilityAction() {

	def getEntityClass(): Class[_ <: Entity]

	def getEntity(player: EntityPlayer): Entity = {
		var entity: Entity = null
		LogHelper.info(MorphAdditions.pluginName, this.getEntityClass().getCanonicalName)
		try {
			entity = this.getEntityClass().getConstructor(classOf[World])
					.newInstance(player.worldObj)
		}
		catch {
			case e: NoSuchMethodException =>
				e.printStackTrace()
			case e: SecurityException =>
				e.printStackTrace()
			case e: InstantiationException =>
				e.printStackTrace()
			case e: IllegalAccessException =>
				e.printStackTrace()
			case e: IllegalArgumentException =>
				e.printStackTrace()
			case e: InvocationTargetException =>
				e.printStackTrace()
		}
		entity
	}

	override def trigger(player: EntityPlayer): Unit = {
		val entity: Entity = this.getEntity(player)
		if (entity != null && !player.worldObj.isRemote)
			player.worldObj.spawnEntityInWorld(entity)
	}

}
