package ru.newmcpe.bhop.api.entites

import java.util.concurrent.CopyOnWriteArrayList

object EntityManager {
    private val entities: MutableList<Player> = CopyOnWriteArrayList()

    fun updateEntities(){

    }
}