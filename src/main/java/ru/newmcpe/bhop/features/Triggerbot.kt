package ru.newmcpe.bhop.features

import com.badlogic.gdx.graphics.Color
import ru.newmcpe.bhop.api.entites.EntityManager
import ru.newmcpe.bhop.api.features.Feature
import ru.newmcpe.bhop.util.Schedule
import java.awt.event.InputEvent
import java.util.concurrent.TimeUnit

class Triggerbot : Feature("TriggerBot") {
    var lastShot = System.currentTimeMillis()
    override fun update() {
        val localPlayer = EntityManager.getLocalPlayer()

        val crosshairId = localPlayer.getCrosshairId()

        if (crosshairId in 1..63) {
            val player = EntityManager.getById(crosshairId)

            if (localPlayer.getTeam() != player.getTeam()) {
                if((System.currentTimeMillis() - lastShot) >= 100) {
                    Schedule.runWithDelay({
                        EntityManager.getLocalPlayer().pressMouse()
                    }, 5, TimeUnit.MILLISECONDS)

                    lastShot = System.currentTimeMillis()
                }
            }
        }
    }
}