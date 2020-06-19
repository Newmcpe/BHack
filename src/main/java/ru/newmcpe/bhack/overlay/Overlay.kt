package ru.newmcpe.bhack.overlay

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import ru.newmcpe.bhack.overlay.transparency.TransparencyApplier
import ru.newmcpe.bhack.overlay.transparency.win10.Win10TransparencyApplier
import ru.newmcpe.bhack.overlay.transparency.win7.Win7TransparencyApplier
import com.sun.jna.platform.win32.User32
import com.sun.jna.platform.win32.WinDef
import ru.newmcpe.bhack.BHack.gameHeight
import ru.newmcpe.bhack.BHack.gameWidth
import ru.newmcpe.bhack.BHack.gameX
import ru.newmcpe.bhack.BHack.gameY
import kotlin.random.Random

object Overlay {
	
	@Volatile var opened = false
	
	lateinit var hwnd: WinDef.HWND
	
	fun open() = LwjglApplicationConfiguration().apply {
		width = gameWidth
		height = gameHeight
		title = Random.nextLong(Long.MAX_VALUE).toString()
		x = gameX
		y = gameY
		resizable = false
		fullscreen = false
		vSyncEnabled = false

		foregroundFPS = 144
		backgroundFPS = 144
		
		LwjglApplication(RenderOverlay, this)
		
		do {
			val hwnd = User32.INSTANCE.FindWindow(null, title)
			if (hwnd != null) {
				Overlay.hwnd = hwnd
				break
			}
			Thread.sleep(64) // decreased so it won't go black as long
		} while (!Thread.interrupted())
		
		// sets up window to be fullscreen, click-through, etc.
		WindowCorrector.setupWindow(hwnd)
		
		
		// sets up the full transparency of the Window (only Windows 7 and 10 can do this)
		val transparencyApplier: TransparencyApplier =
				if (System.getProperty("os.name").contains("windows 10", ignoreCase = true))
					Win10TransparencyApplier
				else
					Win7TransparencyApplier // will only work on Windows 7 or early Windows 10 builds
		transparencyApplier.applyTransparency(hwnd)
		
		opened = true
	}
	
	init {
		System.setProperty("org.lwjgl.opengl.Window.undecorated", "true")
	}
	
}