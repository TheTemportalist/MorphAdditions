package com.countrygamer.morphactions.common.abilities

import com.countrygamer.cgo.common.lib.{ItemMeta, ItemMetaHelper, NameParser}
import com.countrygamer.morphactions.api.AbilityAction
import com.countrygamer.morphactions.common.{MAOptions, MorphedPlayer}
import net.minecraft.entity.player.EntityPlayer

/**
 *
 *
 * @author CountryGamer
 */
class AbilityDrop() extends AbilityAction() {

	private var itemMeta: ItemMeta = null
	private var minTicks: Int = 0
	private var maxTicks: Int = 0

	override def trigger(player: EntityPlayer): Unit = {
		val mPlayer: MorphedPlayer = MAOptions.getMP(player)
		val ticksTillNextDrop: Int = mPlayer.getTag("drop").getInteger("ticksTillNextDrop")
		if (ticksTillNextDrop <= 0) {

		}
		else {

		}
	}

	override def parse(args: Array[String]): AbilityAction = {
		this.itemMeta = ItemMetaHelper.getFromStack(NameParser.getItemStack(args(0)))
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
