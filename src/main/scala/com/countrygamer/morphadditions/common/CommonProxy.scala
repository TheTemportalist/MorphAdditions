package com.countrygamer.morphadditions.common

import com.countrygamer.cgo.wrapper.common.ProxyWrapper
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.Vec3
import net.minecraft.world.World

/**
 *
 *
 * @author CountryGamer
 */
class CommonProxy() extends ProxyWrapper {

	override def registerRender(): Unit = {
	}

	override def getClientElement(ID: Int, player: EntityPlayer, world: World, coord: Vec3,
			tileEntity: TileEntity): AnyRef = {
		null
	}

	override def getServerElement(ID: Int, player: EntityPlayer, world: World, coord: Vec3,
			tileEntity: TileEntity): AnyRef = {
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
