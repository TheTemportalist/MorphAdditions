package com.countrygamer.morphactions.common

import java.io.{File, FileReader}
import java.nio.charset.Charset

import com.countrygamer.cgo.common.lib.JsonHelper
import com.countrygamer.cgo.wrapper.common.extended.ExtendedEntityHandler
import com.countrygamer.cgo.wrapper.common.registries.OptionRegister
import com.google.gson._
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer

/**
 *
 *
 * @author CountryGamer
 */
object MAOptions extends OptionRegister() {

	def getMP(player: EntityPlayer): MorphedPlayer = {
		ExtendedEntityHandler.getExtended(player, classOf[MorphedPlayer])
				.asInstanceOf[MorphedPlayer]
	}

	private final val GSON: Gson = new Gson
	private final val JSONPARSER: JsonParser = new JsonParser

	override def getConfigDirectory(configDir: File): File = {
		val morphAddition: File = new File(configDir, "Morph Additions")
		if (!morphAddition.exists()) {
			morphAddition.mkdir()
		}
		morphAddition
	}

	override def hasCustomConfiguration: Boolean = {
		true
	}

	override def customizeConfiguration(event: FMLPreInitializationEvent): Unit = {
		val mapAbilities: File = new
						File(this.getConfigDirectory(event.getModConfigurationDirectory),
							"MapAbilities.json")
		if (!mapAbilities.exists()) {
			val formattedString: String = JsonHelper
					.toReadableString(GSON.toJson(this.getDefaultAbilities()))
			try {
				com.google.common.io.Files
						.write(formattedString, mapAbilities,
				            Charset.defaultCharset)
			}
			catch {
				case e: java.io.IOException => {
					e.printStackTrace
				}
			}
		}
		if (mapAbilities.exists()) {
			var jsonObject: JsonObject = new JsonObject()
			try {
				jsonObject = this.JSONPARSER.parse(new FileReader(mapAbilities)).getAsJsonObject
			}
			catch {
				case e: java.io.FileNotFoundException => {
					e.printStackTrace
				}
			}

			val iterator = jsonObject.entrySet().iterator()
			while (iterator.hasNext) {
				val key: String = iterator.next().getKey
				try {
					val entityClass: Class[_ <: EntityLivingBase] = Class.forName(key)
							.asInstanceOf[Class[_ <: EntityLivingBase]]

					val abilityString: String = jsonObject.get(key).getAsString

					ApiHelper.mapAbilityByParameters(entityClass, abilityString)

				}
				catch {
					case e: ClassCastException =>
						e.printStackTrace()
				}
			}

		}

	}

	private def getDefaultAbilities(): JsonObject = {
		val jsonObject: JsonObject = new JsonObject

		jsonObject.addProperty(
			"net.minecraft.entity.passive.EntityChicken",
			"drop|net.minecraft.item.ItemEgg,6000,12000")

		//jsonObject.addProperty(
		// "net.minecraft.entity.passive.EntityWolf",
		//	"summon|net.minecraft.entity.passive.EntityWolf")

		jsonObject.addProperty(
			"net.minecraft.entity.monster.EntityBlaze",
			"eject|net.minecraft.entity.projectile.EntitySmallFireball")

		jsonObject.addProperty(
			"net.minecraft.entity.monster.EntityCreeper",
			"explode|3F")

		jsonObject.addProperty(
			"net.minecraft.entity.monster.EntityEnderman",
			"teleport")

		jsonObject.addProperty(
			"net.minecraft.entity.monster.EntityGhast",
			"eject|net.minecraft.entity.projectile.EntityLargeFireball")

		jsonObject.addProperty(
			"net.minecraft.entity.monster.EntitySilverfish",
			"summon")

		jsonObject.addProperty(
			"net.minecraft.entity.monster.EntitySnowman",
			"eject|net.minecraft.entity.projectile.EntitySnowball")

		jsonObject.addProperty(
			"net.minecraft.entity.boss.EntityWither",
			"eject|net.minecraft.entity.projectile.EntityWitherSkull")

		jsonObject.addProperty(
			"net.minecraft.entity.monster.EntityZombie",
			"summon|")

		jsonObject.addProperty(
			"net.minecraft.entity.monster.EntityPigZombie",
			"summon|")

		jsonObject.addProperty(
			"net.minecraft.entity.passive.EntityVillager",
			"summon|")

		jsonObject
	}

	override def register(): Unit = {

	}

	@SideOnly(cpw.mods.fml.relauncher.Side.CLIENT)
	override def getGuiConfigClass: Class[_ <: net.minecraft.client.gui.GuiScreen] = {
		classOf[com.countrygamer.morphactions.client.gui.configFactory.GuiConfig]
	}

}
