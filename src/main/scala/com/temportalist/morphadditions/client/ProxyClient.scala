package com.temportalist.morphadditions.client

import java.util

import com.temportalist.morphadditions.client.gui.HUDOverlay
import com.temportalist.morphadditions.client.gui.configFactory.GuiConfig
import com.temportalist.morphadditions.common.ProxyCommon
import com.temportalist.origin.api.common.register.Registry
import cpw.mods.fml.client.IModGuiFactory
import cpw.mods.fml.client.IModGuiFactory.{RuntimeOptionCategoryElement, RuntimeOptionGuiHandler}
import cpw.mods.fml.client.registry.ClientRegistry
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World
import net.minecraftforge.common.MinecraftForge

/**
 *
 *
 * @author TheTemportalist
 */
class ProxyClient extends ProxyCommon with IModGuiFactory {

	override def register(): Unit = {
		MinecraftForge.EVENT_BUS.register(HUDOverlay)
		Registry.registerKeyBinder(KeyHandler)
		///Registry.registerHandler(KeyBinder)

	}

	override def getClientElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int,
			z: Int, tileEntity: TileEntity): AnyRef = null

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
