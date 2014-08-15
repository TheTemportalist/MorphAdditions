package com.countrygamer.morphactions.common.abilities

import com.countrygamer.cgo.common.lib.util.UtilVector
import com.countrygamer.morphactions.api.AbilityAction
import net.minecraft.entity.player.EntityPlayer

/**
 *
 *
 * @author CountryGamer
 */
class AbilityTeleport() extends AbilityAction() {

	private var reachLength: Double = 0.0D

	override def trigger(player: EntityPlayer): Unit = {
		UtilVector.teleportVector(player.worldObj, player, this.reachLength)
	}

	override def parse(args: Array[String]): AbilityAction = {

		try {
			this.reachLength = args(0).toDouble
		}
		catch {
			case e: NumberFormatException =>
				e.printStackTrace()
		}

		this
	}

	override def copy(): AbilityAction = {
		new AbilityTeleport().parse(this.getArgs())
	}

}
