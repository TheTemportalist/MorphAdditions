package com.temportalist.morphadditions.client

import com.temportalist.morphadditions.common.network.PacketKeyPressed
import com.temportalist.morphadditions.common.{MAOptions, MorphAdditions, MorphedPlayer}
import com.temportalist.origin.api.client.utility.Rendering
import com.temportalist.origin.api.common.resource.IModDetails
import com.temportalist.origin.foundation.client.{EnumKeyCategory, IKeyBinder}
import cpw.mods.fml.common.ObfuscationReflectionHelper
import cpw.mods.fml.relauncher.{Side, SideOnly}
import modwarriors.notenoughkeys.api.Api
import net.minecraft.client.settings.KeyBinding
import org.lwjgl.input.Keyboard

/**
 *
 *
 * @author TheTemportalist
 */
@SideOnly(Side.CLIENT)
object KeyHandler extends IKeyBinder {

	override def getMod: IModDetails = MorphAdditions

	val trigger = this.makeKeyBinding(
		"key.triggerMorphAbility.name", Keyboard.KEY_L, EnumKeyCategory.GAMEPLAY)

	override def onKeyPressed(keyBinding: KeyBinding): Unit = {
		keyBinding match {
			case this.trigger => if (Rendering.mc.inGameHasFocus)
				MAOptions.getMP(Rendering.mc.thePlayer) match {
					case morphed: MorphedPlayer => new PacketKeyPressed().sendToServer()
					case _ =>
				}
			case _ =>
		}
	}

}
