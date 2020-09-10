package ru.newmcpe.bhack.config

import java.io.Serializable

data class Config(
    var AIMBOT_FOV: Int = 1,
    var AIMBOT_SMOOTH: Double = 1.0,
    var AIMBOT_BONE: Int = 8
) : Serializable