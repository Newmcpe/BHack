package ru.newmcpe.bhack.fxml.loader

import Main
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.stage.Stage
import ru.newmcpe.bhack.BHack


class LoaderController {
    @FXML
    lateinit var start: Button

    fun initialize() {
        print("Controller working")
        start.setOnAction {
            val primStage = start.scene.window as Stage
            primStage.title = "BHack Loader (загрузка...)"

            BHack.init()

            val loader = FXMLLoader(Main::class.java.getResource("menu.fxml"))
            loader.load<LoaderController>()
            primStage.scene = Scene(loader.getRoot(), 500.0, 300.0)
            primStage.title = "BHack Menu"
        }
    }
}