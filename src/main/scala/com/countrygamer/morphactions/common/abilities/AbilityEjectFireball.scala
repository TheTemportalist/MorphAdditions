package com.countrygamer.morphactions.common.abilities

import com.countrygamer.morphactions.api.AbilityAction
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer

/**
 *
 *
 * @author CountryGamer
 */
abstract class AbilityEjectFireball() extends AbilityEject() {

	private var acceleration: Float = 0.0F

	override def getEntity(player: EntityPlayer): Entity = {
		val entity: Entity = super.getEntity(player)


		entity
	}

	override def parse(args: Array[String]): AbilityAction = {
		super.parse(args)

	}

}
