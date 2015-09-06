package com.temportalist.morphadditions.common

import java.io.{File, FileNotFoundException, IOException}
import java.nio.charset.Charset

import com.google.common.io.Files
import com.google.gson._
import com.temportalist.origin.api.common.utility.{ThreadFetchOnlineFile, Json}
import com.temportalist.origin.foundation.common.register.OptionRegister
import com.temportalist.origin.internal.common.extended.ExtendedEntityHandler
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer

/**
 *
 *
 * @author TheTemportalist
 */
object MAOptions extends OptionRegister {

	def getMP(player: EntityPlayer): MorphedPlayer = {
		ExtendedEntityHandler.getExtended(player, classOf[MorphedPlayer])
				.asInstanceOf[MorphedPlayer]
	}

	var HUD_ticks: Int = 100

	override def hasDefaultConfig: Boolean = false

	override def getConfigDirectory(configDir: File): File = {
		val morphAddition: File = new File(configDir, "Morph Additions")
		if (!morphAddition.exists()) morphAddition.mkdir()
		morphAddition
	}

	override def customizeConfiguration(event: FMLPreInitializationEvent): Unit = {
		val mapAbilities: File = new File(
			this.getConfigDirectory(event.getModConfigurationDirectory), "MapAbilities.json")
		var onlineAbilities: JsonObject = null
		if (!mapAbilities.exists()) {
			try {
				var onlineAbilitiesString = this.fetchOnlineAbilities
				if (onlineAbilitiesString != null)
					onlineAbilities = Json.parser.parse(onlineAbilitiesString).getAsJsonObject
				else onlineAbilitiesString = Json.toReadableString(this.getDefaultAbilities.toString)
				Files.write(onlineAbilitiesString, mapAbilities, Charset.defaultCharset)
			}
			catch {
				case e: IOException => e.printStackTrace()
			}
		}
		if (mapAbilities.exists()) {
			// Read and parse the local config json file
			var configAbilityJson: JsonObject = new JsonObject()
			try {
				configAbilityJson = Json.getJson(mapAbilities).getAsJsonObject
			}
			catch {
				case e: FileNotFoundException => e.printStackTrace()
			}
			// if online abilities were not already pulled this runtime, then pull and parse
			if (onlineAbilities == null) {
				val onlineAbilitiesString = this.fetchOnlineAbilities
				if (onlineAbilitiesString != null)
					onlineAbilities = Json.parser.parse(onlineAbilitiesString).getAsJsonObject
			}
			if (onlineAbilities != null) {
				// compare the two jsons (local config and online resource)
				// if the local is missing content from online, add them to local json object
				// and write them to the file
				var hasChangedLocal = false
				val onlineAbilityIterator = onlineAbilities.entrySet().iterator()
				while (onlineAbilityIterator.hasNext) {
					val modEntry = onlineAbilityIterator.next()
					val modEntryObject = modEntry.getValue
					if (!configAbilityJson.has(modEntry.getKey)) {
						configAbilityJson.add(modEntry.getKey, modEntryObject)
						if (!hasChangedLocal) hasChangedLocal = true
					}
					else {
						val configModEntry = configAbilityJson.get(modEntry.getKey).getAsJsonObject
						val modEntityIterator = modEntryObject.getAsJsonObject.entrySet().iterator()
						while (modEntityIterator.hasNext) {
							val entityElement = modEntityIterator.next()
							if (!configModEntry.has(entityElement.getKey)) {
								configModEntry.add(entityElement.getKey, entityElement.getValue)
								if (!hasChangedLocal) hasChangedLocal = true
							}
						}
					}
				}
				// if we have changed the local json object, then write changes to the file
				if (hasChangedLocal) {
					Files.write(Json.toReadableString(configAbilityJson.toString),
						mapAbilities, Charset.defaultCharset)
				}
			}
			// Load all values into active abilities
			val configIterator = configAbilityJson.entrySet().iterator()
			while (configIterator.hasNext) {
				val entityElementIterator = configIterator.next().getValue
						.getAsJsonObject.entrySet().iterator()
				while (entityElementIterator.hasNext) {
					val entityElement = entityElementIterator.next()
					try {
						val entityClass = Class.forName(entityElement.getKey)
						if (entityClass != null &&
								entityClass.isInstanceOf[Class[_ <: EntityLivingBase]]) {
							ApiHelper.mapAbilityByParameters(
								entityClass.asInstanceOf[Class[_ <: EntityLivingBase]],
								entityElement.getValue.getAsString)
						}
					}
					catch {
						case e: ClassCastException => e.printStackTrace()
					}
				}

			}
		}

		val mapHelper = new File(
			this.getConfigDirectory(event.getModConfigurationDirectory), "MappingHelp.txt")
		if (!mapHelper.exists()) {
			val text: String =
				"MapAbilities.json Tutorial\n" +
						"This is a short tutorial on the mappings and their format!\n" +
						"Each entity can have ONLY 1 trigger-able ability (AbilityAction). " +
						"Similar to Morph, the mappings are laid out like so in the config:\n\t" +
						"EXAMPLE:  \'fullClassName\': \"\'abilityName\'|\'minimumCooldown\',\'" +
						"maximumCooldown\'|\'arguments\'\n\n" +
						"So here is the break down if that was not simple enough :P\n" +
						"First, is the full class name. This is the KEY for the mapping, as is " +
						"shown in json file format.\n" +
						"EXAMPLE:\"net.minecraft.entity.passive.EntityPig\"\n" +
						"The value, then, is primarily the abilities mapped name. All inherent " +
						"abilities can be found in the default generated file.\n" +
						"After the name, is the minimum and maximum cooldown times. ¡!IN TICKS!¡\n" +
						"The former (minimum) can have minimum value of 0, and the latter " +
						"(maximum) can have a minimum value of 1. This would have the same " +
						"effect as leaving out the two integers, as follows:\n\t" +
						"EXAMPLE:  \'fullClassName\': \"\'abilityName\'|\'arguments\'\n\n" +
						"Finally, make sure you LOOK AT THE DEFAULT MAPPINGS. Using this tutorial" +
						"along with the default mappings should provide you with a good " +
						"understanding at how the config is setup. That said, here is a sample " +
						"and breakdown:\n\n" +
						"\"net.minecraft.entity.passive.EntityChicken\": " +
						"\"drop|6000,12000|minecraft:egg:0\"\n" +
						"This gives the mapping to the Minecraft Chicken entity. The ability is " +
						"the AbilityDrop (mapped using its name \"drop\").\n6000 is the minimum " +
						"cooldown, which is 5 minutes, and 12000 is the maximum cooldown, being " +
						"10 minutes.\nA random integer will be pulled between these two " +
						"(6000-11999 ticks) and will be set as teh cooldown when you trigger the " +
						"ability.\nFor this specific ability, the first arg is the item/block " +
						"name (using the format: \"modID:itemName:metadata\").\nFor this ability, " +
						"there are no other args.\n\n\n\n" +
						"The following are the args of all default abilities, " +
						"as well as each's name.\nAssume numerical numbers are integers unless specified.\n" +
						"name - args (separates by commas)\n" +
						"\tdrop - modid item mapping\n" +
						"\tsummon - class path for summon entity , number of entities to spawn , " +
						"the radius around the player in which the entities will spawn\n" +
						"\texplode - explosion radius (float)\n" +
						"\tteleport - the maximum distance for player to locate " +
						"(cursor position)(double)\n" +
						"\tejectLargeFireball - the maximum distance for player to locate " +
						"(cursor position)(double) , explosion radius\n" +
						"\tejectSmallFireball - the maximum distance for player to locate " +
						"(cursor position)(double)\n" +
						"\tejectSnowball - the maximum distance for player to locate " +
						"(cursor position)(double)\n"
			Files.write(text, mapHelper, Charset.defaultCharset)
		}

	}

	private def fetchOnlineAbilities: String = {
		ThreadFetchOnlineFile.fetchResource("https://raw.githubusercontent.com/"
				+ "TheTemportalist/MorphAdditions/tree/master/src/main/"
				+ "resources/assets/morphadditions/DefaultAbilities.json")
	}

	/*
	private def parseAbilityJson(jsonString: String): mutable.Map[String, String] = {
		val json = Json.parser.parse(jsonString)
		val abilityMap = mutable.Map[String, String]()
		try {
			val jsonIterator = json.getAsJsonObject.entrySet().iterator()
			while (jsonIterator.hasNext) {
				// Note, we do not care about the entry's key because its purpose is to easily
				//  sort the entries in the file for human readability
				val entry = jsonIterator.next()
				val entityToAbility = entry.getValue.getAsJsonObject
				val modEntityIterator = entityToAbility.entrySet().iterator()
				while (modEntityIterator.hasNext) {
					val entityEntry = modEntityIterator.next()
					abilityMap(entityEntry.getKey) = entityEntry.getValue.getAsString
				}
			}
		}
		catch {
			case e: Exception => e.printStackTrace()
		}
		abilityMap
	}
	*/

	/**
	 * Had been used to create the json object for config writing.
	 * Now moved to online handled system, much like ichun.Morph's system.
	 * @return
	 */
	@Deprecated
	private def getDefaultAbilities: JsonObject = {
		val jsonObject: JsonObject = new JsonObject

		jsonObject.addProperty(
			"net.minecraft.entity.passive.EntityChicken",
			"drop|6000,12000|minecraft:egg:0"
		)

		jsonObject.addProperty(
			"net.minecraft.entity.passive.EntityWolf",
			"summon|12000,24000|net.minecraft.entity.passive.EntityWolf,3,7"
		)

		jsonObject.addProperty(
			"net.minecraft.entity.monster.EntityZombie",
			"summon|12000,24000|net.minecraft.entity.monster.EntityZombie,6,7"
		)

		jsonObject.addProperty(
			"net.minecraft.entity.monster.EntityPigZombie",
			"summon|12000,24000|net.minecraft.entity.monster.EntityPigZombie,6,7"
		)

		jsonObject.addProperty(
			"net.minecraft.entity.monster.EntitySilverfish",
			"summon|1200,6000|net.minecraft.entity.monster.EntitySilverfish,5,3"
		)

		jsonObject.addProperty(
			"net.minecraft.entity.passive.EntityVillager",
			"summon|6000,12000|net.minecraft.entity.monster.EntityIronGolem,1,4"
		)

		jsonObject.addProperty(
			"net.minecraft.entity.monster.EntityCreeper",
			"explode|3F"
		)

		jsonObject.addProperty(
			"net.minecraft.entity.monster.EntityEnderman",
			"teleport|1200,2400|300.0D"
		)

		jsonObject.addProperty(
			"net.minecraft.entity.monster.EntityGhast",
			"ejectLargeFireball|60,200|64.0D,1"
		)

		jsonObject.addProperty(
			"net.minecraft.entity.monster.EntityBlaze",
			"ejectSmallFireball|16.0D"
		)

		jsonObject.addProperty(
			"net.minecraft.entity.monster.EntitySnowman",
			"ejectSnowball|16.0D"
		)

		jsonObject
	}

	override def register(): Unit = {}

}
