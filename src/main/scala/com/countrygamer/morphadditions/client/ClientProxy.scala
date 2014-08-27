package com.countrygamer.morphadditions.client

import com.countrygamer.morphadditions.client.gui.HUDOverlay
import com.countrygamer.morphadditions.common.{CommonProxy, MorphedPlayer}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import net.minecraftforge.common.MinecraftForge

/**
 *
 *
 * @author CountryGamer
 */
class ClientProxy() extends CommonProxy() {

	override def registerRender(): Unit = {
		MinecraftForge.EVENT_BUS.register(HUDOverlay)

	}

	override def getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int,
			z: Int): AnyRef = {
		null
	}

	override def syncPlayer(player: MorphedPlayer): Unit = {}

}
