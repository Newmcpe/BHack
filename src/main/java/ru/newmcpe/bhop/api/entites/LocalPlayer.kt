package ru.newmcpe.bhop.api.entites

import ru.newmcpe.bhop.Bhop
import ru.newmcpe.bhop.Bhop.csgo
import ru.newmcpe.bhop.offsets.netvars.NetVarOffsets
import java.awt.Robot

class LocalPlayer(override val offset: Long) : Player(offset) {
    fun getCrosshairId() = csgo.int(offset + NetVarOffsets.iCrossHairID)
    fun pressMouse(button: Int) {
        val bot: Robot = Bhop.getRobot()
        bot.mousePress(button)
        try {
            Thread.sleep(20)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        bot.mouseRelease(button)
    }
}