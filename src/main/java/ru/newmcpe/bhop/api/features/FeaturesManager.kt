package ru.newmcpe.bhop.api.features

import ru.newmcpe.bhop.Bhop
import ru.newmcpe.bhop.features.BunnyHop
import ru.newmcpe.bhop.features.Chams
import ru.newmcpe.bhop.features.GlowESP
import ru.newmcpe.bhop.features.Triggerbot
import kotlin.system.exitProcess

object FeaturesManager {
    val features: MutableList<Feature> = ArrayList()

    fun init() {
        features.add(BunnyHop())
        features.add(GlowESP())
        features.add(Chams())
        features.add(Triggerbot())

        Bhop.executorService.submit {

            while (true) {
                try {
                    features.forEach(Feature::update)

                    Thread.sleep(1)
                } catch (t: Throwable) {
                    println("Ошибка при обновлении функ")
                    t.printStackTrace()
                    exitProcess(1)
                }
            }

        }

    }
}