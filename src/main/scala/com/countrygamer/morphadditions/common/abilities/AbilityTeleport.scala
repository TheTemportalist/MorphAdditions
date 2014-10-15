package com.countrygamer.morphadditions.common.abilities

import com.countrygamer.cgo.library.common.utility.Teleport
import com.countrygamer.morphadditions.api.AbilityAction
import net.minecraft.entity.player.EntityPlayer

/**
 *
 *
 * @author CountryGamer
 */
class AbilityTeleport() extends AbilityAction() {

	private var reachLength: Double = 0.0D

	override def trigger(player: EntityPlayer): Unit = {
		Teleport.toCursorPosition(player, this.reachLength)
	}

	override def parse(args: Array[String]): AbilityAction = {
		super.parse(args)

		try {
			this.reachLength = args(0).toDouble
		}
		catch {
			case e: NumberFormatException =>
				e.printStackTrace()
		}

		this
	}

}
