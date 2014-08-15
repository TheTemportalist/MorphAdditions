package com.countrygamer.morphactions.common

import java.util

import com.countrygamer.morphactions.api.AbilityAction
import com.countrygamer.morphactions.common.init.Abilities
import net.minecraft.entity.EntityLivingBase

/**
 *
 *
 * @author CountryGamer
 */
object ApiHelper {

	def registerAbility(name: String, abilityClass: Class[_ <: AbilityAction]): Unit = {
		Abilities.registerAbility(name, abilityClass)
	}

	def mapAbility(entityClass: Class[_ <: EntityLivingBase], ability: AbilityAction): Unit = {
		Abilities.setMapping(entityClass, ability)
	}

	def mapAbilityByName(entityClass: Class[_ <: EntityLivingBase], name: String): Unit = {
		Abilities.setMapping(entityClass,
			Abilities.getAbilityByNameAndPars(name, new Array[String](0)))
	}

	def mapAbilityByParameters(entityClass: Class[_ <: EntityLivingBase],
			nameAndPars: String): Unit = {
		ApiHelper.mapAbilityByParameters(entityClass, nameAndPars, '|', ',')
	}

	def mapAbilityByParameters(entityClass: Class[_ <: EntityLivingBase], nameAndPars: String,
			nameAndParSeparator: Char, parSeparator: Char): Unit = {
		var name: String = ""
		val pars: util.ArrayList[String] = new util.ArrayList[String]()
		if (nameAndPars.contains(nameAndParSeparator)) {
			val nameAndPars_Array: Array[String] = nameAndPars.split(nameAndParSeparator)
			name = nameAndPars_Array(0)

			if (nameAndPars_Array.length > 1) {

				val args: String = nameAndPars_Array(1)

				if (args.contains(parSeparator)) {
					val parArray: Array[String] = args.split(parSeparator)
					for (i <- 0 until parArray.length) {
						pars.add(parArray(i).trim)
					}
				}
				else {
					pars.add(args.trim)
				}

			}

		}
		else {
			name = nameAndPars
		}

		Abilities.setMapping(entityClass,
			Abilities.getAbilityByNameAndPars(name, pars.toArray(new Array[String](pars.size()))))
	}

}
