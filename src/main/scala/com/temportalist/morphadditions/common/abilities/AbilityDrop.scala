package com.temportalist.morphadditions.common.abilities

import com.temportalist.morphadditions.api.AbilityAction
import com.temportalist.morphadditions.common.MorphAdditions
import com.temportalist.origin.api.common.lib.{NameParser, V3O}
import com.temportalist.origin.api.common.resource.EnumResource
import com.temportalist.origin.api.common.utility.Stacks
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack

/**
 *
 *
 * @author TheTemportalist
 */
class AbilityDrop() extends AbilityAction("Drop") {

	private var itemStack: ItemStack = null

	override def trigger(player: EntityPlayer): Unit = {
		if (!player.worldObj.isRemote) {
			Stacks.spawnItemStack(player.worldObj, new V3O(player) + V3O.CENTER.suppressedYAxis(),
				this.itemStack, player.worldObj.rand, 20)
		}
	}

	override def parse(args: Array[String]): AbilityAction = {
		super.parse(args)
		this.itemStack = NameParser.getItemStack(args(0))
		this
	}

}
