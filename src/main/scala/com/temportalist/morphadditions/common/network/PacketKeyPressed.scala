package com.temportalist.morphadditions.common.network

import com.temportalist.morphadditions.api.AbilityAction
import com.temportalist.morphadditions.common.MAOptions
import com.temportalist.morphadditions.common.init.Abilities
import com.temportalist.origin.foundation.common.network.IPacket
import cpw.mods.fml.relauncher.Side
import morph.api.Api
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer

/**
 *
 *
 * @author TheTemportalist
 */
class PacketKeyPressed() extends IPacket {

	override def handle(player: EntityPlayer, side: Side): Unit = {
		Api.getMorphEntity(player.getCommandSenderName, side.isClient) match {
			case entity: EntityLivingBase =>
				Abilities.getAbility(entity) match {
					case ability: AbilityAction =>
						MAOptions.getMP(player).trigger(entity, ability)
				}
		}

	}

}
