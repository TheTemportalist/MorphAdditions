package com.countrygamer.morphadditions.client

import com.countrygamer.cgo.common.network.PacketHandler
import com.countrygamer.cgo.wrapper.common.network.AbstractPacket
import com.countrygamer.morphadditions.common.MorphAdditions
import com.countrygamer.morphadditions.common.network.PacketKeyPressed
import cpw.mods.fml.client.registry.ClientRegistry
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.client.Minecraft
import net.minecraft.client.settings.KeyBinding
import org.lwjgl.input.Keyboard

/**
 *
 *
 * @author CountryGamer
 */
@SideOnly(Side.CLIENT)
object KeyHandler {

	private val triggerKey_Name: String = "key.triggerMorphAbility.name"
	private val triggerKey_Int: Int = Keyboard.KEY_L
	private var triggerKey_Key: KeyBinding = null

	{
		this.triggerKey_Key = new KeyBinding(this.triggerKey_Name, this.triggerKey_Int, "")
		ClientRegistry.registerKeyBinding(this.triggerKey_Key)

	}

	@SubscribeEvent
	def onKeyInput(event: KeyInputEvent): Unit = {
		if (!Minecraft.getMinecraft.inGameHasFocus) return

		if (this.triggerKey_Key.isPressed) {
			val packet: AbstractPacket = new PacketKeyPressed()
			PacketHandler.sendToServer(MorphAdditions.pluginID, packet)
		}

	}

}
