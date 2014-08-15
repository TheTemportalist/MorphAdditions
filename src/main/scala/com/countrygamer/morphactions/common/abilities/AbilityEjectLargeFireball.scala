package com.countrygamer.morphactions.common.abilities

import com.countrygamer.morphactions.api.AbilityAction
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.projectile.EntityLargeFireball

/**
 *
 *
 * @author CountryGamer
 */
class AbilityEjectLargeFireball() extends AbilityEject() {

	private var explosionRadius: Int = 0

	override def trigger(player: EntityPlayer): Unit = {
		/*
		val largeFireball: EntityLargeFireball = this.getEntity(player.worldObj)
				.asInstanceOf[EntityLargeFireball]
		largeFireball.shootingEntity = player
		double d5 = this.targetedEntity.posX - this.posX;
		double d6 = this.targetedEntity.boundingBox.minY + (double)(this.targetedEntity.height / 2.0F) - (this.posY + (double)(this.height / 2.0F));
		double d7 = this.targetedEntity.posZ - this.posZ;

		val x: Double = player.posX
		val y: Double =
		val z: Double = player.posZ

		largeFireball.field_92057_e = this.explosionRadius
		*/

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
		new AbilityEjectLargeFireball().parse(this.getArgs)
	}

}
