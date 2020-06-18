package ru.newmcpe.bhop.api.entites

import ru.newmcpe.bhop.Bhop.clientDLL
import ru.newmcpe.bhop.Bhop.csgo
import ru.newmcpe.bhop.offsets.ClientOffsets
import ru.newmcpe.bhop.offsets.ClientOffsets.dwEntityList
import ru.newmcpe.bhop.offsets.ClientOffsets.dwLocalPlayer
import ru.newmcpe.bhop.offsets.ClientOffsets.dwRadarBase
import ru.newmcpe.bhop.offsets.ClientOffsets.dwViewAngles
import ru.newmcpe.bhop.offsets.netvars.NetVarOffsets
import ru.newmcpe.bhop.offsets.netvars.NetVarOffsets.dwBoneMatrix
import ru.newmcpe.bhop.offsets.netvars.NetVarOffsets.fFlags
import ru.newmcpe.bhop.offsets.netvars.NetVarOffsets.iHealth
import ru.newmcpe.bhop.offsets.netvars.NetVarOffsets.iTeamNum
import ru.newmcpe.bhop.offsets.netvars.NetVarOffsets.vecOrigin
import ru.newmcpe.bhop.offsets.netvars.NetVarOffsets.vecViewOffset
import ru.newmcpe.bhop.util.Vec3f
import ru.newmcpe.bhop.util.uint

open class Entity(
    open val pointer: Long
) {
    fun fFlags() = csgo.int(pointer + fFlags)

    fun isOnGround() = fFlags() and 1 == 1

    fun getHealth() = csgo.int(pointer + iHealth)

    fun getGlowIndex() = csgo.int(pointer + 0xA438)

    fun getTeam() = csgo.uint(pointer + iTeamNum)

    fun getArmor() = csgo.int(pointer + NetVarOffsets.bSpotted)

    fun getPos(): Vec3f {
        val x: Float = csgo.float(pointer + vecOrigin)
        val z: Float = csgo.float(pointer + vecOrigin + 0x4)
        val y: Float = csgo.float(pointer + vecOrigin + 0x8)
        return Vec3f(x, y, z)
    }

    fun getViewOffsets(): Vec3f {
        val x = csgo.float(pointer + vecViewOffset)
        val y = csgo.float(pointer + vecViewOffset + 0x4)
        val z = csgo.float(pointer + vecViewOffset + 0x8)

        return Vec3f(x, y, z)
    }

    fun getPitch() = csgo.float(pointer + dwViewAngles)

    fun getYaw() = csgo.float(pointer + dwViewAngles + 0x4)

    fun getRoll() = csgo.float(pointer + dwViewAngles + 0x8)

    fun getViewAngles() = Vec3f(getYaw(), getPitch(), getRoll());

    fun getName(): String {
        val radarBase = clientDLL.uint(dwRadarBase)
        val radar = csgo.uint(radarBase + 0x74)
        val id = EntityManager.getEntities().indexOf(this)
        return csgo.read(radar + 0x300 + (0x174 * (id - 1)), 32, false)?.getString(0) ?: "Newmcpe is gay"
    }

    fun bone(offset: Int, boneID: Int = 8, boneMatrix: Long = boneMatrix()) =
        csgo.float(boneMatrix + ((0x30 * boneID) + offset)).toDouble()

    fun glow(r: Float, g: Float, b: Float, a: Float) {
        val glowObjectManager = clientDLL.int(ClientOffsets.dwGlowObject)
        val glowIndex = getGlowIndex()

        csgo[glowObjectManager + (glowIndex * 0x38) + 0x4] = r
        csgo[glowObjectManager + (glowIndex * 0x38) + 0x8] = g
        csgo[glowObjectManager + (glowIndex * 0x38) + 0xC] = b
        csgo[glowObjectManager + (glowIndex * 0x38) + 0x10] = a

        csgo[glowObjectManager + (glowIndex * 0x38) + 0x24] = true
        csgo[glowObjectManager + (glowIndex * 0x38) + 0x25] = false
    }

    private fun boneMatrix() = csgo.uint(pointer + dwBoneMatrix)

    override fun equals(other: Any?): Boolean {
        if (other is Entity) {
            if (this.pointer == other.pointer) {
                return true
            }
        }
        return false
    }

    companion object {
        fun getMe() =
            LocalPlayer(clientDLL.uint(dwLocalPlayer))

        fun getAll(): List<Entity> {
            val players = ArrayList<Entity>()

            repeat(20) {
                val entityOffset = clientDLL.uint(dwEntityList + (0x10 * it))
                println(entityOffset)
                players.add(Entity(entityOffset))
            }


            return players
        }

        fun getById(id: Int): Entity {
            val entityOffset = clientDLL.uint(dwEntityList + ((id - 1) * 16))

            return Entity(entityOffset)
        }
    }
}