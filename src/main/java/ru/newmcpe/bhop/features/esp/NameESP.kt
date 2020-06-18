package ru.newmcpe.bhop.features.esp

import com.badlogic.gdx.graphics.Color
import com.charlatano.overlay.CharlatanoOverlay
import ru.newmcpe.bhop.api.entites.EntityManager
import ru.newmcpe.bhop.api.features.Feature
import ru.newmcpe.bhop.util.Vector
import ru.newmcpe.bhop.util.worldToScreen

class NameESP: Feature("NameESP") {
    private val vHead = Vector()
    private val vFeet = Vector()

    private val vTop = Vector(0.0, 0.0, 0.0)
    private val vBot = Vector(0.0, 0.0, 0.0)

    private val boxes = Array(128) { Box() }

    private var currentIdx = 0

    init {
        CharlatanoOverlay {
            EntityManager.forEach { entity ->
                vHead.set(entity.bone(0xC), entity.bone(0x1C), entity.bone(0x2C) + 9)
                vFeet.set(vHead.x, vHead.y, vHead.z - 75)

                if (worldToScreen(vHead, vTop) && worldToScreen(vFeet, vBot)) {
                    val boxH = vBot.y - vTop.y
                    val boxW = boxH / 5F

                    val sx = (vTop.x - boxW).toInt()
                    val sy = vTop.y.toInt()

                    boxes[currentIdx].apply {
                        x = sx
                        y = sy
                        w = Math.ceil(boxW * 2.15).toInt()
                        h = boxH.toInt()
                        color = Color.YELLOW
                        this.entity = entity
                    }

                    currentIdx++
                }

                return@forEach
            }

            textRenderer.apply tR@{
                batch.begin()
                for (i in 0..currentIdx - 1) boxes[i].apply {
                    this@tR.color = color
                    draw(batch, entity.getName(), x.toFloat() + 20, y.toFloat() - 20)
                }

                batch.end()
            }
            currentIdx = 0

        }
    }
}