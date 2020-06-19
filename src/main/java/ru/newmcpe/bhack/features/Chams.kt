package ru.newmcpe.bhack.features

import ru.newmcpe.bhack.api.features.Feature
import ru.newmcpe.bhack.offsets.ClientOffsets.dwGlowObject
import ru.newmcpe.bhack.util.uint

class Chams : Feature("Chams") {
    init {
        val pointerGlow = clientDLL.uint(dwGlowObject)
        val glowObjectCount = clientDLL.uint(dwGlowObject + 4)
        for (i in 0 until glowObjectCount) {
            try {
                val glowObjectPointer = pointerGlow + i * 56
                val entityAdress = csgoProcess.uint(glowObjectPointer)
                val color = arrayOf(0,255,255, 255)

                for (x in 0..3) {
                    csgoProcess[glowObjectPointer + (x + 1) * 4] = color[x] / 255f
                    csgoProcess[entityAdress + 0x70 + x] = color[x]
                }

                csgoProcess[glowObjectPointer + 0x24] = false
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }
}