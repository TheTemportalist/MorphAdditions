package com.countrygamer.morphactions.common.abilities

import com.countrygamer.morphactions.api.AbilityAction
import net.minecraft.entity.player.EntityPlayer

/**
 *
 *
 * @author CountryGamer
 */
class AbilityExplode() extends AbilityAction() {

	private var explosionRadius: Float = 0.0F

	override def trigger(player: EntityPlayer): Unit = {
		if (!player.worldObj.isRemote) {
			val canDestroyBlocks: Boolean = player.worldObj.getGameRules()
					.getGameRuleBooleanValue("mobGriefing")

			player.worldObj.createExplosion(player, player.posX, player.posY, player.posZ,
				this.explosionRadius, canDestroyBlocks)

			player.setDead()
		}
	}

	override def parse(args: Array[String]): AbilityAction = {

		try {
			this.explosionRadius = args(0).toFloat
		}
		catch {
			case e: NumberFormatException =>
				e.printStackTrace()
		}

		this
	}

	override def copy(): AbilityAction = {
		new AbilityDrop().parse(this.getArgs())
	}

}
