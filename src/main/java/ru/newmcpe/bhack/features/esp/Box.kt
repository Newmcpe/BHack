package ru.newmcpe.bhack.features.esp

import com.badlogic.gdx.graphics.Color
import ru.newmcpe.bhack.api.entites.Entity

data class Box(
    var x: Int = -1, var y: Int = -1,
    var w: Int = -1, var h: Int = -1,
    var color: Color = Color.WHITE,
    var entity: Entity = Entity.getMe()
)