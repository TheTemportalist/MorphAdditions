package com.countrygamer.morphadditions.common.abilities

import com.countrygamer.morphadditions.api.AbilityAction
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.projectile.EntityLargeFireball
import net.minecraft.util.Vec3

/**
 *
 *
 * @author CountryGamer
 */
class AbilityEjectFireballLarge() extends AbilityEjectFireball() {

	var explosionRadius: Int = 1

	override def getEntityClass(): Class[_ <: Entity] = {
		classOf[EntityLargeFireball]
	}

	override def getEntity(player: EntityPlayer): Entity = {
		val entity: EntityLargeFireball = super.getEntity(player).asInstanceOf[EntityLargeFireball]

		entity.field_92057_e = this.explosionRadius
		val d8: Double = 4.0D
		val vec3: Vec3 = player.getLook(1.0F)
		entity.posX = player.posX + vec3.xCoord * d8
		entity.posY = player.posY + (player.height / 2.0F).asInstanceOf[Double] + 0.5D
		entity.posZ = player.posZ + vec3.zCoord * d8

		entity
	}

	override def parse(args: Array[String]): AbilityAction = {
		super.parse(args)

		try {
			this.explosionRadius = args(1).toInt
		}
		catch {
			case e: NumberFormatException =>
				e.printStackTrace()
		}

		this
	}
}
