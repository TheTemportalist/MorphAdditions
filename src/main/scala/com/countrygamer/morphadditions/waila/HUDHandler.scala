package com.countrygamer.morphadditions.waila

import java.util

import mcp.mobius.waila.api._
import morph.api.Api
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.{Entity, EntityList, EntityLivingBase}
import net.minecraft.util.StatCollector

/**
 *
 *
 * @author CountryGamer
 */
object HUDHandler extends IWailaEntityProvider {

	override def getWailaOverride(accessor: IWailaEntityAccessor,
			config: IWailaConfigHandler): Entity = {
		null
	}

	override def getWailaHead(entity: Entity, currenttip: util.List[String],
			accessor: IWailaEntityAccessor, config: IWailaConfigHandler): util.List[String] = {
		entity match {
			case player: EntityPlayer =>
				val morphedEntity: EntityLivingBase = Api
						.getMorphEntity(player.getCommandSenderName, true)
				if (morphedEntity != null) {
					currenttip.set(0, SpecialChars.WHITE + StatCollector.translateToLocal(
						"entity." + EntityList.getEntityString(morphedEntity) + ".name"))
				}
		}
		currenttip
	}

	override def getWailaBody(entity: Entity, currenttip: util.List[String],
			accessor: IWailaEntityAccessor, config: IWailaConfigHandler): util.List[String] = {
		currenttip
	}

	override def getWailaTail(entity: Entity, currenttip: util.List[String],
			accessor: IWailaEntityAccessor, config: IWailaConfigHandler): util.List[String] = {
		currenttip
	}

}
