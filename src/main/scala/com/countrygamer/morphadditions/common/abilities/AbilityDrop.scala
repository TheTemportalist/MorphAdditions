package com.countrygamer.morphadditions.common.abilities

import com.countrygamer.cgo.library.common.lib.NameParser
import com.countrygamer.cgo.library.common.utility.Drops
import com.countrygamer.morphadditions.api.AbilityAction
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack

/**
 *
 *
 * @author CountryGamer
 */
class AbilityDrop() extends AbilityAction() {

	private var itemStack: ItemStack = null

	override def trigger(player: EntityPlayer): Unit = {
		if (!player.worldObj.isRemote) {
			Drops.spawnItemStack(player.worldObj, player.posX + 0.5, player.posY,
				player.posZ + 0.5,
				this.itemStack, player.worldObj.rand, 20)
		}
	}

	override def parse(args: Array[String]): AbilityAction = {
		super.parse(args)
		this.itemStack = NameParser.getItemStack(args(0))
		this
	}

}
