package com.temportalist.morphadditions.api;

import net.minecraft.entity.player.EntityPlayer;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.logging.Logger;

/**
 * @author TheTemportalist
 */
public abstract class AbilityAction {

	// Cooldown ticks
	private int minTicks = 0;
	private int maxTicks = 1;

	public AbilityAction() {
	}

	/**
	 * Called when the player presses the keybinding and if the player is able to trigger the ability
	 *
	 * @param player The player doing the triggering
	 */
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
			ability.setCoolDown(this.minTicks, this.maxTicks);
		}

		return ability;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

	/**
	 * Sets ability variables from the args passed
	 *
	 * @param args Passed args, usually from a config
	 * @return this ability
	 */
	public AbilityAction parse(String[] args) {
		this.setArgs(args);
		return this;
	}

	public String[] getArgs() {
		return this.args;
	}

	/**
	 * Set the range for the cooldown timer
	 *
	 * @param minTicks The minimum amount of ticks (inclusive)
	 * @param maxTicks The maximum amout of ticks (exclusive). If less than or equal to minTicks, will increment
	 * @return this ability
	 */
	public AbilityAction setCoolDown(int minTicks, int maxTicks) {
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

	/**
	 * Gets a random integer between the minimum and maximum ticks
	 *
	 * @return The random integer
	 */
	public int getCoolDown() {
		return new Random().nextInt(this.maxTicks - this.minTicks) + this.minTicks;
	}

}
