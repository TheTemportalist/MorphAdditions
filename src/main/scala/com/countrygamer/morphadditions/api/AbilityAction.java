package com.countrygamer.morphadditions.api;

import net.minecraft.entity.player.EntityPlayer;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.logging.Logger;

/**
 * @author CountryGamer
 */
public abstract class AbilityAction {

	// Cooldown ticks
	private int minTicks = 0;
	private int maxTicks = 0;

	public AbilityAction() {
	}

	public abstract void trigger(EntityPlayer player);

	private String[] args = null;

	public AbilityAction copy() {
		AbilityAction ability = null;
		try {
			ability = this.getClass().getConstructor().newInstance();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		if (ability != null) {
			ability.parse(this.getArgs());
			ability.setCooldown(this.minTicks, this.maxTicks);
		}

		return ability;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

	public AbilityAction parse(String[] args) {
		this.setArgs(args);
		return this;
	}

	public String[] getArgs() {
		return this.args;
	}

	public AbilityAction setCooldown(int minTicks, int maxTicks) {
		this.minTicks = minTicks;
		this.maxTicks = maxTicks;
		while (this.maxTicks - this.minTicks <= 0) {
			this.maxTicks++;
		}
		if (this.maxTicks != maxTicks) {
			Logger.getLogger("MorphAdditions")
					.info("[WARNING] Invalid min (" + minTicks + ") and max (" + maxTicks
							+ ") for ability " + this.getClass().getSimpleName()
							+ ".\nIncremented max ticks to " + this.maxTicks + ".");
		}
		return this;
	}

	public int getCooldown() {
		return new Random().nextInt(this.maxTicks - this.minTicks) + this.minTicks;
	}

}
