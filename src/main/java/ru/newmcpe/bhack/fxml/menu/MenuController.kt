package ru.newmcpe.bhack.fxml.menu

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.RadioButton
import ru.newmcpe.bhack.api.features.FeaturesManager

class MenuController {
    @FXML
    lateinit var BunnyHop: RadioButton
    lateinit var TriggerBot: RadioButton

    fun initialize() {
        print("Controller working")
    }

    @FXML
    private fun clickFeature(event: ActionEvent) {
        val source = event.source as RadioButton
        val id: String = source.id

        val feature = FeaturesManager.getByName(id)
        feature.enabled = source.isSelected
    }
}