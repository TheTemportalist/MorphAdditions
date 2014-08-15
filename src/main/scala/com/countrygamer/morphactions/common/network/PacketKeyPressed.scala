package com.countrygamer.morphactions.common.network

import com.countrygamer.cgo.wrapper.common.network.AbstractPacket
import com.countrygamer.morphactions.api.AbilityAction
import com.countrygamer.morphactions.common.init.Abilities
import io.netty.buffer.ByteBuf
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer

/**
 *
 *
 * @author CountryGamer
 */
class PacketKeyPressed() extends AbstractPacket {

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
			if (ability != null)
				ability.copy().trigger()
		}

	}

}
