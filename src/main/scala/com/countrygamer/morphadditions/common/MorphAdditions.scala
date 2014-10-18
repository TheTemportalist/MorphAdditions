package com.countrygamer.morphadditions.common

import com.countrygamer.cgo.library.common.helpers.RegisterHelper
import com.countrygamer.cgo.wrapper.common.PluginWrapper
import com.countrygamer.morphadditions.addon.Morph
import com.countrygamer.morphadditions.client.KeyHandler
import com.countrygamer.morphadditions.common.network.PacketKeyPressed
import com.countrygamer.morphadditions.waila.Waila
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.gameevent.TickEvent
import cpw.mods.fml.common.gameevent.TickEvent.Phase
import cpw.mods.fml.common.{FMLCommonHandler, Mod, SidedProxy}
import cpw.mods.fml.relauncher.Side

/**
 *
 *
 * @author CountryGamer
 */
@Mod(modid = MorphAdditions.pluginID, name = MorphAdditions.pluginName,
	version = "@PLUGIN_VERSION@",
	guiFactory = MorphAdditions.clientProxy,
	modLanguage = "scala",
	dependencies = "required-after:Forge@[10.13,);required-after:cgo@[3.2,);required-after:Morph@[0.9.0,);"
)
object MorphAdditions extends PluginWrapper {

	final val pluginID = "morphadditions"
	final val pluginName = "MorphAdditions"
	final val clientProxy = "com.countrygamer.morphadditions.client.ClientProxy"
	final val serverProxy = "com.countrygamer.morphadditions.server.ServerProxy"

	@SidedProxy(
		clientSide = this.clientProxy,
		serverSide = this.serverProxy
	)
	var proxy: CommonProxy = null

	@Mod.EventHandler
	def preInit(event: FMLPreInitializationEvent): Unit = {
		super.preInitialize(this.pluginID, this.pluginName, event, this.proxy, MAOptions)

		RegisterHelper.registerExtendedPlayer("morphedPlayer", classOf[MorphedPlayer],
			deathPersistance = true)

		RegisterHelper.registerHandler(this, null)

		RegisterHelper.registerPacketHandler(this.pluginID, classOf[PacketKeyPressed])

		RegisterHelper.registerCommand(CommandMorphA)

		if (event.getSide == Side.CLIENT) {
			FMLCommonHandler.instance().bus().register(KeyHandler)
		}

	}

	@Mod.EventHandler
	def init(event: FMLInitializationEvent): Unit = {
		super.initialize(event)

	}

	@Mod.EventHandler
	def postInit(event: FMLPostInitializationEvent): Unit = {
		super.postInitialize(event)

		Morph.register()
		Waila.post()

	}

	@SubscribeEvent
	def tickingPlayer(event: TickEvent.PlayerTickEvent): Unit = {
		if (event.phase == Phase.START)
			this.proxy.tickPlayer(MAOptions.getMP(event.player))
	}

}
