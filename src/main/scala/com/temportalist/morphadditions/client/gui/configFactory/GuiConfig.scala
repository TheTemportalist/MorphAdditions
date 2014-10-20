package com.temportalist.morphadditions.client.gui.configFactory

import com.temportalist.morphadditions.common.MorphAdditions
import com.temportalist.origin.wrapper.client.gui.configFactory.GuiConfigWrapper
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.client.gui.GuiScreen

/**
 *
 *
 * @author TheTemportalist
 */
@SideOnly(Side.CLIENT)
class GuiConfig(guiScreen: GuiScreen)
		extends GuiConfigWrapper(guiScreen, MorphAdditions, MorphAdditions.pluginID) {
}
