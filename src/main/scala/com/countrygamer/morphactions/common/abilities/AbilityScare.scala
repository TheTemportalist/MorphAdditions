package com.countrygamer.morphactions.common.abilities

import com.countrygamer.morphactions.api.AbilityAction

/**
 *
 *
 * @author CountryGamer
 */
class AbilityScare() extends AbilityAction() {

	override def trigger(): Unit = {

	}

	override def parse(args: Array[String]): AbilityAction = {
		this
	}

	override def copy(): AbilityAction = {
		new AbilityScare().parse(this.getArgs())
	}

}
