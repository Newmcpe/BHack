package ru.newmcpe.bhack.fxml

import Main
import javafx.application.Application
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage
import kotlin.system.exitProcess

class App : Application() {
    override fun start(primaryStage: Stage) {
        val root = FXMLLoader.load<Parent>(Main::class.java.getResource("main.fxml"))
        primaryStage.title = "BHack Loader"
        primaryStage.scene = Scene(root, 500.0, 300.0)
        primaryStage.isResizable = false
        primaryStage.icons.add(Image(Main::class.java.getResourceAsStream("icon.png")))
        primaryStage.show()

        primaryStage.onCloseRequest = EventHandler {
            Platform.exit()
            exitProcess(0)
        }
    }
}