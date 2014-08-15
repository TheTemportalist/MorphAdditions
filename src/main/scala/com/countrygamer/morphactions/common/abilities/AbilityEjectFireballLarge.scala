package com.countrygamer.morphactions.common.abilities

import com.countrygamer.morphactions.api.AbilityAction
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.projectile.EntityLargeFireball

/**
 *
 *
 * @author CountryGamer
 */
class AbilityEjectFireballLarge() extends AbilityEjectFireball() {

	private var explosionRadius: Int = 0

	override def getEntity(player: EntityPlayer): Entity = {
		val entity: Entity = super.getEntity(player)

		entity match {
			case large: EntityLargeFireball =>
				large.field_92057_e = this.explosionRadius

			case _ =>
		}

		entity
	}

	override def parse(args: Array[String]): AbilityAction = {
		super.parse(args)

		if (this.entityClass.isAssignableFrom(classOf[EntityLargeFireball])) {
			try {
				this.explosionRadius = Integer.parseInt(args(1))
			}
			catch {
				case e: NumberFormatException =>
					e.printStackTrace()
			}
		}

		this
	}

	override def copy(): AbilityAction = {
		new AbilityEjectFireballLarge().parse(this.getArgs)
	}

}
