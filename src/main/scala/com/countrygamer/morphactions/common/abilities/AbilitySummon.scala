package com.countrygamer.morphactions.common.abilities

import com.countrygamer.morphactions.api.AbilityAction

/**
 *
 *
 * @author CountryGamer
 */
class AbilitySummon() extends AbilityAction() {

	override def trigger(): Unit = {

	}

	override def parse(args: Array[String]): AbilityAction = {
		this
	}

	override def copy(): AbilityAction = {
		new AbilitySummon().parse(this.getArgs())
	}

}
