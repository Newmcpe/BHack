package ru.newmcpe.bhop.api.entites

import ru.newmcpe.bhop.Bhop
import ru.newmcpe.bhop.offsets.ClientOffsets
import ru.newmcpe.bhop.util.uint
import java.util.concurrent.CopyOnWriteArrayList

object EntityManager {
    private val ENTITIES: MutableList<Entity> = CopyOnWriteArrayList()

    @ExperimentalUnsignedTypes
    fun updateEntities() {
        val localPlayerBase = Bhop.clientDLL.uint(ClientOffsets.dwLocalPlayer)
        val localPlayer = LocalPlayer(localPlayerBase)

        if (localPlayer.pointer != 0L && !containsEntity(localPlayer)) {
            ENTITIES.add(localPlayer)
            println("Добавил локального игрока ${localPlayer.pointer.toUInt()} в кэш. Размер: ${ENTITIES.size}")
        }

        if (localPlayer.pointer != 0L) {
            for (i in 1..63) {
                val entityOffset = Bhop.clientDLL.uint(ClientOffsets.dwEntityList + (i * 16L))

                if (entityOffset == localPlayerBase) continue;
                if (!isEntityValid(entityOffset)) continue;

                val entity = Entity(entityOffset)

                if (entityOffset != 0L && !containsEntity(entity)) {
                    ENTITIES.add(entity)

                    println("Добавил сущность игрока ${localPlayer.pointer.toUInt()} в кэш. Размер: ${ENTITIES.size}")
                }
            }
        }
    }

    fun forEach(consumer: (Entity) -> Unit) = ENTITIES.filter { it != getLocalPlayer() }.forEach(consumer)

    fun getEntities() = ENTITIES

    fun getById(id: Int): Entity {
        val entityPointer = Bhop.clientDLL.uint(ClientOffsets.dwEntityList + ((id - 1) * 16))

        return getEntityFromPointer(entityPointer)
    }

    fun getLocalPlayer(): LocalPlayer = ENTITIES.first() as LocalPlayer

    fun isEntityValid(entity: Entity) = isEntityValid(entity.pointer)

    private fun containsEntity(entity: Entity) = ENTITIES.any { it == entity }

    private fun getEntityFromPointer(entityPointer: Long): Entity = ENTITIES.first { it.pointer == entityPointer }

    private fun isEntityValid(base: Long): Boolean {
        if (base == 0L) return false
        if (Bhop.clientDLL.uint(ClientOffsets.dwLocalPlayer) == base) return true

        return true
    }
}