package com.countrygamer.morphactions.common.abilities

import com.countrygamer.morphactions.api.AbilityAction
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer

/**
 *
 *
 * @author CountryGamer
 */
class AbilityEjectFireball() extends AbilityEject() {

	private var acceleration: Float = 0.0F

	override def getEntity(player: EntityPlayer): Entity = {
		val entity: Entity = super.getEntity(player)


		entity
	}

	override def parse(args: Array[String]): AbilityAction = {
		if (args(0).equals("small")) {
			//val ability: AbilityEjectFireball = new AbilityEjectFireballSmall()
			//ability.parse(args)
			this
		}
		else if (args(0).equals("large")) {
			super.parse(Array[String]("net.minecraft.entity.projectile.EntityLargeFireball"))
			val ability: AbilityEjectFireballLarge = new AbilityEjectFireballLarge()
			ability.entityClass = this.entityClass
			ability.setArgs(args)
			ability.parse(args)
		}
		else {
			super.parse(args)
		}
	}

	override def copy(): AbilityAction = {
		new AbilityEjectFireball().parse(this.getArgs)
	}

}
