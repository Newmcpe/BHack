package ru.newmcpe.bhack.api.entites

import ru.newmcpe.bhack.BHack.clientDLL
import ru.newmcpe.bhack.BHack.csgo
import ru.newmcpe.bhack.api.entites.EntityManager.dwClientState
import ru.newmcpe.bhack.offsets.ClientOffsets
import ru.newmcpe.bhack.offsets.ClientOffsets.bDormant
import ru.newmcpe.bhack.offsets.ClientOffsets.dwEntityList
import ru.newmcpe.bhack.offsets.ClientOffsets.dwLocalPlayer
import ru.newmcpe.bhack.offsets.ClientOffsets.dwRadarBase
import ru.newmcpe.bhack.offsets.ClientOffsets.dwViewAngles
import ru.newmcpe.bhack.offsets.netvars.NetVarOffsets
import ru.newmcpe.bhack.offsets.netvars.NetVarOffsets.bSpottedByMask
import ru.newmcpe.bhack.offsets.netvars.NetVarOffsets.dwBoneMatrix
import ru.newmcpe.bhack.offsets.netvars.NetVarOffsets.fFlags
import ru.newmcpe.bhack.offsets.netvars.NetVarOffsets.iHealth
import ru.newmcpe.bhack.offsets.netvars.NetVarOffsets.iTeamNum
import ru.newmcpe.bhack.offsets.netvars.NetVarOffsets.vecOrigin
import ru.newmcpe.bhack.offsets.netvars.NetVarOffsets.vecPunch
import ru.newmcpe.bhack.offsets.netvars.NetVarOffsets.vecViewOffset
import ru.newmcpe.bhack.util.Vector
import ru.newmcpe.bhack.util.uint

open class Entity(
    open val pointer: Long
) {
    fun fFlags() = csgo.int(pointer + fFlags)

    fun isOnGround() = fFlags() and 1 == 1

    fun getHealth() = csgo.int(pointer + iHealth)

    fun getGlowIndex() = csgo.int(pointer + 0xA438)

    fun getTeam() = csgo.uint(pointer + iTeamNum)

    fun getArmor() = csgo.int(pointer + NetVarOffsets.bSpotted)

    fun getPos(): Vector {
        val x: Float = csgo.float(pointer + vecOrigin)
        val y: Float = csgo.float(pointer + vecOrigin + 0x4)
        val z: Float = csgo.float(pointer + vecOrigin + 0x8)
        return Vector(x.toDouble(), y.toDouble(), z.toDouble())
    }

    fun getViewOffsets(): Vector {
        val x = csgo.float(pointer + vecViewOffset)
        val y = csgo.float(pointer + vecViewOffset + 0x4)
        val z = csgo.float(pointer + vecViewOffset + 0x8)

        return Vector(x.toDouble(), y.toDouble(), z.toDouble())
    }

    fun getBonePos(id: Int): Vector {
        val bone = Vector()
        bone.x = this.bone(0x0C, id)
        bone.y = this.bone(0x1C, id)
        bone.z = this.bone(0x2C, id)
        return bone
    }

    fun getPitch() = csgo.float(dwClientState + dwViewAngles)

    fun getYaw() = csgo.float(dwClientState + dwViewAngles + 4)

    fun getRoll() = csgo.float(dwClientState + dwViewAngles + 8)

    val spotted: Boolean
        get() {
            val spottedByMask = csgo.uint(pointer + bSpottedByMask)
            println("spooted $spottedByMask")
            return spottedByMask != 0L
        }

    var viewAngles = Vector()
        get() = Vector(getYaw().toDouble(), getPitch().toDouble(), getRoll().toDouble());
        set(value) {
            field = value

            println("angles to aim for yaw = ${value.x}, pitch ${value.y}")

            csgo[dwClientState + dwViewAngles] = value.x.toFloat()
            csgo[dwClientState + dwViewAngles + 4] = value.y.toFloat()
        }

    fun getId() = EntityManager.getEntities().filterValues { it.pointer == this.pointer }.keys.first()

    val punch: Vector
        get() = Vector().apply {
            x = csgo.float(pointer + vecPunch).toDouble()
            y = csgo.float(pointer + vecPunch + 4).toDouble()
            z = 0.0
        }

    fun writeAngles(value: Vector) {
        println("angles to aim: yaw = ${value.x}, pitch ${value.y}")

        csgo[dwClientState + dwViewAngles] = value.x
        csgo[dwClientState + dwViewAngles + 4] = value.y

        println(getYaw())
        println(getPitch())
    }

    val lifeState
        get() = csgo.byte(pointer + NetVarOffsets.lifeState).toInt()
    val dead
        get() = try {
            lifeState != 0 || getHealth() <= 0
        } catch (t: Throwable) {
            false
        }
    val dormant = try {
        csgo.boolean(pointer + bDormant)
    } catch (t: Throwable) {
        false
    }

    fun getName(): String {
        val radarBase = clientDLL.uint(dwRadarBase)
        val radar = csgo.uint(radarBase + 0x74)
        val id = getId()
        return csgo.read(radar + 0x300 + 0x174 * (id), 32, false)?.getString(0) ?: "Newmcpe is gay"
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
            return this.pointer == other.pointer
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