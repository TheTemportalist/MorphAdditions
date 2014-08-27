package com.countrygamer.morphadditions.api;

import cpw.mods.fml.common.eventhandler.Cancelable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;

/**
 * @author CountryGamer
 */
@Cancelable
public class MorphActionEvent extends PlayerEvent {

	/**
	 * The entity the player is morphed as
	 */
	public EntityLivingBase entityLivingBase;
	/**
	 * The ability that is to be used
	 */
	public AbilityAction ability;

	public MorphActionEvent(EntityPlayer player, EntityLivingBase entityLivingBase,
			AbilityAction ability) {
		super(player);
		this.entityLivingBase = entityLivingBase;
		this.ability = ability;

	}

}
