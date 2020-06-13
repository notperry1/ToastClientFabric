package toast.client.gui.clickgui.component.components

import toast.client.gui.clickgui.component.Component
import toast.client.modules.Module
import toast.client.utils.ConfigManager

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

    override fun render() {
        drawBox("> ", module.name, false, module.enabled)
        if (ConfigManager.clickGuiPositions.positions.containsKey(module.category)) {
            if ((ConfigManager.clickGuiPositions.positions[module.category]
                            ?: return).expandedModule.containsKey(module.name)) {
                for (setting in subComponents) {
                    setting.render()
                }
            }
        }
    }

    init {
        if (if (ConfigManager.clickGuiPositions.positions[module.category] != null) (ConfigManager.clickGuiPositions.positions[module.category]!!).expandedModule[module.name] == true else false) {
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