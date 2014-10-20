package com.temportalist.morphadditions.common.abilities

import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.projectile.EntityFireball
import net.minecraft.util.MathHelper

/**
 *
 *
 * @author TheTemportalist
 */
abstract class AbilityEjectFireball() extends AbilityEject() {

	override def getEntity(player: EntityPlayer): Entity = {
		val entity: EntityFireball = super.getEntity(player).asInstanceOf[EntityFireball]

		val targetCoords: Array[Double] = this.getTargetCoords(player)

		if (targetCoords == null)
			return null

		val x: Double = targetCoords(0)
		val y: Double = targetCoords(1)
		val z: Double = targetCoords(2)
		val entityHeight: Double = targetCoords(3)

		entity.shootingEntity = player
		entity.setLocationAndAngles(player.posX, player.posY, player.posZ, player.rotationYaw,
			player.rotationPitch)
		entity.setPosition(entity.posX, entity.posY, entity.posZ)
		entity.yOffset = 0.0F
		entity.motionX = 0.0D
		entity.motionY = 0.0D
		entity.motionZ = 0.0D
		var difX: Double = x - player.posX
		var difY: Double = y + (entityHeight / 2.0F) - (player.posY + (player.height / 2.0F))
		var difZ: Double = z - player.posZ
		difX = difX + player.worldObj.rand.nextGaussian() * 0.4D
		difY = difY + player.worldObj.rand.nextGaussian() * 0.4D
		difZ = difZ + player.worldObj.rand.nextGaussian() * 0.4D
		val d3: Double = MathHelper.sqrt_double(difX * difX + difY * difY + difZ * difZ)
				.asInstanceOf[Double]
		entity.accelerationX = difX / d3 * 0.1D
		entity.accelerationY = difY / d3 * 0.1D
		entity.accelerationZ = difZ / d3 * 0.1D

		entity
	}

}
