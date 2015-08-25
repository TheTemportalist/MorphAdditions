package com.temportalist.morphadditions.client

import com.temportalist.morphadditions.common.MorphAdditions
import com.temportalist.origin.api.client.utility.Rendering
import com.temportalist.origin.foundation.client.EnumKeyCategory
import cpw.mods.fml.client.registry.ClientRegistry
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent
import modwarriors.notenoughkeys.api.{Api, KeyBindingPressedEvent}
import net.minecraft.client.settings.KeyBinding
import org.lwjgl.input.{Keyboard, Mouse}

/**
 * Created by TheTemportalist on 8/25/2015.
 */
object KeyBinder {

	val trigger = new KeyBinding(
		"key.trigger.desc", Keyboard.KEY_L, EnumKeyCategory.GAMEPLAY.getName)
	//ClientRegistry.registerKeyBinding(this.trigger)
	//Api.registerMod(MorphAdditions.getModName, this.trigger.getKeyDescription)

	@SubscribeEvent
	def keyInput(event: KeyInputEvent): Unit = {
		if (Rendering.mc.inGameHasFocus) {
			println("Internal " + this.trigger.getIsKeyPressed)
		}
	}

	@SubscribeEvent
	def keyInput(event: KeyBindingPressedEvent): Unit = {
		if (Rendering.mc.inGameHasFocus) {
			println("NotEKeys " + this.trigger.getIsKeyPressed)
		}
	}

	def isKeyDown(key: Int): Boolean = {
		if (key < 0) Mouse.isButtonDown(key + 100)
		else Keyboard.isKeyDown(key)
	}

}
