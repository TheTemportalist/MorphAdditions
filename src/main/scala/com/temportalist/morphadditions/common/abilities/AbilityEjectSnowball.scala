package com.temportalist.morphadditions.common.abilities

import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.projectile.EntitySnowball
import net.minecraft.util.MathHelper

/**
 *
 *
 * @author TheTemportalist
 */
class AbilityEjectSnowball() extends AbilityEject("Snowball") {

	override def getEntityClass(): Class[_ <: Entity] = {
		null
	}

	override def trigger(player: EntityPlayer): Unit = {
		val targetCoords: Array[Double] = this.getTargetCoords(player)

		if (targetCoords == null)
			return

		val snowball: EntitySnowball = new EntitySnowball(player.worldObj, player)
		val x: Double = targetCoords(0) - player.posX
		val y: Double = targetCoords(1) + targetCoords(3) - 1.100000023841858D - snowball.posY
		val z: Double = targetCoords(2) - player.posZ
		val distance: Float = MathHelper.sqrt_double(x * x + z * z) * 0.2F
		snowball.setThrowableHeading(x, y + distance, z, 1.6F, 12.0F)
		player.playSound("random.bow", 1.0F, 1.0F / (player.getRNG().nextFloat() * 0.4F + 0.8F))
		player.worldObj.spawnEntityInWorld(snowball)

	}

}
