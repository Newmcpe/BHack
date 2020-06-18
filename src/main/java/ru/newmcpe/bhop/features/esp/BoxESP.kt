package ru.newmcpe.bhop.features.esp

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.charlatano.overlay.CharlatanoOverlay
import ru.newmcpe.bhop.Bhop
import ru.newmcpe.bhop.api.entites.Entity
import ru.newmcpe.bhop.api.entites.EntityManager
import ru.newmcpe.bhop.api.features.Feature
import ru.newmcpe.bhop.util.Vector
import ru.newmcpe.bhop.util.worldToScreen


class BoxESP : Feature("BoxESP") {
    private val vHead = Vector()
    private val vFeet = Vector()

    private val vTop = Vector(0.0, 0.0, 0.0)
    private val vBot = Vector(0.0, 0.0, 0.0)

    private val boxes = Array(128) { Box() }

    private var currentIdx = 0

    private var waifuTex: Texture? = null
    private var waifu: Sprite? = null

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
                        color = Color.RED
                        this.entity = entity
                    }

                    currentIdx++
                }

                return@forEach
            }

            shapeRenderer.apply sR@{
                begin()
                for (i in 0..currentIdx - 1) boxes[i].apply {
                    this@sR.color = color
                    rect(x.toFloat(), y.toFloat(), w.toFloat(), h.toFloat())
                    //     batch.draw(waifu, x.toFloat(), y.toFloat(), w.toFloat(), h.toFloat())
                }

                end()
            }

            currentIdx = 0

        }
    }
}


