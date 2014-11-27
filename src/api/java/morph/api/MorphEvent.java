package morph.api;

import cpw.mods.fml.common.eventhandler.Cancelable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;

@Cancelable
public class MorphEvent extends PlayerEvent {
	//Can be null
	public final EntityLivingBase prevMorph;

	//Will never be null
	public final EntityLivingBase morph;

	public MorphEvent(EntityPlayer player, EntityLivingBase prevMorph, EntityLivingBase morph) {
		super(player);
		this.prevMorph = prevMorph;
		this.morph = morph;
	}
}
