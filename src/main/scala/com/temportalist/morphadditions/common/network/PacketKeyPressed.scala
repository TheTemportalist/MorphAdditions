package com.temportalist.morphadditions.common.network

import com.temportalist.morphadditions.api.AbilityAction
import com.temportalist.morphadditions.common.MAOptions
import com.temportalist.morphadditions.common.init.Abilities
import com.temportalist.origin.library.common.nethandler.IPacket
import io.netty.buffer.ByteBuf
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer

/**
 *
 *
 * @author TheTemportalist
 */
class PacketKeyPressed() extends IPacket {

	override def writeTo(buffer: ByteBuf): Unit = {

	}

	override def readFrom(buffer: ByteBuf): Unit = {

	}

	override def handleOnClient(player: EntityPlayer): Unit = {
		this.handle(player, isClient = true)
	}

	override def handleOnServer(player: EntityPlayer): Unit = {
		this.handle(player, isClient = false)
	}

	def handle(player: EntityPlayer, isClient: Boolean): Unit = {
		val entity: EntityLivingBase = morph.api.Api
				.getMorphEntity(player.getCommandSenderName, isClient)

		if (entity != null) {
			val ability: AbilityAction = Abilities.getAbility(entity)
			if (ability != null) {
				MAOptions.getMP(player).trigger(entity, ability)
			}
		}

	}

}
