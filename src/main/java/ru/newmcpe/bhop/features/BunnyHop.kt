package ru.newmcpe.bhop.features

import org.jire.arrowhead.keyPressed
import ru.newmcpe.bhop.api.entites.Player
import ru.newmcpe.bhop.api.features.Feature
import ru.newmcpe.bhop.offsets.ClientOffsets
import java.awt.event.KeyEvent

class BunnyHop : Feature("BunnyHop") {
    override fun update() {
        if (Player.getMe().onGround() && keyPressed(KeyEvent.VK_SPACE)) {
            clientDLL[ClientOffsets.dwForceJump] = 6
        }
    }
}
