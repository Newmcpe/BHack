package ru.newmcpe.bhop.features.esp

import ru.newmcpe.bhop.api.entites.Entity
import ru.newmcpe.bhop.api.entites.EntityManager
import ru.newmcpe.bhop.api.features.Feature

class GlowESP : Feature("GlowESP") {
    override fun update() {
        try {
            EntityManager.getEntities().forEachIndexed { index: Int, it: Entity ->
                if (Entity.getMe().getTeam() != it.getTeam()) {
                    it.glow(255F, 0F, 0F, 1F)

                    /*  CharlatanoOverlay{
                          batch.begin()
                          textRenderer.color = Color.ORANGE
                          val draw = textRenderer.draw(batch, "${it.getHealth()} hp",20F + (index * 50),500F)

                          batch.end()
                      }*/
                } else {
                    it.glow(0F, 255F, 0F, 1F)
                }
            }

        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

}