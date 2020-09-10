package ru.newmcpe.bhack.fxml.menu

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.RadioButton
import javafx.scene.control.Slider
import javafx.scene.control.TextField
import javafx.scene.text.Text
import ru.newmcpe.bhack.api.features.FeaturesManager
import ru.newmcpe.bhack.config.ConfigVars
import ru.newmcpe.bhack.util.Schedule
import java.util.concurrent.TimeUnit

public class MenuController {
    @FXML
    lateinit var FOV: Text
    @FXML
    lateinit var fovSlider: Slider

    @FXML
    lateinit var smooth: Text
    @FXML
    lateinit var smoothSlider: Slider

    @FXML
    lateinit var bone: TextField


    fun initialize() {
        print("Controller working")

        FOV.text = "FOV: ${ConfigVars.AIMBOT_FOV}"
        fovSlider.value = ConfigVars.AIMBOT_FOV.toDouble()

        smooth.text = "Скорость: ${ConfigVars.AIMBOT_SMOOTH}"
        smoothSlider.value = ConfigVars.AIMBOT_SMOOTH

        bone.text = ConfigVars.AIMBOT_BONE.toString()

        fovSlider.valueProperty().addListener { _, _, newValue ->
            FOV.text = "FOV: $newValue"

            ConfigVars.AIMBOT_FOV = newValue.toInt()
        }
        smoothSlider.valueProperty().addListener { _, _, newValue ->
            smooth.text = "Скорость: $newValue"

            ConfigVars.AIMBOT_SMOOTH = newValue.toDouble()
        }

        bone.textProperty().addListener { observable, oldValue, newValue ->
            ConfigVars.AIMBOT_BONE = newValue.toInt()
        }

        Schedule.runWithDelay({
            try {
                FeaturesManager.features.forEach {
                    val feature = fovSlider.scene.lookup("#${it.name}") as RadioButton
                    feature.isSelected = it.enabled
                }
            }catch (t: Throwable) {t.printStackTrace()}

        }, 2, TimeUnit.SECONDS)

    }

    @FXML
    private fun clickFeature(event: ActionEvent) {
        val source = event.source as RadioButton
        val id: String = source.id

        val feature = FeaturesManager.getByName(id)
        feature.enabled = source.isSelected
    }
}