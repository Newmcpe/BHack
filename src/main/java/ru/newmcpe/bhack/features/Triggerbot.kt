package ru.newmcpe.bhack.features

import ru.newmcpe.bhack.api.entites.EntityManager
import ru.newmcpe.bhack.api.features.Feature
import ru.newmcpe.bhack.util.Schedule
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