package ru.newmcpe.bhack.api.features

import ru.newmcpe.bhack.BHack
import ru.newmcpe.bhack.api.entites.EntityManager
import ru.newmcpe.bhack.features.Aimbot
import ru.newmcpe.bhack.features.BunnyHop
import ru.newmcpe.bhack.features.Chams
import ru.newmcpe.bhack.features.Triggerbot
import ru.newmcpe.bhack.features.esp.BoxESP
import ru.newmcpe.bhack.features.esp.GlowESP
import ru.newmcpe.bhack.features.esp.NameESP
import ru.newmcpe.bhack.offsets.ViewMatrix
import java.io.File

object FeaturesManager {
    val features: MutableList<Feature> = ArrayList()

    fun init() {
        features.add(BunnyHop())
        features.add(Chams())
        features.add(Triggerbot())
        features.add(Aimbot())

        features.add(BoxESP())
        features.add(GlowESP())
        features.add(NameESP())

        val cheatPath = File(System.getenv("LOCALAPPDATA") + "/BHack")
        if (!cheatPath.exists()) cheatPath.mkdirs()

        val featuresConfig = File(cheatPath.absolutePath + "/features.cfg")
        if (!featuresConfig.exists()) println("Created features config ${featuresConfig.createNewFile()} at path ${featuresConfig.absolutePath}")

        try {
             featuresConfig.reader().forEachLine {
                 val exploded = it.split(":")
                 println("Set ${exploded[0]} status to ${exploded[1]}")
                 getByName(exploded[0]).enabled = exploded[1].toBoolean()
             }
        } catch (t: Throwable) {
        }

        Runtime.getRuntime().addShutdownHook(object : Thread() {
            override fun run() {
                val writer = featuresConfig.writer()

                features.forEach {
                    writer.write("${it.name}:${it.enabled}")
                    writer.write("\n")
                }

                writer.flush()
                writer.close()
            }
        })

        BHack.executorService.submit {
            while (true) {
                try {
                    ViewMatrix.getInstance().updateMatrix()
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

    fun getByName(featureName: String) = features.first { it.name == featureName }
}