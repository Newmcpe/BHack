@file:Suppress("JoinDeclarationAndAssignment")

package ru.newmcpe.bhack

import ru.newmcpe.bhack.overlay.RenderOverlay
import ru.newmcpe.bhack.overlay.RenderOverlay.camera
import ru.newmcpe.bhack.overlay.Overlay
import com.sun.jna.Pointer
import com.sun.jna.platform.win32.User32
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.platform.win32.WinNT
import org.jire.arrowhead.Module
import org.jire.arrowhead.Process
import org.jire.arrowhead.processByName
import ru.newmcpe.bhack.api.features.FeaturesManager
import ru.newmcpe.bhack.offsets.netvars.NetVars
import ru.newmcpe.bhack.util.every
import ru.newmcpe.bhack.util.inBackground
import ru.newmcpe.bhack.util.natives.CUser32
import ru.newmcpe.bhack.util.retry
import java.awt.Robot
import java.io.File
import java.util.concurrent.Executors

object BHack {
    lateinit var clientDLL: Module
        private set
    lateinit var engineDLL: Module
        private set
    var gameHeight: Int = 0
        private set

    var gameX: Int = 0
        private set

    var gameWidth: Int = 0
        private set

    var gameY: Int = 0
        private set

    val executorService = Executors.newFixedThreadPool(25)
    val waifu_file = File("src/main/java/ru/newmcpe/bhop/assets/mrpidaras.png")
    lateinit var csgo: Process

    @JvmField
    var robot: Robot? = null

    init {
    }

    fun init() {
        retry(128) {
            csgo = processByName(
                "csgo.exe",
                WinNT.PROCESS_QUERY_INFORMATION or WinNT.PROCESS_VM_READ or WinNT.PROCESS_VM_WRITE
            )!!
        }
        retry(128) {
            csgo.loadModules()
            clientDLL = csgo.modules.getValue("client.dll")
            engineDLL = csgo.modules.getValue("engine.dll")
        }

        val rect = WinDef.RECT()
        val hwd = CUser32.FindWindowA(
            null, "Counter-Strike: Global Offensive"
        )
        var lastWidth = 0
        var lastHeight = 0
        var lastX = 0
        var lastY = 0

        every(1000) {
            if (!CUser32.GetClientRect(hwd, rect)) System.exit(2)
            gameWidth = rect.right - rect.left
            gameHeight = rect.bottom - rect.top

            if (!CUser32.GetWindowRect(hwd, rect)) System.exit(3)
            gameX = rect.left + (((rect.right - rect.left) - gameWidth) / 2)
            gameY = rect.top + ((rect.bottom - rect.top) - gameHeight)

            if (Overlay.opened && (lastX != gameX || lastY != gameY))
                User32.INSTANCE.MoveWindow(Overlay.hwnd, gameX, gameY, gameWidth, gameHeight, false)

            if (Overlay.opened && RenderOverlay.created && (lastWidth != gameWidth || lastHeight != gameHeight))
                camera.setToOrtho(true, gameWidth.toFloat(), gameHeight.toFloat())

            lastWidth = gameWidth
            lastHeight = gameHeight
            lastX = gameX
            lastY = gameY
        }
        every(1024, continuous = true) {
            inBackground = Pointer.nativeValue(hwd.pointer) != CUser32.GetForegroundWindow()
            if (inBackground) return@every
        }

        Overlay.open()

        NetVars.load()
        FeaturesManager.init()

        println("BHack by Newmcpe")
        println("Contract me: newmcpe.ru")
    }

    fun getRobot(): Robot {
        return if (robot == null) Robot().also { robot = it } else robot!!
    }

}
