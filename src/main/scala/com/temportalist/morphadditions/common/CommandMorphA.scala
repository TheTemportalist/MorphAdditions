package com.temportalist.morphadditions.common

import com.temportalist.morphadditions.addon.Morph
import com.temportalist.origin.foundation.common.utility.Players
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

		var player: EntityPlayer = sender.asInstanceOf[EntityPlayer]
		if (args.length >= 2) Players.getPlayer(args(1)) match {
			case p: EntityPlayer => player = p
			case _ =>
		}


		if (args(0).equals("force") && args.length == 3 || args.length == 4) {
			val entity: EntityLivingBase = this.getEntity(player.worldObj,
				this.getEntityTag(sender, args)).asInstanceOf[EntityLivingBase]
			if (entity != null) Api.forceMorph(player.asInstanceOf[EntityPlayerMP], entity)
		}
		if (args(0).equals("give") && args.length == 3 || args.length == 4) {
			val entity: EntityLivingBase = this.getEntity(player.worldObj,
				this.getEntityTag(sender, args)).asInstanceOf[EntityLivingBase]
			if (entity != null) Morph.addMorph(player.asInstanceOf[EntityPlayerMP], entity)
		}
		if (args(0).equals("setCoolDown") && args.length == 2) {
			try {
				MAOptions.getMP(player).setCoolDownTicks(args(1).toInt)
			}
			catch {
				case e: Exception =>
			}
		}
		if (args(0).equals("clearCoolDown")) {
			MAOptions.getMP(player).clearCoolDown()
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
							Players.message(player, "Invalid tag")
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
