package com.temportalist.morphadditions.waila

import com.temportalist.morphadditions.common.MorphAdditions
import com.temportalist.origin.library.common.lib.LogHelper
import cpw.mods.fml.relauncher.ReflectionHelper
import mcp.mobius.waila.api.IWailaEntityProvider
import net.minecraft.entity.player.EntityPlayer

/**
 *
 *
 * @author TheTemportalist
 */
object Waila {

	def post(): Unit = {
		try {
			val moduleReg: Class[_] = Class.forName("mcp.mobius.waila.api.impl.ModuleRegistrar")
			val instance: Object = ReflectionHelper.findField(moduleReg, "instance").get(null)
			moduleReg.getMethod("registerHeadProvider", classOf[IWailaEntityProvider],
				classOf[Class[_]]).invoke(instance, HUDHandler, classOf[EntityPlayer])
			LogHelper.info(MorphAdditions.pluginName, "WAILA mod found. Done registering.")
		}
		catch {
			case e: Exception =>
				LogHelper.info(MorphAdditions.pluginName, "WAILA not found. Not registering.")
		}

	}

}
