package ru.newmcpe.bhop.api.entites

import ru.newmcpe.bhop.Bhop
import ru.newmcpe.bhop.Bhop.clientDLL
import ru.newmcpe.bhop.Bhop.csgo
import ru.newmcpe.bhop.offsets.ClientOffsets.dwForceAttack
import ru.newmcpe.bhop.offsets.netvars.NetVarOffsets
import java.awt.Robot
import kotlin.random.Random

class LocalPlayer(override val pointer: Long) : Entity(pointer) {
    fun getCrosshairId() = csgo.int(pointer + NetVarOffsets.iCrossHairID)
    fun pressMouseLegit(button: Int) {
        val bot: Robot = Bhop.getRobot()
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