package com.temportalist.morphadditions.common

import java.io.IOException

import com.temportalist.morphadditions.addon.Morph
import com.temportalist.morphadditions.client.KeyHandler
import com.temportalist.morphadditions.common.network.PacketKeyPressed
import com.temportalist.morphadditions.waila.Waila
import com.temportalist.origin.api.client.utility.Rendering
import com.temportalist.origin.api.common.resource.{EnumResource, IModDetails, IModResource}
import com.temportalist.origin.foundation.common.IMod
import com.temportalist.origin.internal.common.handlers.RegisterHelper
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.gameevent.TickEvent
import cpw.mods.fml.common.gameevent.TickEvent.Phase
import cpw.mods.fml.common.{FMLCommonHandler, Mod, SidedProxy}
import cpw.mods.fml.relauncher.{SideOnly, Side}
import net.minecraft.client.renderer.entity.Render
import net.minecraft.client.renderer.texture.{TextureUtil, SimpleTexture}
import net.minecraft.util.ResourceLocation

/**
 *
 *
 * @author TheTemportalist
 */
@Mod(modid = MorphAdditions.modid, name = MorphAdditions.modname, version = MorphAdditions.version,
	guiFactory = MorphAdditions.clientProxy,
	modLanguage = "scala",
	dependencies = "required-after:Morph@[0.9,);"//"required-after:origin@[3.3,);after:Waila@[1,);"
)
object MorphAdditions extends IMod with IModResource {

	final val modid = "morphadditions"
	final val modname = "MorphAdditions"
	final val version = "@MOD_VERSION@"
	final val clientProxy = "com.temportalist.morphadditions.client.ProxyClient"
	final val serverProxy = "com.temportalist.morphadditions.server.ProxyServer"

	override def getDetails: IModDetails = this

	override def getModVersion: String = this.version

	override def getModName: String = this.modname

	override def getModid: String = this.modid

	@SidedProxy(clientSide = this.clientProxy, serverSide = this.serverProxy)
	var proxy: ProxyCommon = null

	@Mod.EventHandler
	def preInit(event: FMLPreInitializationEvent): Unit = {
		super.preInitialize(this, event, this.proxy, MAOptions)

		this.registerPackets(classOf[PacketKeyPressed])

		RegisterHelper.registerExtendedPlayer("morphedPlayer", classOf[MorphedPlayer],
			deathPersistance = true)
		RegisterHelper.registerCommand(CommandMorphA)

	}

	@Mod.EventHandler
	def init(event: FMLInitializationEvent): Unit = {
		super.initialize(event, this.proxy)

	}

	@Mod.EventHandler
	def postInit(event: FMLPostInitializationEvent): Unit = {
		super.postInitialize(event, this.proxy)
		Morph.register()
		Waila.post()

		this.loadResource("actionMan", (EnumResource.GUI, "running_man-256.png"))
		this.loadResource("actionManOutline", (EnumResource.GUI, "running_man_outline.png"))
	}

	@SubscribeEvent
	def tickingPlayer(event: TickEvent.PlayerTickEvent): Unit = {
		if (event.phase == Phase.START) this.proxy.tickPlayer(MAOptions.getMP(event.player))
	}

	@SideOnly(Side.CLIENT) // TODO, move this function to Origin.Rendering
	def doesTextureExist(resourceLocation: ResourceLocation): Boolean = {
		val textureManager = Rendering.mc.getTextureManager
		var textureObject = textureManager.getTexture(resourceLocation)
		if (textureObject == null) {
			textureObject = new SimpleTexture(resourceLocation)
			try textureObject.loadTexture(Rendering.mc.getResourceManager)
			catch {
				case exception: IOException => return false
				// no other cases because nothing else should be thrown
			}
			return true
		}
		textureObject != TextureUtil.missingTexture
	}

}
