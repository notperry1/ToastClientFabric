package toast.client.gui.clickgui.component.components

import toast.client.gui.clickgui.ClickGuiPositions
import toast.client.gui.clickgui.component.Component
import toast.client.modules.Module

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
    override fun render(mouseX: Double, mouseY: Double) {
        drawBox(" ", category.name, isMouseOver(mouseX, mouseY), on = true)
        if (ClickGuiPositions.positions.containsKey(category)) {
            if ((ClickGuiPositions.positions[category] ?: return).expanded) {
                for (module in subComponents) {
                    module.render(mouseX, mouseY)
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