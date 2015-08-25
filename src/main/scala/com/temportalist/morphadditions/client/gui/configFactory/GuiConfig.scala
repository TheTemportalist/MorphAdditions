package com.temportalist.morphadditions.client.gui.configFactory

import com.temportalist.morphadditions.common.MorphAdditions
import com.temportalist.origin.foundation.client.gui.GuiConfigBase
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.client.gui.GuiScreen

/**
 *
 *
 * @author TheTemportalist
 */
@SideOnly(Side.CLIENT)
class GuiConfig(guiScreen: GuiScreen)
		extends GuiConfigBase(guiScreen, MorphAdditions, MorphAdditions.getModid) {
}
