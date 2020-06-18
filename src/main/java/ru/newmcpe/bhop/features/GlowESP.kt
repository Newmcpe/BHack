package ru.newmcpe.bhop.features

import ru.newmcpe.bhop.api.entites.Player
import ru.newmcpe.bhop.api.features.Feature

class GlowESP : Feature("GlowESP") {
    override fun update() {
        try {
            Player.getAll().forEach {
                if (Player.getMe().getTeam() != it.getTeam()) {
                    it.glow(255F, 0F, 0F, 1F)
                } else {
                    it.glow(0F, 255F, 0F, 1F)
                }
            }

        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

}