package com.countrygamer.morphactions.client.gui.configFactory

import com.countrygamer.cgo.wrapper.client.gui.configFactory.GuiConfigWrapper
import com.countrygamer.morphactions.common.MorphActions
import net.minecraft.client.gui.GuiScreen

/**
 *
 *
 * @author CountryGamer
 */
class GuiConfig(guiScreen: GuiScreen)
		extends GuiConfigWrapper(guiScreen, MorphActions.pluginID, MorphActions) {
}
