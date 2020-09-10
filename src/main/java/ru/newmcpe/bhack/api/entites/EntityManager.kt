package ru.newmcpe.bhack.api.entites

import ru.newmcpe.bhack.BHack.clientDLL
import ru.newmcpe.bhack.BHack.engineDLL
import ru.newmcpe.bhack.offsets.ClientOffsets
import ru.newmcpe.bhack.util.uint
import java.util.*
import kotlin.collections.HashMap

object EntityManager {
    private val ENTITIES: MutableMap<Int, Entity> = Collections.synchronizedMap(HashMap())
    var dwClientState = engineDLL.uint(ClientOffsets.dwClientState)

    @ExperimentalUnsignedTypes
    fun updateEntities() {
        dwClientState = engineDLL.uint(ClientOffsets.dwClientState)
        for (i in 1..63) {
            val entityOffset = clientDLL.uint(ClientOffsets.dwEntityList + (i * 16L))

            if (entityOffset == getLocalPlayer().pointer) continue
            if (!isEntityValid(entityOffset)) {
                continue
            }

            val entity = Entity(entityOffset)

            if (entityOffset != 0L && !containsEntity(entity)) {
                ENTITIES[i] = entity

                println("Добавил сущность игрока ${entity.getName()} в кэш. Размер: ${ENTITIES.size}")
            }
        }
    }

    fun getEntities() = ENTITIES

    fun getEntityByCrosshairId(id: Int) =
        ENTITIES.values.first { it.pointer == clientDLL.uint(ClientOffsets.dwEntityList + (id - 1) * 0x10) }

    fun getLocalPlayer(): LocalPlayer = LocalPlayer(clientDLL.uint(ClientOffsets.dwLocalPlayer))

    private fun containsEntity(entity: Entity) = ENTITIES.any { it.value == entity }

    private fun isEntityValid(base: Long): Boolean {
        if (base == 0L) return false
        if (clientDLL.uint(ClientOffsets.dwLocalPlayer) == base) return true

        return true
    }

    fun isEntityValid(entity: Entity): Boolean {
        if (!isEntityValid(entity.pointer)) return false
        if (entity.dead) return false

        return true
    }

}