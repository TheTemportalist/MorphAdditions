package com.temportalist.morphadditions.common.abilities

import java.util.Random

import com.temportalist.morphadditions.api.AbilityAction
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.{Entity, EntityLivingBase}
import net.minecraft.init.Blocks
import net.minecraft.util.MathHelper

/**
 *
 *
 * @author TheTemportalist
 */
class AbilitySummon() extends AbilityEject("Summon") {

	var entityClass: Class[_ <: EntityLivingBase] = null
	var hoardSize: Int = 0
	var radius: Int = 3

	override def getEntityClass(): Class[_ <: Entity] = {
		this.entityClass
	}

	override def trigger(player: EntityPlayer): Unit = {
		val originX: Int = MathHelper.floor_double(player.posX)
		val originY: Int = player.boundingBox.minY.asInstanceOf[Int]
		val originZ: Int = MathHelper.floor_double(player.posZ)
		for (i <- 0 until this.hoardSize) {
			val x: Int = originX + this.getRandom(-this.radius, this.radius)
			val z: Int = originZ + this.getRandom(-this.radius, this.radius)
			var y: Int = originY - radius
			do {
				if (y < 0) {
					y = 0
				}
				else {
					y += 1
				}
			}
			while (y <= originY + radius && player.worldObj.getBlock(x, y, z) != Blocks.air)
			val entity: Entity = this.getEntity(player)
			if (entity != null) {
				entity.setPosition(x + 0.5, y, z + 0.5)
				if (!player.worldObj.isRemote)
					player.worldObj.spawnEntityInWorld(entity)
			}
		}
	}

	def getRandom(min: Int, max: Int): Int = {
		new Random().nextInt(max - min) + min
	}

	override def parse(args: Array[String]): AbilityAction = {
		super.parse(Array[String]("0"))
		this.setArgs(args)

		try {
			this.entityClass = Class.forName(args(0)).asInstanceOf[Class[_ <: EntityLivingBase]]
		}
		catch {
			case e: ClassNotFoundException =>
				e.printStackTrace()
		}

		try {
			this.hoardSize = args(1).toInt
		}
		catch {
			case e: NumberFormatException =>
				e.printStackTrace()
		}

		try {
			this.radius = args(2).toInt
		}
		catch {
			case e: NumberFormatException =>
				e.printStackTrace()
		}

		this
	}

}
