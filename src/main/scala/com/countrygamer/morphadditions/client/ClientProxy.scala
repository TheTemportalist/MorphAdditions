package com.countrygamer.morphadditions.client

import java.util

import com.countrygamer.morphadditions.client.gui.HUDOverlay
import com.countrygamer.morphadditions.client.gui.configFactory.GuiConfig
import com.countrygamer.morphadditions.common.{CommonProxy, MorphedPlayer}
import cpw.mods.fml.client.IModGuiFactory
import cpw.mods.fml.client.IModGuiFactory.{RuntimeOptionCategoryElement, RuntimeOptionGuiHandler}
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.Vec3
import net.minecraft.world.World
import net.minecraftforge.common.MinecraftForge

/**
 *
 *
 * @author CountryGamer
 */
class ClientProxy() extends CommonProxy with IModGuiFactory {

	override def registerRender(): Unit = {
		MinecraftForge.EVENT_BUS.register(HUDOverlay)

	}

	override def getClientElement(ID: Int, player: EntityPlayer, world: World, coord: Vec3,
			tileEntity: TileEntity): AnyRef = {
		null
	}

	override def syncPlayer(player: MorphedPlayer): Unit = {}

	override def initialize(minecraftInstance: Minecraft): Unit = {

	}

	override def runtimeGuiCategories(): util.Set[RuntimeOptionCategoryElement] = {
		null
	}

	override def getHandlerFor(element: RuntimeOptionCategoryElement): RuntimeOptionGuiHandler = {
		null
	}

	override def mainConfigGuiClass(): Class[_ <: GuiScreen] = {
		classOf[GuiConfig]
	}

}
