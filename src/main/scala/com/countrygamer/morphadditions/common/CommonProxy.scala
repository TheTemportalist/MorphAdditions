package com.countrygamer.morphadditions.common

import com.countrygamer.cgo.wrapper.common.ProxyWrapper
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World

/**
 *
 *
 * @author CountryGamer
 */
class CommonProxy() extends ProxyWrapper {

	override def getServerGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int,
			z: Int): AnyRef = {
		null
	}

	override def registerRender(): Unit = {}

	override def getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int,
			z: Int): AnyRef = {
		null
	}

	def tickPlayer(player: MorphedPlayer): Unit = {
		player.tick()
		if (player.getCooldown() < 0)
			this.syncPlayer(player)
	}

	def syncPlayer(player: MorphedPlayer): Unit = {
		player.syncEntity()
	}

}
