package ru.newmcpe.bhop.features

import ru.newmcpe.bhop.api.entites.Player
import ru.newmcpe.bhop.api.features.Feature
import java.awt.Robot
import java.awt.event.InputEvent
import java.awt.event.KeyEvent

class Triggerbot: Feature("TriggerBot") {
    override fun update() {
        val localPlayer = Player.getMe()

        val crosshairId = localPlayer.getCrosshairId()

        if(crosshairId in 1..63){
            val player = Player.getById(crosshairId)

           if(localPlayer.getTeam() != player.getTeam()) {
                Player.getMe().pressMouse(InputEvent.BUTTON1_MASK)
            }
        }
    }
}