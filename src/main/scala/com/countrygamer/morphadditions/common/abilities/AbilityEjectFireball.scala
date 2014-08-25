package com.countrygamer.morphadditions.common.abilities

import com.countrygamer.cgo.common.lib.util.Cursor
import com.countrygamer.morphadditions.api.AbilityAction
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.projectile.EntityFireball
import net.minecraft.util.{MovingObjectPosition, MathHelper}

/**
 *
 *
 * @author CountryGamer
 */
abstract class AbilityEjectFireball() extends AbilityEject() {

	var targetRadius: Double = 100.0D

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

	override def parse(args: Array[String]): AbilityAction = {

		try {
			this.targetRadius = args(0).toDouble
		}
		catch {
			case e: NumberFormatException =>
				e.printStackTrace()
		}

		this
	}

	def getTargetCoords(player: EntityPlayer): Array[Double] = {
		val mop: MovingObjectPosition = Cursor
				.getMOPFromPlayer(player.worldObj, player, this.targetRadius)

		if (mop == null)
			return null

		var x: Double = mop.hitVec.xCoord
		var y: Double = mop.hitVec.yCoord
		var z: Double = mop.hitVec.zCoord

		if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
			val newCoords: Array[Int] = Cursor
					.getNewCoordsFromSide(mop.blockX, mop.blockY, mop.blockZ, mop.sideHit)
			x = newCoords(0)
			y = newCoords(1)
			z = newCoords(2)
		}

		var entityHeight: Double = 0.0D
		if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
			entityHeight = mop.entityHit.height
		}

		Array[Double](x, y, z, entityHeight)
	}

}
