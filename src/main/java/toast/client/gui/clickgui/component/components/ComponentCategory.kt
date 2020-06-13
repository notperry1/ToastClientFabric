package toast.client.gui.clickgui.component.components

import toast.client.gui.clickgui.ClickGuiPositions
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
    private var poses: LinkedHashMap<Module.Category, ClickGuiPositions.Position> = LinkedHashMap()

    /**
     * Renders the Category's window
     */
    override fun render(mouseX: Double, mouseY: Double) {
        poses = positions
        drawBox(" ", category.name, isMouseOver(mouseX, mouseY), on = true)
        if (positions.containsKey(category)) {
            (positions[category] ?: return).expanded
            println("test")
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
        width = mc.textRenderer.getStringWidth(category.name) + 8.0
        for (module in mM.getModulesInCategory(category)) {
            val modNameWidth: Int = mc.textRenderer.getStringWidth("""> ${module.name}""") + 8
            if (modNameWidth > width) width = modNameWidth.toDouble()
            for (settingName in module.settings.settingsDef.keys) {
                var settingNameWidth: Int = mc.textRenderer.getStringWidth(""" > $settingName""") + 8
                val type = (module.settings.settings[settingName] ?: continue).type
                if (type == 0 || type == 1) {
                    for (mode in (module.settings.settingsDef[settingName] ?: continue).modes ?: continue) {
                        val modeWidth = mc.textRenderer.getStringWidth(""" > $settingName: ${if (type == 0) mode else "000.00"}""") + 8
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