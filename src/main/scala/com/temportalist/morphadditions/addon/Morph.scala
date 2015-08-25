package com.temportalist.morphadditions.addon

import java.util

import com.temportalist.morphadditions.common.MorphAdditions
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.{EntityPlayer, EntityPlayerMP}
import net.minecraft.world.World

/**
 *
 *
 * @author TheTemportalist
 */
object Morph {

	var Morph: Class[_] = null
	var tickHandlerServer: Object = null
	var MorphHandler: Class[_] = null
	var MorphState: Class[_] = null
	var EntityHelper: Class[_] = null

	def register(): Unit = {
		try {
			this.Morph = Class.forName("morph.common.Morph")
			this.MorphHandler = Class.forName("morph.common.morph.MorphHandler")
			this.MorphState = Class.forName("morph.common.morph.MorphState")
			this.EntityHelper = Class.forName("morph.common.core.EntityHelper")

			MorphAdditions.log("Morph Reflection Success")
		}
		catch {
			case e: Exception =>
				e.printStackTrace()
				MorphAdditions.log("Morph Reflection Failure")
				return
		}

		//
		var proxyO: Object = null
		var proxyC: Class[_] = null

		try {
			proxyO = this.Morph.getDeclaredField("proxy").get(null)
			proxyC = proxyO.getClass
		}
		catch {
			case e: Exception =>
				e.printStackTrace()
		}

		if (proxyC != null) {
			var field: java.lang.reflect.Field = null
			while (field == null) {
				try {
					field = proxyC.getDeclaredField("tickHandlerServer")
				}
				catch {
					case e: Exception =>
					//e.printStackTrace()
				}
				if (field == null) {
					proxyC = proxyC.getSuperclass
				}
			}
			if (field != null) {
				this.tickHandlerServer = field.get(proxyO)
			}
		}
		//

	}

	def getPlayerMorphs(world: World, player: EntityPlayer): util.ArrayList[_] = {
		try {
			this.tickHandlerServer.getClass.getDeclaredMethod(
				"getPlayerMorphs", classOf[World], classOf[EntityPlayer]
			).invoke(this.tickHandlerServer, world, player).asInstanceOf[util.ArrayList[_]]
		}
		catch {
			case e: Exception =>
				e.printStackTrace()
				null
		}
	}

	def addMorph(player: EntityPlayerMP, entity: EntityLivingBase): Unit = {
		try {
			this.EntityHelper.getMethod(
				"morphPlayer", classOf[EntityPlayerMP], classOf[EntityLivingBase],
				java.lang.Boolean.TYPE, java.lang.Boolean.TYPE
			).invoke(null, player, entity, java.lang.Boolean.valueOf(false),
			            java.lang.Boolean.valueOf(false))
		}
		catch {
			case e: Exception =>
				e.printStackTrace()
		}
	}

}
