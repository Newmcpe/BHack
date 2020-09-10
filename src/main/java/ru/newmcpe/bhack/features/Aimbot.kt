package ru.newmcpe.bhack.features

import org.jnativehook.mouse.NativeMouseEvent
import ru.newmcpe.bhack.api.entites.Entity
import ru.newmcpe.bhack.api.entites.EntityManager
import ru.newmcpe.bhack.api.entites.LocalPlayer
import ru.newmcpe.bhack.api.features.Feature
import ru.newmcpe.bhack.config.ConfigVars
import ru.newmcpe.bhack.util.MathHelper
import ru.newmcpe.bhack.util.Vector
import ru.newmcpe.bhack.util.closestToCrosshair
import kotlin.math.asin
import kotlin.math.atan
import kotlin.math.sqrt


class Aimbot : Feature("Aimbot") {

    private var lastTimeChecked = System.currentTimeMillis()

    private var state = false
    private var findNewTarget = true

    private var target: Entity? = null

    init {
        enabled = true
    }


    override fun mousePressed(e: NativeMouseEvent) {
        if (e.button == NativeMouseEvent.BUTTON1) {
            state = true
            findNewTarget = true
        }
    }

    override fun mouseReleased(e: NativeMouseEvent) {
        if (e.button == NativeMouseEvent.BUTTON1) {
            state = false
            findNewTarget = false
        }
    }

    override fun update() {
        try {
            val player: LocalPlayer = EntityManager.getLocalPlayer()
            if (state) {
                if (findNewTarget) {
                    target = closestToCrosshair(player)
                    findNewTarget = false
                }
                if (target == null || !EntityManager.isEntityValid(target!!)) return
                val angles = Vector()
                val myPos = player.getPos().clone() + player.getViewOffsets().clone()
                val targetPos = target!!.getBonePos(ConfigVars.AIMBOT_BONE)
                calcAngles(myPos, targetPos, angles)
                val currentAngles =
                    Vector(player.viewAngles.y, player.viewAngles.x)
                val diffYaw = MathHelper.differenceBetweenAngles(currentAngles.x.toFloat(), angles.x.toFloat())
                val diffPitch = MathHelper.differenceBetweenAngles(currentAngles.y.toFloat(), angles.y.toFloat())
                val distanceFromCrosshair =
                    sqrt(diffYaw * diffYaw + diffPitch * diffPitch.toDouble()).toFloat()

                angles.finalize(currentAngles, ConfigVars.AIMBOT_SMOOTH / 100)
                if (angles.valid() && distanceFromCrosshair < ConfigVars.AIMBOT_FOV) {
                    player.viewAngles = angles
                }
            }
            lastTimeChecked = System.currentTimeMillis()
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

    fun calcAngles(src: Vector, dst: Vector, angles: Vector) {
        val delta = src - dst
        val hyp = sqrt(delta.x * delta.x + delta.y * delta.y)
        angles.x = Math.toDegrees(asin(delta.z / hyp)) // pitch
        angles.y = Math.toDegrees(atan(delta.y / delta.x))// yaw
        if (delta.x >= 0.0) {
            angles.y += 180.0f
        }
    }
}
