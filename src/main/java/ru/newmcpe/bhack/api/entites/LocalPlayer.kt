package ru.newmcpe.bhack.api.entites

import ru.newmcpe.bhack.BHack
import ru.newmcpe.bhack.BHack.clientDLL
import ru.newmcpe.bhack.BHack.csgo
import ru.newmcpe.bhack.offsets.ClientOffsets.dwForceAttack
import ru.newmcpe.bhack.offsets.netvars.NetVarOffsets
import java.awt.Robot
import kotlin.random.Random

class LocalPlayer(override val pointer: Long) : Entity(pointer) {
    fun getCrosshairId() = csgo.int(pointer + NetVarOffsets.iCrossHairID)
    fun pressMouseLegit(button: Int) {
        val bot: Robot = BHack.getRobot()
        bot.mousePress(button)
        try {
            Thread.sleep(20)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        bot.mouseRelease(button)
    }

    fun pressMouse(){
        clientDLL[dwForceAttack] = 5.toByte()
        Thread.sleep(8 + Random.nextLong(16))
        clientDLL[dwForceAttack] = 4.toByte()
    }
}