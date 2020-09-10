package ru.newmcpe.bhack.features.esp

import ru.newmcpe.bhack.api.entites.Entity
import ru.newmcpe.bhack.api.entites.EntityManager
import ru.newmcpe.bhack.api.features.Feature

class GlowESP : Feature("GlowESP") {
    override fun update() {
        try {
            EntityManager.getEntities().values.forEachIndexed { index: Int, it: Entity ->
                if (Entity.getMe().getTeam() != it.getTeam()) {
                    it.glow(255F, 0F, 0F, 1F)
                }
            }

        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

}