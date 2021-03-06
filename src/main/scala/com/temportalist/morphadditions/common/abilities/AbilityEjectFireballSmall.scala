package com.temportalist.morphadditions.common.abilities

import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.projectile.EntitySmallFireball

/**
 *
 *
 * @author TheTemportalist
 */
class AbilityEjectFireballSmall() extends AbilityEjectFireball("Small") {

	override def getEntityClass(): Class[_ <: Entity] = {
		classOf[EntitySmallFireball]
	}

	override def getEntity(player: EntityPlayer): Entity = {
		val entity: EntitySmallFireball = super.getEntity(player).asInstanceOf[EntitySmallFireball]

		if (entity != null)
			entity.posY = player.posY + (player.height / 2.0F).asInstanceOf[Double] + 0.5D

		entity
	}

}
