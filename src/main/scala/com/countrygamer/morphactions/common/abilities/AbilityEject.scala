package com.countrygamer.morphactions.common.abilities

import com.countrygamer.morphactions.api.AbilityAction
import net.minecraft.entity.Entity
import net.minecraft.world.World

/**
 *
 *
 * @author CountryGamer
 */
abstract class AbilityEject() extends AbilityAction() {

	protected var entityClass: Class[_ <: Entity] = null

	def getEntity(world: World): Entity = {
		this.entityClass.getConstructor(classOf[World]).newInstance(world)
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
