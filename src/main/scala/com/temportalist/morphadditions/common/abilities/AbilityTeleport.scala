package com.temportalist.morphadditions.common.abilities

import com.temportalist.morphadditions.api.AbilityAction
import com.temportalist.origin.library.common.utility.Teleport
import net.minecraft.entity.player.EntityPlayer

/**
 *
 *
 * @author TheTemportalist
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
