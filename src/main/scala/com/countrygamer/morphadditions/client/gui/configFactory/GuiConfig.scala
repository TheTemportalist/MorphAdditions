package com.countrygamer.morphadditions.client.gui.configFactory

import com.countrygamer.cgo.wrapper.client.gui.configFactory.GuiConfigWrapper
import com.countrygamer.morphadditions.common.MorphAdditions
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.client.gui.GuiScreen

/**
 *
 *
 * @author CountryGamer
 */
@SideOnly(Side.CLIENT)
class GuiConfig(guiScreen: GuiScreen)
		extends GuiConfigWrapper(guiScreen, MorphAdditions, MorphAdditions.pluginID) {
}
