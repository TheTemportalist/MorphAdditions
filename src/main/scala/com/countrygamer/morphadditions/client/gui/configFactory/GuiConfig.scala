package com.countrygamer.morphadditions.client.gui.configFactory

import com.countrygamer.cgo.wrapper.client.gui.configFactory.GuiConfigWrapper
import com.countrygamer.morphadditions.common.MorphAdditions
import net.minecraft.client.gui.GuiScreen

/**
 *
 *
 * @author CountryGamer
 */
class GuiConfig(guiScreen: GuiScreen)
		extends GuiConfigWrapper(guiScreen, MorphAdditions.pluginID, MorphAdditions) {
}
