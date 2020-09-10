package ru.newmcpe.bhack.util.natives

import org.jnativehook.mouse.NativeMouseEvent
import org.jnativehook.mouse.NativeMouseListener
import ru.newmcpe.bhack.api.features.FeaturesManager

class GlobalMouseListener: NativeMouseListener {
    override fun nativeMousePressed(nativeMouseEvent: NativeMouseEvent) {
        FeaturesManager.features.forEach { it.mousePressed(nativeMouseEvent) }
    }

    override fun nativeMouseClicked(nativeMouseEvent: NativeMouseEvent) {
        FeaturesManager.features.forEach { it.mouseClicked(nativeMouseEvent) }
    }

    override fun nativeMouseReleased(nativeMouseEvent: NativeMouseEvent) {
        FeaturesManager.features.forEach { it.mouseReleased(nativeMouseEvent) }
    }
}