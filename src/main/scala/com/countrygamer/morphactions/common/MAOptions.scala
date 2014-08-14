package com.countrygamer.morphactions.common

import com.countrygamer.cgo.wrapper.common.registries.OptionRegister
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import cpw.mods.fml.relauncher.SideOnly

/**
 *
 *
 * @author CountryGamer
 */
object MAOptions extends OptionRegister() {

	override def hasCustomConfiguration: Boolean = {
		true
	}

	override def customizeConfiguration(event: FMLPreInitializationEvent): Unit = {

	}

	override def register(): Unit = {

	}

	@SideOnly {val value = cpw.mods.fml.relauncher.Side.CLIENT}
	override def getGuiConfigClass: Class[_ <: net.minecraft.client.gui.GuiScreen] = {
		classOf[com.countrygamer.morphactions.client.gui.configFactory.GuiConfig]
	}

}
