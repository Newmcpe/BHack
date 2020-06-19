package ru.newmcpe.bhack.util

import ru.newmcpe.bhack.BHack.clientDLL
import ru.newmcpe.bhack.BHack.gameHeight
import ru.newmcpe.bhack.BHack.gameWidth
import ru.newmcpe.bhack.api.entites.LocalPlayer
import ru.newmcpe.bhack.offsets.ClientOffsets.dwViewMatrix


private val viewMatrix = Array(4) { DoubleArray(4) }

fun worldToScreen(from: Vector, vOut: Vector) = try {
    val buffer = clientDLL.read(dwViewMatrix, 4 * 4 * 4)!!
    var offset = 0
    for (row in 0..3) for (col in 0..3) {
        val value = buffer.getFloat(offset.toLong())
        viewMatrix[row][col] = value.toDouble()
        offset += 4
    }

    vOut.x = viewMatrix[0][0] * from.x + viewMatrix[0][1] * from.y + viewMatrix[0][2] * from.z + viewMatrix[0][3]
    vOut.y = viewMatrix[1][0] * from.x + viewMatrix[1][1] * from.y + viewMatrix[1][2] * from.z + viewMatrix[1][3]

    val w = viewMatrix[3][0] * from.x + viewMatrix[3][1] * from.y + viewMatrix[3][2] * from.z + viewMatrix[3][3]

    if (!w.isNaN() && w >= 0.01F) {
        val invw = 1.0 / w
        vOut.x *= invw
        vOut.y *= invw

        val width = gameWidth
        val height = gameHeight

        var x = width / 2.0
        var y = height / 2.0

        x += 0.5 * vOut.x * width + 0.5
        y -= 0.5 * vOut.y * height + 0.5

        vOut.x = x
        vOut.y = y

        true
    } else {
        false
    }
} catch (e: Exception) {
    e.printStackTrace()
    false
}

fun toScreen(vec: Vec3f, viewMatrix: Array<FloatArray>, player: LocalPlayer): ScreenPos {
    var x = viewMatrix[0][0] * vec.x + viewMatrix[0][1] * vec.z + viewMatrix[0][2] * vec.y + viewMatrix[0][3]
    var y = viewMatrix[1][0] * vec.x + viewMatrix[1][1] * vec.z + viewMatrix[1][2] * vec.y + viewMatrix[1][3]
    val w = viewMatrix[3][0] * vec.x + viewMatrix[3][1] * vec.z + viewMatrix[3][2] * vec.y + viewMatrix[3][3].toDouble()

    //if (Double.isNaN(w) || w < 0.01F) return null;
    val invw = 1.0 / w
    x *= invw.toFloat()
    y *= invw.toFloat()
    var x2 = gameWidth / 2.0
    var y2 = gameHeight / 2.0
    x2 += 0.5 * x * gameWidth + 0.5
    y2 -= 0.5 * y * gameHeight + 0.5
    var visible = true
    val diffAngle = Vec2f(0F, 0F)
    val myPos: Vec3f = player.getPos().add(player.getViewOffsets())
    val playerAngles: Vec3f = player.getViewAngles()
    val angles = Vec2f(playerAngles.y, playerAngles.x)
    calcAngles(myPos, vec, diffAngle)
    val distanceFromCrosshair: Float =
        Math.abs(MathHelper.normalizeAngle(MathHelper.distanceBetweenPoints(diffAngle, angles)))
    if (distanceFromCrosshair > 90f) visible = false
    val pos = Vec2f(x2.toFloat(), y2.toFloat())
    return ScreenPos(pos, visible)
}

fun calcAngles(
    src: Vec3f,
    dst: Vec3f,
    angles: Vec2f
) {
    val delta =
        doubleArrayOf((src.x - dst.x).toDouble(), (src.z - dst.z).toDouble(), (src.y - dst.y).toDouble())
    val hyp = Math.sqrt(delta[0] * delta[0] + delta[1] * delta[1])
    angles.x = Math.toDegrees(Math.asin(delta[2] / hyp)).toFloat() // pitch
    angles.y = Math.toDegrees(Math.atan(delta[1] / delta[0])).toFloat() // yaw
    if (delta[0] >= 0.0) {
        angles.y += 180.0f
    }
}


class ScreenPos(var vec: Vec2f, var isVisible: Boolean)