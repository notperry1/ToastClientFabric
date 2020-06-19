package toast.client.gui.clickgui.component.components

import toast.client.modules.config.Setting
import toast.client.modules.config.SettingDef
import toast.client.utils.TwoDRenderUtils
import java.awt.Color
import kotlin.math.roundToInt

/**
 * Render definition for slider settings
 */
class ComponentSlider(override var setting: Setting, override var settingDef: SettingDef, override var settingName: String, override var x: Double, override var y: Double, override var width: Double) : SettingComponent() {
    private val tColor: Int = Color(255, 255, 255, 255).rgb

    private val minValWidth: Int = mc.textRenderer.getStringWidth(settingDef.minValue.toString())
    private val maxValWidth: Int = mc.textRenderer.getStringWidth(settingDef.maxValue.toString())
    val barX: Int = minValWidth + 5
    val barLength: Int = width.roundToInt() - minValWidth - maxValWidth - 10
    private val maxValX: Int = minValWidth + barLength + 6

    override fun render(mouseX: Double, mouseY: Double) {
        drawBox(" > ", "$settingName: ${setting.value}", isMouseOver(mouseX, mouseY), setting.enabled ?: true)
        TwoDRenderUtils.drawText("${settingDef.minValue}", (x + 4).roundToInt(), (y + height / 2 + 2).roundToInt(), tColor)
        TwoDRenderUtils.drawText("${settingDef.maxValue}", (x + maxValX).roundToInt(), (y + height / 2 + 2).roundToInt(), tColor)
        val barY = (y + height / 2 + textHeight / 2 + 1).roundToInt()
        renderUtils.drawRect((x + barX).roundToInt(), barY, barLength, 2, Color(255, 255, 255, 255).rgb)
        renderUtils.drawRect((x + barX + (((setting.value?.div((settingDef.maxValue
                ?: return)))?.times(barLength) ?: return))).roundToInt(), barY - 3, 4, 8, Color(255, 255, 255, 255).rgb)
    }

    fun isMouseOnKnob(mouseX: Double, mouseY: Double): Boolean {
        val knobX = (x + barX + (((setting.value?.div((settingDef.maxValue
                ?: return false)))?.times(barLength) ?: return false)))
        val knobY = (y + height / 2 + textHeight / 2 + 1).roundToInt() - 3
        if (mouseX >= knobX && mouseX <= knobX + 4 && mouseY >= knobY && mouseY <= knobY + 8) return true
        return false
    }

    init {
        height = textHeightWithSpacing * 2 - 2.0
    }
}