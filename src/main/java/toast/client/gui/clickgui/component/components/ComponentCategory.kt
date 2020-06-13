package toast.client.gui.clickgui.component.components

import toast.client.gui.clickgui.component.Component
import toast.client.modules.Module
import toast.client.utils.ConfigManager.Companion.clickGuiPositions

/**
 * Category renderer
 */
class ComponentCategory(override var x: Double, override var y: Double, override var subComponents: ArrayList<Component>,
                        /**
                         * Category to render
                         */
                        var category: Module.Category) : Component() {
    override var width: Double = 0.0

    /**
     * Renders the Category's window
     */
    override fun render() {
        drawBox(" ", category.name, hover = false, on = true)
        if (clickGuiPositions.positions.containsKey(category)) {
            if ((clickGuiPositions.positions[category] ?: return).expanded) {
                for (module in subComponents) {
                    module.render()
                }
            }
        }
    }

    /**
     * Generates the modules position and size
     */
    fun generatePositions() {
        subComponents.clear()
        width = mc.textRenderer.getStringWidth(category.name) + 8.0
        for (module in mM.getModulesInCategory(category)) {
            val modNameWidth: Int = mc.textRenderer.getStringWidth("""> ${module.name}""") + 8
            if (modNameWidth > width) width = modNameWidth.toDouble()
            for (settingName in module.settings.settingsDef.keys) {
                var settingNameWidth: Int = mc.textRenderer.getStringWidth(""" > $settingName""") + 8
                if ((module.settings.settings[settingName] ?: return).type == 0) {
                    for (mode in (module.settings.settingsDef[settingName] ?: return).modes ?: return) {
                        val modeWidth = mc.textRenderer.getStringWidth(""" > $settingName: $mode""") + 8
                        settingNameWidth = modeWidth
                    }
                }
                if (settingNameWidth > width) width = settingNameWidth.toDouble()
            }
        }
        var currentY = y + height
        for (module in mM.getModulesInCategory(category)) {
            val newModuleComponent = ComponentModule(x, currentY, ArrayList(), module, width)
            subComponents.add(newModuleComponent)
            currentY += newModuleComponent.totalHeight
        }
    }

    init {
        generatePositions()
    }
}