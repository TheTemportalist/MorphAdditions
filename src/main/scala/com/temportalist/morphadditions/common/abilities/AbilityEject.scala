package com.temportalist.morphadditions.common.abilities

import java.lang.reflect.InvocationTargetException

import com.temportalist.morphadditions.api.AbilityAction
import com.temportalist.origin.library.common.utility.Cursor
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.{MovingObjectPosition, Vec3}
import net.minecraft.world.World

/**
 *
 *
 * @author TheTemportalist
 */
abstract class AbilityEject() extends AbilityAction() {

	var targetRadius: Double = 100.0D

	def getEntityClass(): Class[_ <: Entity]

	def getEntity(player: EntityPlayer): Entity = {
		var entity: Entity = null
		if (this.getEntityClass() != null) {
			try {
				entity = this.getEntityClass().getConstructor(classOf[World])
						.newInstance(player.worldObj)
			}
			catch {
				case e: NoSuchMethodException =>
					e.printStackTrace()
				case e: SecurityException =>
					e.printStackTrace()
				case e: InstantiationException =>
					e.printStackTrace()
				case e: IllegalAccessException =>
					e.printStackTrace()
				case e: IllegalArgumentException =>
					e.printStackTrace()
				case e: InvocationTargetException =>
					e.printStackTrace()
			}
		}
		entity
	}

	override def trigger(player: EntityPlayer): Unit = {
		val entity: Entity = this.getEntity(player)
		if (entity != null && !player.worldObj.isRemote)
			player.worldObj.spawnEntityInWorld(entity)
	}

	override def parse(args: Array[String]): AbilityAction = {
		super.parse(args)

		try {
			this.targetRadius = args(0).toDouble
		}
		catch {
			case e: NumberFormatException =>
				e.printStackTrace()
		}

		this
	}

	def getTargetCoords(player: EntityPlayer): Array[Double] = {
		val mop: MovingObjectPosition = Cursor.rayTrace(player, this.targetRadius)

		if (mop == null)
			return null

		var x: Double = mop.hitVec.xCoord
		var y: Double = mop.hitVec.yCoord
		var z: Double = mop.hitVec.zCoord

		if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
			val point: Vec3 = Cursor
					.getNewCoordsFromSide(mop.blockX, mop.blockY, mop.blockZ, mop.sideHit)
			x = point.xCoord
			y = point.yCoord
			z = point.zCoord
		}

		var entityHeight: Double = 0.0D
		if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
			entityHeight = mop.entityHit.height
		}

		Array[Double](x, y, z, entityHeight)
	}

}