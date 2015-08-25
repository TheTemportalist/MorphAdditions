package com.temportalist.morphadditions.common

import java.util

import com.temportalist.morphadditions.api.AbilityAction
import com.temportalist.morphadditions.common.init.Abilities
import net.minecraft.entity.EntityLivingBase

/**
 *
 *
 * @author TheTemportalist
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
		var min_cooldown: Int = 0
		var max_cooldown: Int = 1
		val pars: util.ArrayList[String] = new util.ArrayList[String]()
		if (nameAndPars.contains(nameAndParSeparator)) {
			val nameAndPars_Array: Array[String] = nameAndPars.split(nameAndParSeparator)

			name = nameAndPars_Array(0)
			var args: String = null

			var cooldown_String: String = ""
			if (nameAndPars_Array.length == 3) {
				cooldown_String = nameAndPars_Array(1)
				args = nameAndPars_Array(2)
			}
			else {
				args = nameAndPars_Array(1)
			}

			if (!cooldown_String.equals("")) {
				val cooldownSet: Array[String] = cooldown_String.split(parSeparator)
				try {
					min_cooldown = cooldownSet(0).toInt
					max_cooldown = cooldownSet(1).toInt
				}
				catch {
					case e: NumberFormatException =>
						e.printStackTrace()
				}
			}

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
		else {
			name = nameAndPars
		}

		Abilities.setMapping(entityClass,
			Abilities.getAbilityByNameAndPars(name, pars.toArray(new Array[String](pars.size())))
					.setCoolDown(min_cooldown, max_cooldown))
	}

}
