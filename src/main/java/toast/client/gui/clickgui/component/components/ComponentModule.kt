package toast.client.gui.clickgui.component.components

import toast.client.gui.clickgui.ClickGuiPositions.Companion.positions
import toast.client.gui.clickgui.component.Component
import toast.client.modules.Module

/**
 * GUI component to render Modules
 */
class ComponentModule(override var x: Double, override var y: Double, override var subComponents: ArrayList<Component>,
                      /**
                       * Module to render
                       */
                      var module: Module, override var width: Double) : Component() {
    /**
     * The total height of the module
     */
    var totalHeight: Double = height

    override fun render(mouseX: Double, mouseY: Double) {
        drawBox("> ", module.name, isMouseOver(mouseX, mouseY), module.enabled)
        if (positions.containsKey(module.category)) {
            if ((positions[module.category]
                            ?: return).expandedModule.containsKey(module.name)) {
                for (setting in subComponents) {
                    setting.render(mouseX, mouseY)
                }
            }
        }
    }

    init {
        if (if (positions[module.category] != null) (positions[module.category]!!).expandedModule[module.name] == true else false) {
            var currentY = y + height
            loop@ for ((settingName, setting) in module.settings.settings) {
                val settingComponent = when (setting.type) {
                    0 -> ComponentMode(setting, module.settings.getSettingDef(settingName)!!, settingName, x, currentY, width)
                    1 -> ComponentSlider(setting, module.settings.getSettingDef(settingName)!!, settingName, x, currentY, width)
                    2 -> ComponentToggle(setting, module.settings.getSettingDef(settingName)!!, settingName, x, currentY, width)
                    else -> ComponentName(setting, module.settings.getSettingDef(settingName)!!, settingName, x, currentY, width)
                }
                currentY += settingComponent.height
                subComponents.add(settingComponent)
            }
            totalHeight = currentY - y
        }
    }
}