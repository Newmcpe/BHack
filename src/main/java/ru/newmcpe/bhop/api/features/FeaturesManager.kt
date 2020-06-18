package ru.newmcpe.bhop.api.features

import ru.newmcpe.bhop.api.entites.EntityManager
import ru.newmcpe.bhop.features.*
import ru.newmcpe.bhop.features.esp.BoxESP
import ru.newmcpe.bhop.features.esp.GlowESP
import ru.newmcpe.bhop.features.esp.NameESP
import ru.newmcpe.bhop.offsets.ViewMatrix

object FeaturesManager {
    val features: MutableList<Feature> = ArrayList()

    fun init() {
        features.add(BunnyHop())
        features.add(Chams())
        features.add(Triggerbot())

        features.add(BoxESP())
        features.add(GlowESP())
        features.add(NameESP())

        while (true) {
            try {
                EntityManager.updateEntities()
                ViewMatrix.getInstance().updateMatrix()
                features.forEach(Feature::update)
                Thread.sleep(1)
            } catch (t: Throwable) {
                println("Ошибка при обновлении функ")
                t.printStackTrace()
           //     exitProcess(1)
            }
        }

    }
}