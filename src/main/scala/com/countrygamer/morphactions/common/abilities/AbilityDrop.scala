package com.countrygamer.morphactions.common.abilities

import com.countrygamer.cgo.common.lib.NameParser
import com.countrygamer.cgo.common.lib.util.UtilDrops
import com.countrygamer.morphactions.api.AbilityAction
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack

/**
 *
 *
 * @author CountryGamer
 */
class AbilityDrop() extends AbilityAction() {

	private var itemStack: ItemStack = null
	private var minTicks: Int = 0
	private var maxTicks: Int = 0

	override def trigger(player: EntityPlayer): Unit = {
		if (!player.worldObj.isRemote) {
			UtilDrops.spawnItemStack(player.worldObj, player.posX, player.posY, player.posZ,
				this.itemStack, player.worldObj.rand, 10)
		}
	}

	override def parse(args: Array[String]): AbilityAction = {
		this.itemStack = NameParser.getItemStack(args(0))
		try {
			this.minTicks = Integer.parseInt(args(1))
			this.maxTicks = Integer.parseInt(args(2))
		}
		catch {
			case e: NumberFormatException =>
				e.printStackTrace()
		}

		this
	}

	override def copy(): AbilityAction = {
		new AbilityDrop().parse(this.getArgs())
	}

}
