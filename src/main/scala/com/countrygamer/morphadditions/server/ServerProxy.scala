package com.countrygamer.morphadditions.server

import com.countrygamer.morphadditions.common.CommonProxy
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World

/**
 *
 *
 * @author CountryGamer
 */
class ServerProxy() extends CommonProxy() {

	override def getServerElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int,
			z: Int, tileEntity: TileEntity): AnyRef = {
		null
	}

}
