package com.countrygamer.morphadditions.common.abilities

import com.countrygamer.morphadditions.api.AbilityAction
import net.minecraft.entity.player.EntityPlayer

/**
 *
 *
 * @author CountryGamer
 */
class AbilitySummon() extends AbilityAction() {

	override def trigger(player: EntityPlayer): Unit = {

	}

	override def parse(args: Array[String]): AbilityAction = {
		this
	}

	override def copy(): AbilityAction = {
		new AbilitySummon().parse(this.getArgs())
	}

}
