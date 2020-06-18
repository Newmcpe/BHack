package ru.newmcpe.bhop

import com.sun.jna.platform.win32.WinNT
import org.jire.arrowhead.Module
import org.jire.arrowhead.Process
import org.jire.arrowhead.processByName
import ru.newmcpe.bhop.api.features.FeaturesManager
import ru.newmcpe.bhop.offsets.netvars.NetVars
import ru.newmcpe.bhop.util.retry
import java.awt.AWTException
import java.awt.Robot
import java.util.concurrent.Executors


object Bhop {
    @JvmField
    var robot: Robot? = null
    lateinit var csgo: Process
    lateinit var clientDLL: Module
        private set
    lateinit var engineDLL: Module
        private set
    val executorService = Executors.newFixedThreadPool(25)

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
        NetVars.load()

        println("Bhop Hack by Newmcpe")

        FeaturesManager.init()
    }

    fun getRobot(): Robot {
        return if (robot == null) Robot().also { robot = it } else robot!!
    }

}
