package toast.client.gui.clickgui

import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.LiteralText
import org.lwjgl.glfw.GLFW
import toast.client.gui.clickgui.component.components.ComponentCategory
import toast.client.gui.clickgui.component.components.ComponentMode
import toast.client.gui.clickgui.component.components.ComponentModule
import toast.client.gui.clickgui.component.components.ComponentToggle
import toast.client.modules.Module
import toast.client.utils.ConfigManager.Companion.clickGuiPositions

/**
 * Main ClickGui class, manages the GUI and its rendering
 */
class ClickGui : Screen(LiteralText("ClickGui")) {
    private var categories: ArrayList<ComponentCategory> = ArrayList()
    private var pressedOnCategory = false
    private var catPressedOn: Module.Category? = null
    private var dragDeltaX = 0.0
    private var dragDeltaY = 0.0
    private var mouseIsDragging = false
    private var clickedOnce = false
    private var released = false
    private var didDrag = false

    /**
     * Renders everything
     */
    override fun render(mouseX: Int, mouseY: Int, delta: Float) {
        loop@ for (category in categories) {
            category.render()
            if (pressedOnCategory) {
                if (catPressedOn == category.category) {
                    when {
                        mouseIsDragging -> {
                            category.x += dragDeltaX
                            category.y += dragDeltaY
                            ((clickGuiPositions.positions[category.category]) ?: continue@loop).x = category.x
                            (clickGuiPositions.positions[category.category] ?: continue@loop).y = category.y
                            category.updateSubComponentsPos(dragDeltaX, dragDeltaY)
                            didDrag = true
                            dragDeltaY = 0.0
                            dragDeltaX = 0.0
                        }
                        released -> {
                            if (!clickedOnce && !didDrag) {
                                (clickGuiPositions.positions[category.category]
                                        ?: return).expanded = !(clickGuiPositions.positions[category.category]?.expanded
                                        ?: return)
                                clickedOnce = true
                            }
                            pressedOnCategory = false
                            catPressedOn = null
                            didDrag = false
                            released = false
                        }
                    }
                }
            } else {
                released = false
                didDrag = false
                released = false
                clickedOnce = false
                catPressedOn = null
            }
        }
    }

    /**
     * Executes when mouse is clicked
     */
    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            for (category in categories) if (category.isMouseOver(mouseX, mouseY)) {
                catPressedOn = category.category
                pressedOnCategory = true
                clickedOnce = false
                return true
            }
        }
        pressedOnCategory = false
        catPressedOn = null
        loop@ for (category in categories) {
            val component = category.getSubComponentAtCoords(mouseX, mouseY)
            if (component != null) {
                when (component) {
                    is ComponentModule -> {
                        when (button) {
                            GLFW.GLFW_MOUSE_BUTTON_RIGHT -> {
                                var state = clickGuiPositions.positions[category.category]!!.expandedModule[component.module.name]
                                state = if (state == null) true else !state
                                clickGuiPositions.positions[category.category]!!.expandedModule[component.module.name] = state
                                category.generatePositions()
                            }
                            GLFW.GLFW_MOUSE_BUTTON_LEFT -> {
                                component.module.toggle()
                            }
                        }
                    }
                    is ComponentToggle -> {
                        val state = component.setting.enabled ?: break@loop
                        component.setting.enabled = !state
                    }
                    is ComponentMode -> {
                        val modes = component.settingDef.modes ?: break@loop
                        val mode = component.setting.mode ?: break@loop
                        component.setting.mode = if (modes.indexOf(mode) == modes.size - 1) modes[0] else modes[modes.indexOf(mode)]
                        println("mode clicked")
                    }
                }
                break@loop
            }
        }
        return true
    }

    /**
     * Executes when mouse is dragged
     */
    override fun mouseDragged(mouseX: Double, mouseY: Double, button: Int, deltaX: Double, deltaY: Double): Boolean {
        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            dragDeltaX = deltaX
            dragDeltaY = deltaY
            mouseIsDragging = true
            didDrag = true
        }
        return true
    }

    /**
     * Executes when mouse is released
     */
    override fun mouseReleased(mouseX: Double, mouseY: Double, button: Int): Boolean {
        mouseIsDragging = false
        dragDeltaY = 0.0
        dragDeltaX = 0.0
        released = true
        return true
    }

    init {
        categories.clear()
        var x = 5.0
        for (category in Module.Category.values()) {
            var y = 10.0
            var catX = x
            if (clickGuiPositions.positions.containsKey(category)) {
                catX = clickGuiPositions.positions[category]!!.x
                y = clickGuiPositions.positions[category]!!.y
            } else {
                clickGuiPositions.positions[category] = ClickGuiPositions.Position(catX, y, false, HashMap())
            }
            val categoryToAdd = ComponentCategory(catX, y, ArrayList(), category)
            categories.add(categoryToAdd)
            x += categoryToAdd.width + 5
        }
    }
}