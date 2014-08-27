package com.countrygamer.morphadditions.client.gui

import com.countrygamer.cgo.common.lib.LogHelper
import com.countrygamer.morphadditions.common.{MAOptions, MorphAdditions, MorphedPlayer}
import cpw.mods.fml.common.eventhandler.{EventPriority, SubscribeEvent}
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType
import org.lwjgl.opengl.GL11

/**
 *
 *
 * @author CountryGamer
 */
object HUDOverlay extends Gui() {

	private val mc: Minecraft = Minecraft.getMinecraft
	private val icon: ResourceLocation = new
					ResourceLocation(MorphAdditions.pluginID, "textures/gui/cooldown.png")

	@SubscribeEvent(priority = EventPriority.NORMAL)
	def renderOverlay(event: RenderGameOverlayEvent.Post): Unit = {
		val mPlayer: MorphedPlayer = MAOptions.getMP(Minecraft.getMinecraft.thePlayer)
		if (event.`type` == ElementType.TEXT && mPlayer.getCooldown() >= 0) {
			val width: Int = event.resolution.getScaledWidth
			val height: Int = event.resolution.getScaledHeight
			GL11.glPushMatrix()
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F)
			GL11.glDisable(GL11.GL_LIGHTING)
			GL11.glEnable(GL11.GL_BLEND)

			//val w: Int = 16
			//val h: Int = 16
			//val x: Int = width / 2 - w / 2
			//val y: Int = height / 2 - h / 2
			//UtilRender.bindResource(this.icon)
			//this.drawTexturedModalRect(x, y, 0, 0, w, h)

			val timeLeft: String = mPlayer.getCooldownTime()
			//val x1: Int = width / 2 - this.mc.fontRenderer.getStringWidth(timeLeft)
			//val y1: Int = y + h + 1
			LogHelper.info(MorphAdditions.pluginName, timeLeft)
			val x1: Int = width / 4 + 15 - this.mc.fontRenderer.getStringWidth(timeLeft) - 2
			val y1: Int = height - 37
			this.mc.fontRenderer.drawString(timeLeft, x1, y1, 0x000000)

			GL11.glPopMatrix()
		}
	}

}
