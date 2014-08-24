package com.countrygamer.morphadditions.common.abilities

import com.countrygamer.cgo.common.lib.LogHelper
import com.countrygamer.cgo.common.lib.util.Cursor
import com.countrygamer.morphadditions.api.AbilityAction
import com.countrygamer.morphadditions.common.MorphAdditions
import net.minecraft.client.Minecraft
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.projectile.EntityLargeFireball
import net.minecraft.init.Blocks
import net.minecraft.util.{MovingObjectPosition, Vec3}

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
				large.setLocationAndAngles(player.posX, player.posY, player.posZ,
					player.rotationYaw, player.rotationPitch)
				large.setPosition(player.posX, player.posY, player.posZ)
				large.yOffset = 0.0F
				large.motionX = 0.0F
				large.motionY = 0.0F
				large.motionZ = 0.0F

				large.posX = player.posX
				large.posY = player.posY + (player.height / 2.0F).asInstanceOf[Double] + 0.5D
				large.posZ = player.posZ

				val mop: MovingObjectPosition = Cursor
						.getMOPFromPlayer(player.worldObj, player, 10D)
				if (mop != null) {
					LogHelper.info(MorphAdditions.pluginName,
						"Pos'\n" + mop.hitVec.xCoord + "\n" + mop.hitVec.yCoord + "\n" +
								mop.hitVec.zCoord
					)
					LogHelper.info(MorphAdditions.pluginName,
						"Block\n" + mop.blockX + "\n" + mop.blockY + "\n" + mop.blockZ
					)
					player.worldObj.setBlock(mop.blockX, mop.blockY, mop.blockZ, Blocks.stone)
				}
				large.accelerationX = 0.0D
				large.accelerationY = 0.0D
				large.accelerationZ = 0.0D

			/*
			val offsets: Array[Double] = this
					.get3DOffsetFromDistanceAndAngles(100F, player.rotationYaw,
						player.rotationPitch)
			val xOffset: Double = offsets(0) + player.worldObj.rand.nextGaussian() * 0.4D
			val yOffset: Double = offsets(1) + player.worldObj.rand.nextGaussian() * 0.4D
			val zOffset: Double = offsets(2) + player.worldObj.rand.nextGaussian() * 0.4D
			// here, the distance passed to get3DOffsets should match distance
			val distance: Double = MathHelper
					.sqrt_double(xOffset * xOffset + yOffset * yOffset + zOffset * zOffset)
			large.accelerationX = xOffset / distance * 0.1D
			large.accelerationY = yOffset / distance * 0.1D
			large.accelerationZ = zOffset / distance * 0.1D
			*/
			case _ =>
		}

		entity
	}

	override def parse(args: Array[String]): AbilityAction = {
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

	private def getOffsets(player: EntityPlayer): Vec3 = {
		val cursorPos: Vec3 = this.getCursorPosition(player)
		if (cursorPos == null)
			null
		else
			Vec3.createVectorHelper(
				player.posX - cursorPos.xCoord,
				player.boundingBox.minY + (player.height / 2.0D) - cursorPos.yCoord,
				player.posZ - cursorPos.zCoord
			)
	}

	private def getCursorPosition(player: EntityPlayer): Vec3 = {
		val maxDistance: Double = Math
				.pow(Minecraft.getMinecraft.gameSettings.renderDistanceChunks * 16, 3)
		val mop: MovingObjectPosition = Cursor.getMOPFromPlayer(player.worldObj, player,
			10)

		if (mop == null)
			null
		else
			mop.typeOfHit match {
				case MovingObjectPosition.MovingObjectType.ENTITY =>
					Vec3.createVectorHelper(mop.entityHit.posX,
						mop.entityHit.boundingBox.minY + (mop.entityHit.height / 2.0D),
						mop.entityHit.posZ)
				case MovingObjectPosition.MovingObjectType.BLOCK =>
					Vec3.createVectorHelper(mop.blockX, mop.blockY, mop.blockZ)
				case _ =>
					null
			}
	}

	/**
	 *
	 * @param distance The distance the entity is to travel
	 * @param yaw The rotation around the Y axis
	 * @param pitch The rotation around the X axis (horizontally left to right)
	 * @return
	 */
	private def get3DOffsetFromDistanceAndAngles(distance: Float, yaw: Float,
			pitch: Float): Array[Double] = {

		val quadAndDegPitch: Array[Float] = this
				.getQuadrantFromRotation(Math.toDegrees(pitch).asInstanceOf[Float])
		val quadPitch: Int = quadAndDegPitch(0).asInstanceOf[Int]
		val firstQuadPitch: Float = Math.toRadians(quadAndDegPitch(1)).asInstanceOf[Float]

		var y: Double = Math.sin(firstQuadPitch) * distance
		var horizontal: Double = Math.cos(firstQuadPitch) * distance

		if (quadPitch == 2 || quadPitch == 3) {
			horizontal = -horizontal
		}
		if (quadPitch == 3 || quadPitch == 4) {
			y = -y
		}

		val quadAndDegYaw: Array[Float] = this
				.getQuadrantFromRotation(Math.toDegrees(yaw).asInstanceOf[Float])
		val quadYaw: Int = quadAndDegYaw(0).asInstanceOf[Int]
		val firstQuadYaw: Float = Math.toRadians(quadAndDegYaw(1)).asInstanceOf[Float]

		var z: Double = Math.sin(firstQuadYaw) * horizontal
		var x: Double = Math.cos(firstQuadYaw) * horizontal

		if (quadYaw == 2 || quadYaw == 3) {
			x = -x
		}
		if (quadYaw == 3 || quadYaw == 4) {
			z = -z
		}

		Array[Double](x, y, -z)
	}

	private def getQuadrantFromRotation(yaw: Float): Array[Float] = {
		if (yaw >= 0 && yaw < 90) {
			Array[Float](1.0F, yaw - 0)
		}
		else if (yaw >= 90 && yaw < 180) {
			Array[Float](2.0F, yaw - 90)
		}
		else if (yaw >= 180 && yaw < 270) {
			Array[Float](3.0F, yaw - 180)
		}
		else if (yaw >= 270 && yaw < 360) {
			Array[Float](4.0F, yaw - 270)
		}
		else if (yaw >= 360) {
			this.getQuadrantFromRotation(yaw - 360)
		}
		else {
			Array[Float](-1.0F, yaw)
		}
	}

}
