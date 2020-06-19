package toast.client.gui.clickgui.component.components

import toast.client.gui.clickgui.ClickGuiPositions.Companion.positions
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
        if (positions.containsKey(category)) {
            (positions[category] ?: return).expanded
            if ((positions[category] ?: return).expanded) {
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
        width = 0.0
        for (module in mM.getModulesInCategory(category)) {
            val modNameWidth: Int = mc.textRenderer.getStringWidth(module.name)
            if (modNameWidth > width) width += modNameWidth.toDouble() - width
            for (settingName in module.settings.settingsDef.keys) {
                var settingNameWidth: Double = mc.textRenderer.getStringWidth(""" $settingName""").toDouble()
                val type = (module.settings.settings[settingName] ?: continue).type
                var suffix = 0.0
                if (type == 0) {
                    for (mode in (module.settings.settingsDef[settingName] ?: continue).modes ?: continue) {
                        val modeWidth = mc.textRenderer.getStringWidth(""": $mode""").toDouble()
                        if (modeWidth > suffix) suffix = modeWidth
                    }
                }
                if (suffix == 0.0 && type == 1) suffix = mc.textRenderer.getStringWidth(": 000.00").toDouble() // TODO: make this dynamic
                settingNameWidth += suffix
                if (settingNameWidth > width) width += settingNameWidth - width
            }
        }
        width += 8.0 + mc.textRenderer.getStringWidth("> ")
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