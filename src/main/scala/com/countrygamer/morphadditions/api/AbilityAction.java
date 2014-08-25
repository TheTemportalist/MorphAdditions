package com.countrygamer.morphadditions.api;

import net.minecraft.entity.player.EntityPlayer;

import java.lang.reflect.InvocationTargetException;

/**
 * @author CountryGamer
 */
public abstract class AbilityAction {

	public AbilityAction() {

	}

	public abstract void trigger(EntityPlayer player);

	private String[] args = null;

	public AbilityAction copy() {
		AbilityAction ability = null;
		try {
			ability = this.getClass().getConstructor().newInstance();
		}
		catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		catch (InstantiationException e) {
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		if (ability != null) {
			ability.parse(this.getArgs());
		}

		return ability;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

	public AbilityAction parse(String[] args) {
		return this;
	}

	public String[] getArgs() {
		return this.args;
	}

}
