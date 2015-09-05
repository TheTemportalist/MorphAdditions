package com.temportalist.morphadditions.common.init

import java.util

import com.temportalist.morphadditions.api.AbilityAction
import com.temportalist.morphadditions.common.abilities._
import morph.api.Ability
import net.minecraft.entity.EntityLivingBase

/**
 *
 *
 * @author TheTemportalist
 */
object Abilities {

	private val abilities: util.HashMap[String, Class[_ <: AbilityAction]] = new
					util.HashMap[String, Class[_ <: AbilityAction]]()
	private val entityToAbility: util.HashMap[Class[_ <: EntityLivingBase], AbilityAction] = new
					util.HashMap[Class[_ <: EntityLivingBase], AbilityAction]()

	{
		/*
		bat
		cat
		chicken -   lay eggs (random intervals)
		cow
		horse
		iron golem
		mooshroom cow
		ocelot
		pig
		sheep
		squid
		wolf    -   summon hoard
		blaze   -   fireball
		cave spider
		charged creeper - explosion
		enderdragon
		enderman    -   teleport
		endermite
		ghast   -   fireball
		magma cube
		silverfish  -   summon hoard
		skeleton
		slime
		snow golem  -   snow ball
		spider
		witch   -   ?
		wither  -   wither skull
		wither skeleton
		zombie  -   summon hoard
		zombie pigman   -   summon hoard
		NPC -   call iron golem
		*/
		registerAbility("drop", classOf[AbilityDrop])
		registerAbility("summon", classOf[AbilitySummon])
		registerAbility("explode", classOf[AbilityExplode])
		registerAbility("teleport", classOf[AbilityTeleport])
		registerAbility("ejectLargeFireball", classOf[AbilityEjectFireballLarge])
		registerAbility("ejectSmallFireball", classOf[AbilityEjectFireballSmall])
		registerAbility("ejectSnowball", classOf[AbilityEjectSnowball])

	}

	def registerAbility(name: String, abilityClass: Class[_ <: AbilityAction]): Unit = {
		this.abilities.put(name, abilityClass)
		Ability.registerAbility(name, abilityClass)
	}

	def setMapping(entityClass: Class[_ <: EntityLivingBase], ability: AbilityAction): Unit = {
		this.entityToAbility.put(entityClass, ability)
		Ability.mapAbilities(entityClass, ability)
	}

	def getAbility(entity: EntityLivingBase): AbilityAction = {
		var ability: AbilityAction = null
		var entityClass: Class[_] = entity.getClass
		do {
			if (this.entityToAbility.containsKey(entityClass)) {
				ability = this.entityToAbility.get(entityClass)
			}
			else {
				entityClass = entityClass.getSuperclass
			}
		}
		while (ability == null && entityClass != classOf[EntityLivingBase])

		ability
	}

	def getAbilityByNameAndPars(name: String, args: Array[String]): AbilityAction = {
		val abilityClass: Class[_ <: AbilityAction] = this.abilities.get(name)
		if (abilityClass != null) {
			val ability: AbilityAction = abilityClass.getConstructor().newInstance()
					.asInstanceOf[AbilityAction]
			ability.parse(args)
			return ability
		}
		null
	}

}
