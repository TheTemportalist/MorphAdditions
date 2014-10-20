package com.temportalist.morphadditions.common.abilities

import com.temportalist.morphadditions.api.AbilityAction
import com.temportalist.origin.library.common.lib.NameParser
import com.temportalist.origin.library.common.utility.Drops
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack

/**
 *
 *
 * @author TheTemportalist
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
