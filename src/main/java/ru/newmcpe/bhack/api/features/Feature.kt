package ru.newmcpe.bhack.api.features

import org.jnativehook.keyboard.NativeKeyEvent
import org.jnativehook.mouse.NativeMouseEvent
import ru.newmcpe.bhack.BHack

abstract class Feature(
    val name: String
) {
    protected val clientDLL = BHack.clientDLL
    protected val csgoProcess = BHack.csgo

    var enabled = false

    open fun update() {}
    open fun keyTyped(nativeKeyEvent: NativeKeyEvent) {}
    open fun keyPressed(nativeKeyEvent: NativeKeyEvent) {}
    open fun keyReleased(nativeKeyEvent: NativeKeyEvent) {}
    open fun mousePressed(nativeMouseEvent: NativeMouseEvent) {}
    open fun mouseReleased(nativeMouseEvent: NativeMouseEvent) {}
    open fun mouseClicked(nativeMouseEvent: NativeMouseEvent) {}
}