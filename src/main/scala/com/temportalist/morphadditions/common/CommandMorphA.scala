package com.temportalist.morphadditions.common

import com.temportalist.morphadditions.addon.Morph
import com.temportalist.origin.library.common.utility.Player
import morph.api.Api
import net.minecraft.command.{CommandBase, ICommandSender}
import net.minecraft.entity.player.{EntityPlayer, EntityPlayerMP}
import net.minecraft.entity.{Entity, EntityList, EntityLivingBase}
import net.minecraft.nbt._
import net.minecraft.util.IChatComponent
import net.minecraft.world.World

/**
 *
 *
 * @author TheTemportalist
 */
object CommandMorphA extends CommandBase {

	override def getCommandName: String = {
		"morpha"
	}

	override def getCommandUsage(sender: ICommandSender): String = {
		"commands.morphadditions:morpha.usage"
	}

	override def processCommand(sender: ICommandSender, args: Array[String]): Unit = {
		if (!(args.length >= 3 && args.length <= 4)) {
			return
		}

		val player: EntityPlayer = Player.getPlayer(args(1))
		val entityTag: NBTTagCompound = this.getEntityTag(sender, args)

		val entity: EntityLivingBase = this.getEntity(player.worldObj, entityTag)
				.asInstanceOf[EntityLivingBase]
		if (entity == null) {
			return
		}

		if (args(0).equals("force")) {

			Api.forceMorph(player.asInstanceOf[EntityPlayerMP], entity)
		}
		if (args(0).equals("give")) {
			Morph.addMorph(player.asInstanceOf[EntityPlayerMP], entity)
		}
	}

	def getEntityTag(sender: ICommandSender, args: Array[String]): NBTTagCompound = {
		val name: String = args(2)
		val data: String =
			if (args.length == 4)
				args(3)
			else
				null
		var entityTag: NBTTagCompound = new NBTTagCompound
		if (data != null) {
			val ichatcomponent: IChatComponent = CommandBase.func_147178_a(sender, args, 3)
			try {
				val nbtBase: NBTBase = JsonToNBT.func_150315_a(ichatcomponent.getUnformattedText)
				if (!nbtBase.isInstanceOf[NBTTagCompound]) {
					sender match {
						case player: EntityPlayer =>
							Player.message(player, "Invalid tag")
						case _ =>
					}
					return null
				}
				entityTag = nbtBase.asInstanceOf[NBTTagCompound]
			}
			catch {
				case e: NBTException =>
					e.printStackTrace()
			}
		}
		entityTag.setString("id", name)
		entityTag
	}

	def getEntity(world: World, entityTag: NBTTagCompound): Entity = {
		EntityList.createEntityFromNBT(entityTag, world)
	}

}
