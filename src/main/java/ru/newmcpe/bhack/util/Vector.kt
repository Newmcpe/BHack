package ru.newmcpe.bhack.util

import kotlin.math.sqrt

data class Vector(@JvmField var x: Double = 0.0, @JvmField var y: Double = 0.0, @JvmField var z: Double = 0.0) {

    fun set(x: Double, y: Double, z: Double) = apply {
        this.x = x
        this.y = y
        this.z = z
    }

    fun invalid() = x == 0.0 && y == 0.0 && z == 0.0

    fun valid() = !invalid()

    fun clone(): Vector {
        return Vector(x, y, z)
    }

    operator fun minus(vec: Vector): Vector {
        x -= vec.x
        y -= vec.y
        z -= vec.z
        return this
    }

    operator fun plus(vec: Vector): Vector {
        x += vec.x
        y += vec.y
        z += vec.z
        return this
    }

    operator fun times(times: Float): Vector {
        x *= times
        y *= times
        z *= times
        return this
    }

    private fun normalize() = apply {
        if (x != x) x = 0.0
        if (y != y) y = 0.0

        if (x > 89) x = 89.0
        if (x < -89) x = -89.0

        while (y > 180) y -= 360
        while (y <= -180) y += 360

        if (y > 180) y = 180.0
        if (y < -180F) y = -180.0

        z = 0.0
    }

    fun distance(o: Vector): Double {
        return Math.sqrt(
            Math.pow(x - o.x, 2.0) + Math.pow(y - o.y, 2.0)
                    + Math.pow(z - o.z, 2.0)
        )
    }

    operator fun div(times: Int): Vector {
        x /= times
        y /= times
        z /= times
        return this
    }

    fun finalize(orig: Vector, strictness: Double) {
        x -= orig.x
        y -= orig.y
        z = 0.0
        normalize()

        x = orig.x + x * strictness
        y = orig.y + y * strictness
        normalize()
    }

    fun length(): Double = sqrt(x * x * y * y * z * z)

}