package ru.newmcpe.bhack.features

import org.jire.arrowhead.keyPressed
import ru.newmcpe.bhack.api.entites.EntityManager
import ru.newmcpe.bhack.api.features.Feature
import ru.newmcpe.bhack.offsets.ClientOffsets
import java.awt.event.KeyEvent

class BunnyHop : Feature("BunnyHop") {
    override fun update() {
        if (EntityManager.getLocalPlayer().isOnGround() && keyPressed(KeyEvent.VK_SPACE)) {
            clientDLL[ClientOffsets.dwForceJump] = 6
        }
    }
}
