package com.countrygamer.morphactions.api;

import net.minecraft.entity.EntityLivingBase;

/**
 * @author CountryGamer
 */
public class Api {

	public static void registerAbility(String name, Class<? extends AbilityAction> abilityClass) {
		try {
			Class.forName("com.countrygamer.morphactions.common.ApiHelper")
					.getDeclaredMethod("registerAbility", AbilityAction.class)
					.invoke(null, name, abilityClass);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void mapAbility(Class<? extends EntityLivingBase> entityClass,
			Class<? extends AbilityAction> abilityClass) {
		try {
			Class.forName("com.countrygamer.morphactions.common.ApiHelper")
					.getDeclaredMethod("mapAbilityByName", Class.class)
					.invoke(null, entityClass, abilityClass);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void mapAbilityByName(Class<? extends EntityLivingBase> entityClass,
			String name) {
		try {
			Class.forName("com.countrygamer.morphactions.common.ApiHelper")
					.getDeclaredMethod("mapAbilityByName", String.class)
					.invoke(null, entityClass, name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void mapAbilityWithParameters(Class<? extends EntityLivingBase> entityClass,
			String nameAndPars) {
		Api.mapAbilityWithParameters(entityClass, nameAndPars, '|', ',');
	}

	public static void mapAbilityWithParameters(Class<? extends EntityLivingBase> entityClass,
			String nameAndPars,
			char nameAndParSeparator, char parSeparator) {
		try {
			Class.forName("com.countrygamer.morphactions.common.ApiHelper")
					.getDeclaredMethod("mapAbilityWithParameters", String.class, Character.class,
							Character.class)
					.invoke(null, entityClass, nameAndPars, nameAndParSeparator, parSeparator);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
