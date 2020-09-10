package ru.newmcpe.bhack.config

import org.apache.commons.lang3.SerializationUtils
import java.io.File
import java.io.Serializable

object ConfigVars : Serializable {
    var AIMBOT_FOV: Int = 0
    var AIMBOT_SMOOTH: Double = 0.0
    var AIMBOT_BONE: Int = 8

    init {
        val cheatPath = File(System.getenv("LOCALAPPDATA") + "/BHack")
        if (!cheatPath.exists()) cheatPath.mkdirs()

        val config = File(cheatPath.absolutePath + "/config.cfg")
        if (!config.exists()) println("Created vars config ${config.createNewFile()} at path ${config.absolutePath}")

        try {
            val config = SerializationUtils.deserialize<Config>(config.readBytes())

            AIMBOT_FOV =
                config.AIMBOT_FOV
            AIMBOT_SMOOTH =
                config.AIMBOT_SMOOTH
            AIMBOT_BONE =
                config.AIMBOT_BONE

            println("Loaded config = $config")

        } catch (t: Throwable){}

        Runtime.getRuntime().addShutdownHook(object : Thread() {
            override fun run() {
                println("гавно")
                config.writeBytes(SerializationUtils.serialize(Config(AIMBOT_FOV, AIMBOT_SMOOTH, AIMBOT_BONE)))
            }
        })
    }

    override fun toString(): String {
        return "Config (AIMBOT_FOV = $AIMBOT_FOV, AIMBOT_SMOOTH = $AIMBOT_SMOOTH, AIMBOT_BONE = $AIMBOT_BONE)"
    }
}