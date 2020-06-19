package ru.newmcpe.bhack.api.features

import ru.newmcpe.bhack.BHack

abstract class Feature(
    val name: String
) {
    protected val clientDLL = BHack.clientDLL
    protected val csgoProcess = BHack.csgo

    var enabled = false

    open fun update() {}
}