package com.countrygamer.morphadditions.common.abilities

import com.countrygamer.morphadditions.api.AbilityAction
import morph.api.Api
import net.minecraft.entity.Entity
import net.minecraft.entity.monster.EntityCreeper
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

			var radius: Float = this.explosionRadius
			val morphedEnt: Entity = Api.getMorphEntity(player.getCommandSenderName, false)
			if (morphedEnt.isInstanceOf[EntityCreeper] && morphedEnt.getDataWatcher
					.getWatchableObjectByte(17).equals((1).asInstanceOf[Byte])) {
				radius *= 2
			}

			player.worldObj.createExplosion(player, player.posX, player.posY, player.posZ,
				radius, canDestroyBlocks)

			if (!player.capabilities.isCreativeMode && !player.capabilities.disableDamage) {
				player.setHealth(0.0F)
			}

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
		new AbilityExplode().parse(this.getArgs())
	}

}
