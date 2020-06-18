package ru.newmcpe.bhop.api.entites

import ru.newmcpe.bhop.Bhop.clientDLL
import ru.newmcpe.bhop.Bhop.csgo
import ru.newmcpe.bhop.offsets.ClientOffsets
import ru.newmcpe.bhop.offsets.ClientOffsets.dwEntityList
import ru.newmcpe.bhop.offsets.ClientOffsets.dwLocalPlayer
import ru.newmcpe.bhop.offsets.netvars.NetVarOffsets
import ru.newmcpe.bhop.offsets.netvars.NetVarOffsets.fFlags
import ru.newmcpe.bhop.offsets.netvars.NetVarOffsets.iHealth
import ru.newmcpe.bhop.offsets.netvars.NetVarOffsets.iTeamNum
import ru.newmcpe.bhop.util.uint

open class Player(
    open val offset: Long
) {
    fun fFlags() = csgo.int(offset + fFlags)

    fun onGround() = fFlags() and 1 == 1

    fun getHealth() = csgo.int(offset + iHealth)

    fun getGlowIndex() = csgo.int(offset + 0xA438)

    fun getTeam() = csgo.uint(offset + iTeamNum)

    fun getArmor() = csgo.int(offset + NetVarOffsets.bSpotted)

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

    companion object {
        fun getMe() =
            LocalPlayer(clientDLL.uint(dwLocalPlayer))

        fun getAll(): List<Player> {
            val players = ArrayList<Player>()

            repeat(20) {
                val entityOffset = clientDLL.uint(dwEntityList + (0x10 * it))
                println(entityOffset)
                players.add(Player(entityOffset))
            }


            return players
        }

        fun getById(id: Int): Player {
            val entityOffset = clientDLL.uint(dwEntityList + ((id - 1) * 16))

            return Player(entityOffset)
        }
    }
}