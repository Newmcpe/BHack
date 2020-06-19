package ru.newmcpe.bhack.features.esp

import com.badlogic.gdx.graphics.Color
import ru.newmcpe.bhack.overlay.RenderOverlay
import ru.newmcpe.bhack.api.entites.EntityManager
import ru.newmcpe.bhack.api.features.Feature
import ru.newmcpe.bhack.util.Vector
import ru.newmcpe.bhack.util.worldToScreen

class NameESP: Feature("NameESP") {
    private val vHead = Vector()
    private val vFeet = Vector()

    private val vTop = Vector(0.0, 0.0, 0.0)
    private val vBot = Vector(0.0, 0.0, 0.0)

    private val boxes = Array(128) { Box() }

    private var currentIdx = 0

    init {
        RenderOverlay {
            if(!enabled) return@RenderOverlay
            EntityManager.forEach { entity ->
                vHead.set(entity.bone(0xC), entity.bone(0x1C), entity.bone(0x2C) + 9)
                vFeet.set(vHead.x, vHead.y, vHead.z - 75)

                if(entity.dead || entity.getTeam() == EntityManager.getLocalPlayer().getTeam()) return@forEach

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

                    val name = entity.getName()
                    draw(batch, name, x.toFloat() + 20, y.toFloat() - 20)
                }

                batch.end()
            }
            currentIdx = 0
        }
    }
}