package com.temportalist.morphadditions.common

import com.temportalist.origin.wrapper.common.ProxyWrapper
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World

/**
 *
 *
 * @author TheTemportalist
 */
class CommonProxy() extends ProxyWrapper {

	override def registerRender(): Unit = {
	}

	override def getClientElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int,
			z: Int, tileEntity: TileEntity): AnyRef = {
		null
	}

	override def getServerElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int,
			z: Int, tileEntity: TileEntity): AnyRef = {
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
