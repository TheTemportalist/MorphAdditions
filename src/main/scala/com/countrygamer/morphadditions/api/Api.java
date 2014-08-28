package com.countrygamer.morphadditions.api;

import net.minecraft.entity.EntityLivingBase;

/**
 * @author CountryGamer
 */
public class Api {

	/**
	 * Registers an Ability Class. This class must extend AbilityAction.
	 *
	 * @param name         The name to be used in configs and such for mapping a entity class with an ability
	 * @param abilityClass The class of the ability, extending AbilityAction
	 */
	public static void registerAbility(String name, Class<? extends AbilityAction> abilityClass) {
		try {
			Class.forName("com.countrygamer.morphactions.common.ApiHelper")
					.getDeclaredMethod("registerAbility", String.class, Class.class)
					.invoke(null, name, abilityClass);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Maps an entity class with an ability instance. This is used for code based ability adding.
	 *
	 * @param entityClass The entity class for the mapping
	 * @param ability     The object extending AbilityAction
	 */
	public static void mapAbility(Class<? extends EntityLivingBase> entityClass,
			AbilityAction ability) {
		try {
			Class.forName("com.countrygamer.morphactions.common.ApiHelper")
					.getDeclaredMethod("mapAbility", Class.class, AbilityAction.class)
					.invoke(null, entityClass, ability);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Maps an entity class to an ability key, setting the ability with no arguments.
	 *
	 * @param entityClass The entity class for the mapping
	 * @param name        The registered ability key
	 */
	public static void mapAbilityByName(Class<? extends EntityLivingBase> entityClass,
			String name) {
		try {
			Class.forName("com.countrygamer.morphactions.common.ApiHelper")
					.getDeclaredMethod("mapAbilityByName", Class.class, String.class)
					.invoke(null, entityClass, name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Maps an entity class to string representing the registered key and its arguments
	 *
	 * @param entityClass The entity class for the mapping
	 * @param nameAndPars The registered ability key + the ability's arguments. Example: "key|arg,arg,..."
	 */
	public static void mapAbilityWithParameters(Class<? extends EntityLivingBase> entityClass,
			String nameAndPars) {
		try {
			Class.forName("com.countrygamer.morphactions.common.ApiHelper")
					.getDeclaredMethod("mapAbilityWithParameters", Class.class, String.class)
					.invoke(null, entityClass, nameAndPars);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Maps an entity class to string representing the registered key and its arguments.
	 * Unlike mapAbilityWithParameters(Class, String), the latter two arguments of this function
	 * are the separators. By default, these are '|' and ','
	 *
	 * @param entityClass The entity class for the mapping
	 * @param nameAndPars The registered ability key + the ability's arguments. Example: "key|arg,arg,..."
	 */
	public static void mapAbilityWithParameters(Class<? extends EntityLivingBase> entityClass,
			String nameAndPars,
			char nameAndParSeparator, char parSeparator) {
		try {
			Class.forName("com.countrygamer.morphactions.common.ApiHelper")
					.getDeclaredMethod("mapAbilityWithParameters", Class.class, String.class,
							Character.class,
							Character.class)
					.invoke(null, entityClass, nameAndPars, nameAndParSeparator, parSeparator);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
