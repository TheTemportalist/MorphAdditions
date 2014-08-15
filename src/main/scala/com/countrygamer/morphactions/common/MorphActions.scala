package com.countrygamer.morphactions.common

import com.countrygamer.cgo.common.RegisterHelper
import com.countrygamer.cgo.wrapper.common.PluginWrapper
import com.countrygamer.morphactions.client.KeyHandler
import com.countrygamer.morphactions.common.network.PacketKeyPressed
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.gameevent.TickEvent
import cpw.mods.fml.common.{FMLCommonHandler, Mod, SidedProxy}
import cpw.mods.fml.relauncher.Side

/**
 *
 *
 * @author CountryGamer
 */
@Mod(modid = MorphActions.pluginID, name = MorphActions.pluginName, version = "@PLUGIN_VERSION@",
	guiFactory = "com.countrygamer.morphactions.client.gui.configFactory.MorphActionsFactory",
	modLanguage = "scala",
	dependencies = "required-after:Forge@[10.13,);required-after:cgo@[3,);required-after:Morph@[0.9.0,);"
)
object MorphActions extends PluginWrapper {

	final val pluginID = "morphactions"
	final val pluginName = "MorphActions"

	@SidedProxy(clientSide = "com.countrygamer.morphactions.client.ClientProxy",
		serverSide = "com.countrygamer.morphactions.common.CommonProxy")
	var proxy: CommonProxy = null

	@Mod.EventHandler
	def preInit(event: FMLPreInitializationEvent): Unit = {
		super.preInitialize(this.pluginID, this.pluginName, event, this.proxy, MAOptions)

		RegisterHelper.registerExtendedPlayer("morphedPlayer", classOf[MorphedPlayer],
			deathPersistance = true)

		RegisterHelper.registerHandler(this, null)

		RegisterHelper.registerPacketHandler(this.pluginID, classOf[PacketKeyPressed])

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

	}

	@SubscribeEvent
	def tickingPlayer(event: TickEvent.PlayerTickEvent): Unit = {
		val mPlayer: MorphedPlayer = MAOptions.getMP(event.player)

	}

}
