package com.temportalist.morphadditions.client.gui

import com.temportalist.morphadditions.common.{MAOptions, MorphAdditions, MorphedPlayer}
import com.temportalist.origin.api.client.utility.Rendering
import com.temportalist.origin.api.client.utility.Rendering.Gl
import cpw.mods.fml.common.eventhandler.{EventPriority, SubscribeEvent}
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType
import org.lwjgl.opengl.GL11

/**
 *
 *
 * @author TheTemportalist
 */
@SideOnly(Side.CLIENT)
object HUDOverlay {//} extends Gui {

	@SubscribeEvent(priority = EventPriority.NORMAL)
	def renderOverlay(event: RenderGameOverlayEvent.Post): Unit = {
		// MorphAdditions.getResource("cooldown")
		val mPlayer: MorphedPlayer = MAOptions.getMP(Minecraft.getMinecraft.thePlayer)
		if (event.`type` == ElementType.TEXT && mPlayer.getCoolDown >= 0) {
			val width: Int = event.resolution.getScaledWidth
			val height: Int = event.resolution.getScaledHeight
			Gl.push()
			Gl.color(1.0F, 1.0F, 1.0F, 1.0F)
			Gl.enable(GL11.GL_LIGHTING, isOn = false)
			Gl.enable(GL11.GL_BLEND, isOn = true)

			//val w: Int = 16
			//val h: Int = 16
			//val x: Int = width / 2 - w / 2
			//val y: Int = height / 2 - h / 2
			//UtilRender.bindResource(this.icon)
			//this.drawTexturedModalRect(x, y, 0, 0, w, h)

			val timeLeft: String = mPlayer.getCoolDownTime
			//val x1: Int = width / 2 - this.mc.fontRenderer.getStringWidth(timeLeft)
			//val y1: Int = y + h + 1
			//MorphAdditions.log(timeLeft)
			val x1: Int = width / 4 + 15 - Rendering.mc.fontRenderer.getStringWidth(timeLeft) - 2
			val y1: Int = height - 37
			Rendering.mc.fontRenderer.drawString(timeLeft, x1, y1, 0x000000)
			Gl.pop()
		}
	}

}
