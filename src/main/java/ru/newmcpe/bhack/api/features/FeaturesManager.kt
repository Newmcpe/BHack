package ru.newmcpe.bhack.api.features

import ru.newmcpe.bhack.BHack
import ru.newmcpe.bhack.api.entites.EntityManager
import ru.newmcpe.bhack.features.BunnyHop
import ru.newmcpe.bhack.features.Chams
import ru.newmcpe.bhack.features.Triggerbot
import ru.newmcpe.bhack.features.esp.BoxESP
import ru.newmcpe.bhack.features.esp.GlowESP
import ru.newmcpe.bhack.features.esp.NameESP

object FeaturesManager {
    val features: MutableList<Feature> = ArrayList()

    fun init() {
        features.add(BunnyHop())
        features.add(Chams())
        features.add(Triggerbot())

        features.add(BoxESP())
        features.add(GlowESP())
        features.add(NameESP())

        BHack.executorService.submit {
            while (true) {
                try {
                    EntityManager.updateEntities()
                    features.filter(Feature::enabled)
                        .forEach(Feature::update)
                    Thread.sleep(1)
                } catch (t: Throwable) {
                    println("Ошибка при обновлении функ")
                    t.printStackTrace()
                }
            }
        }
    }

    fun getByName(featureName: String) = features.first{it.name == featureName}
}