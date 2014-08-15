package com.countrygamer.morphactions.api;

import net.minecraft.entity.player.EntityPlayer;

/**
 * @author CountryGamer
 */
public abstract class AbilityAction {

	public AbilityAction() {

	}

	public abstract void trigger(EntityPlayer player);

	private String[] args = null;

	public abstract AbilityAction copy();

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
