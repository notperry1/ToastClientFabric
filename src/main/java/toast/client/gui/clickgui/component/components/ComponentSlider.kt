package toast.client.gui.clickgui.component.components

import toast.client.modules.config.Setting
import toast.client.modules.config.SettingDef
import toast.client.utils.TwoDRenderUtils
import kotlin.math.roundToInt

/**
 * Render definition for slider settings
 */
class ComponentSlider(override var setting: Setting, override var settingDef: SettingDef, override var settingName: String, override var x: Double, override var y: Double, override var width: Double) : SettingComponent() {
    private val tColor: Int = java.awt.Color(255, 255, 255, 255).rgb

    override fun render(mouseX: Double, mouseY: Double) {
        drawBox(" > ", "$settingName: ${setting.value}", isMouseOver(mouseX, mouseY), setting.enabled ?: true)
        TwoDRenderUtils.drawText("${settingDef.minValue}", (x + 4).roundToInt(), (y + height / 2).roundToInt(), tColor)
    }

    init {
        height = textHeightWithSpacing * 2 - 2.0
    }
}