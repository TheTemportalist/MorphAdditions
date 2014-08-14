package com.countrygamer.morphactions.common

import com.countrygamer.cgo.wrapper.common.PluginWrapper
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}
import cpw.mods.fml.common.{Mod, SidedProxy}

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
		super.preInitialize(this.pluginID, this.pluginName, event, this.proxy)

	}

	@Mod.EventHandler
	def init(event: FMLInitializationEvent): Unit = {
		super.initialize(event)

	}

	@Mod.EventHandler
	def postInit(event: FMLPostInitializationEvent): Unit = {
		super.postInitialize(event)

	}

}
