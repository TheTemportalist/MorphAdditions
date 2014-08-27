package com.countrygamer.morphadditions.waila

import cpw.mods.fml.relauncher.ReflectionHelper
import mcp.mobius.waila.api.IWailaEntityProvider
import net.minecraft.entity.player.EntityPlayer

/**
 *
 *
 * @author CountryGamer
 */
object Waila {

	def post(): Unit = {
		try {
			val moduleReg: Class[_] = Class.forName("mcp.mobius.waila.api.impl.ModuleRegistrar")
			val instance: Object = ReflectionHelper.findField(moduleReg, "instance").get(null)
			moduleReg.getMethod("registerHeadProvider", classOf[IWailaEntityProvider],
				classOf[Class[_]]).invoke(instance, HUDHandler, classOf[EntityPlayer])
		}
		catch {
			case e: Exception =>
				e.printStackTrace()
		}

	}

}
