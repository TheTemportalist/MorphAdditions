package com.temportalist.morphadditions.api;

import com.temportalist.morphadditions.common.MorphAdditions;
import com.temportalist.origin.api.client.utility.Rendering;
import com.temportalist.origin.api.common.resource.EnumResource;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import morph.api.Ability;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.logging.Logger;

/**
 * @author TheTemportalist
 */
public abstract class AbilityAction extends Ability {

	// Cooldown ticks
	private int minTicks = 0;
	private int maxTicks = 1;
	private ResourceLocation icon;
	protected String name;

	public AbilityAction(String name) {
		this(EnumResource.GUI, name);
	}

	public AbilityAction(EnumResource category, String name) {
		this(MorphAdditions.loadResource(category, "ability/" + name + ".png"));
		this.name = name;
	}

	public AbilityAction(ResourceLocation iconLoc) {
		this.icon = iconLoc;
	}

	@Override
	public String getType() {
		return this.name;
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

	public String[] getArgs() {
		return this.args;
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

	@Override
	public void tick() {}

	@Override
	public void kill() {}

	@Override
	public void save(NBTTagCompound tag) {}

	@Override
	public void load(NBTTagCompound tag) {}

	@Override
	public void postRender() {}

	@SideOnly(Side.CLIENT)
	@Override
	public ResourceLocation getIcon() {
		if (!Rendering.doesTextureExist(this.icon))
			return MorphAdditions.getResource("actionMan");
		else return this.icon;
	}

	@Override
	public Ability clone() {
		AbilityAction inst = null;
		try {
			// load abilities that pass the resource location parameter
			inst = this.getClass().getConstructor(
					ResourceLocation.class).newInstance(this.getIcon());
		}
		catch (Exception exception1) {
			try {
				// load abilities which internally set the resource location parameter
				inst = this.getClass().getConstructor().newInstance();
			}
			catch (Exception exception2) {
				exception1.printStackTrace();
				exception2.printStackTrace();
			}
		}
		if (inst != null) {
			NBTTagCompound tag = new NBTTagCompound();
			this.save(tag);
			inst.load(tag);
			return inst;
		}
		return null;
	}

}
