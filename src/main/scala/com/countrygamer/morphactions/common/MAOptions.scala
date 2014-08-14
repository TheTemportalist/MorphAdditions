package com.countrygamer.morphactions.common

import com.countrygamer.cgo.wrapper.common.registries.OptionRegister
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.client.gui.GuiScreen

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
	override def getGuiConfigClass: Class[_ <: GuiScreen] = {

	}

}
