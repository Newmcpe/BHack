package ru.newmcpe.bhop.api.features

import ru.newmcpe.bhop.Bhop
import ru.newmcpe.bhop.event.impl.OverlayEvent

abstract class Feature(
    val name: String
) {
    protected val clientDLL = Bhop.clientDLL
    protected val csgoProcess = Bhop.csgo

    open fun update() {}

    open fun render(overlayEvent: OverlayEvent) {}
}