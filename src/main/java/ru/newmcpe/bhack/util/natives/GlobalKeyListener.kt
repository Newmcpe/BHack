package ru.newmcpe.bhack.util.natives

import org.jnativehook.keyboard.NativeKeyEvent
import org.jnativehook.keyboard.NativeKeyListener
import ru.newmcpe.bhack.api.features.FeaturesManager

class GlobalKeyListener: NativeKeyListener {
    override fun nativeKeyTyped(nativeKeyEvent: NativeKeyEvent) {
        FeaturesManager.features.forEach { it.keyTyped(nativeKeyEvent) }
    }

    override fun nativeKeyPressed(nativeKeyEvent: NativeKeyEvent) {
        FeaturesManager.features.forEach { it.keyPressed(nativeKeyEvent) }
    }

    override fun nativeKeyReleased(nativeKeyEvent: NativeKeyEvent) {
        FeaturesManager.features.forEach { it.keyReleased(nativeKeyEvent) }
    }
}