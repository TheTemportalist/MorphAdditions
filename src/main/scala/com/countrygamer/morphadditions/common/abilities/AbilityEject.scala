package com.countrygamer.morphadditions.common.abilities

import com.countrygamer.morphadditions.api.AbilityAction
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World

/**
 *
 *
 * @author CountryGamer
 */
abstract class AbilityEject() extends AbilityAction() {

	protected var entityClass: Class[_ <: Entity] = null

	def getEntity(player: EntityPlayer): Entity = {
		this.entityClass.getConstructor(classOf[World]).newInstance(player.worldObj)
	}

	override def trigger(player: EntityPlayer): Unit = {
		this.getEntity(player)
		//player.worldObj.spawnEntityInWorld(this.getEntity(player))
	}

	override def parse(args: Array[String]): AbilityAction = {
		try {
			this.entityClass = Class.forName(args(0)).asInstanceOf[Class[_ <: Entity]]
		}
		catch {
			case e: ClassNotFoundException =>
				e.printStackTrace()
		}

		this
	}

}
