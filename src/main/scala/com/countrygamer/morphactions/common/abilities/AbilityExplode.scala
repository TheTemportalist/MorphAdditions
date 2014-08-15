package com.countrygamer.morphactions.common.abilities

import com.countrygamer.morphactions.api.AbilityAction

/**
 *
 *
 * @author CountryGamer
 */
class AbilityExplode() extends AbilityAction() {

	override def trigger(): Unit = {

	}

	override def parse(args: Array[String]): AbilityAction = {
		this
	}

	override def copy(): AbilityAction = {
		new AbilityDrop().parse(this.getArgs())
	}

}
