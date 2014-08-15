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

	override def trigger(player: EntityPlayer): Unit = {
		UtilVector.teleportVector(player.worldObj, player, 500D)
	}

	override def parse(args: Array[String]): AbilityAction = {
		this
	}

	override def copy(): AbilityAction = {
		new AbilityTeleport().parse(this.getArgs())
	}

}
