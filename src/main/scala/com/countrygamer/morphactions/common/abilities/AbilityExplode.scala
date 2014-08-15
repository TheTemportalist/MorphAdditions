package com.countrygamer.morphactions.common.abilities

import com.countrygamer.morphactions.api.AbilityAction
import net.minecraft.entity.player.EntityPlayer

/**
 *
 *
 * @author CountryGamer
 */
class AbilityExplode() extends AbilityAction() {

	override def trigger(player: EntityPlayer): Unit = {

	}

	override def parse(args: Array[String]): AbilityAction = {
		this
	}

	override def copy(): AbilityAction = {
		new AbilityDrop().parse(this.getArgs())
	}

}
