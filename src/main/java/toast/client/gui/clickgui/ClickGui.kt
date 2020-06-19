package toast.client.gui.clickgui

import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.LiteralText
import org.lwjgl.glfw.GLFW
import toast.client.ToastClient.CONFIG_MANAGER
import toast.client.ToastClient.MODULE_MANAGER
import toast.client.gui.clickgui.ClickGuiPositions.Companion.positions
import toast.client.gui.clickgui.component.components.*
import toast.client.modules.Module
import toast.client.modules.render.ClickGui

/**
 * Main ClickGui class, manages the GUI and its rendering
 */
class ClickGui : Screen(LiteralText("ClickGui")) {
    private var categories: ArrayList<ComponentCategory> = ArrayList()
    private var pressedOnCategory = false
    private var catPressedOn: Module.Category? = null
    private var mouseIsDragging = false
    private var clickedOnce = false
    private var released = false
    private var didDrag = false
    private var pressedSlider: ComponentSlider? = null
    private var clickRel: Pair<Double, Double> = Pair(0.0, 0.0)

    /**
     * ClickGUI state storage
     */
    private val clickGuiPositions: ClickGuiPositions = ClickGuiPositions()

    /**
     * Renders everything
     */
    override fun render(mouseX: Int, mouseY: Int, delta: Float) {
        loop@ for (category in categories) {
            category.render(mouseX.toDouble(), mouseY.toDouble())
            if (pressedOnCategory) {
                if (catPressedOn == category.category) {
                    when {
                        mouseIsDragging -> {
                            val oldCatPos: Pair<Double, Double> = Pair(category.x, category.y)
                            category.x = mouseX - clickRel.first
                            category.y = mouseY - clickRel.second
                            val delta: Pair<Double, Double> = Pair(category.x - oldCatPos.first, category.y - oldCatPos.second)
                            ((positions[category.category]) ?: continue@loop).x = category.x
                            (positions[category.category] ?: continue@loop).y = category.y
                            category.updateSubComponentsPos(delta)
                            didDrag = true
                        }
                        released -> {
                            if (!clickedOnce && !didDrag) {
                                (positions[category.category]
                                        ?: continue@loop).expanded = !(positions[category.category]
                                        ?: continue@loop).expanded
                                clickedOnce = true
                            }
                            pressedOnCategory = false
                            catPressedOn = null
                            didDrag = false
                            released = false
                        }
                    }
                }
            } else if (mouseIsDragging && pressedSlider != null) {
                val slider = pressedSlider ?: continue@loop
                val mousePosPercentage = ((mouseX - (slider.x + slider.barX) / (slider.settingDef.maxValue
                        ?: continue@loop) - (slider.settingDef.minValue
                        ?: continue@loop)) * slider.barLength)
                slider.setting.value = ((slider.settingDef.minValue?.plus((slider.settingDef.maxValue
                        ?: continue@loop)))?.div(100)
                        ?: continue@loop) * mousePosPercentage + (slider.settingDef.minValue ?: continue@loop)
                didDrag = true
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
                clickRel = Pair(mouseX - category.x, mouseY - category.y)
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
                                var state = positions[category.category]!!.expandedModule[component.module.name]
                                state = if (state == null) true else !state
                                positions[category.category]!!.expandedModule[component.module.name] = state
                                category.generatePositions()
                            }
                            GLFW.GLFW_MOUSE_BUTTON_LEFT -> {
                                component.module.toggle()
                                CONFIG_MANAGER.writeModules()
                            }
                        }
                    }
                    is ComponentToggle -> {
                        val state = component.setting.enabled ?: break@loop
                        component.setting.enabled = !state
                        CONFIG_MANAGER.writeConfig()
                    }
                    is ComponentMode -> {
                        val modes = component.settingDef.modes ?: break@loop
                        val mode = component.setting.mode ?: break@loop
                        component.setting.mode = if (modes.indexOf(mode) == modes.size - 1) modes[0] else modes[modes.indexOf(mode) + 1]
                        CONFIG_MANAGER.writeConfig()
                    }
                    is ComponentSlider -> {
                        pressedSlider = component
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
            mouseIsDragging = true
            didDrag = true
        }
        return true
    }

    /**
     * Executes when mouse is released
     */
    override fun mouseReleased(mouseX: Double, mouseY: Double, button: Int): Boolean {
        if (mouseIsDragging) {
            mouseIsDragging = false
            clickGuiPositions.writePositions()
        }
        clickRel = Pair(0.0, 0.0)
        released = true
        pressedSlider = null
        return true
    }

    init {
        categories.clear()
        clickGuiPositions.loadPositions()
        var x = 5.0
        for (category in Module.Category.values()) {
            var y = 10.0
            var catX = x
            if (positions.containsKey(category)) {
                catX = positions[category]!!.x
                y = positions[category]!!.y
            } else {
                positions[category] = ClickGuiPositions.Position(catX, y, false, HashMap())
            }
            val categoryToAdd = ComponentCategory(catX, y, ArrayList(), category)
            categories.add(categoryToAdd)
            x += categoryToAdd.width + 5
        }
        clickGuiPositions.writePositions()
    }

    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        return if (keyCode == 256 && shouldCloseOnEsc()) {
            onClose()
            true
        } else if (keyCode == 258) {
            val notShifting = !hasShiftDown()
            if (!changeFocus(notShifting)) {
                changeFocus(notShifting)
            }
            true
        } else {
            super.keyPressed(keyCode, scanCode, modifiers)
        }
    }

    override fun onClose() {
        MODULE_MANAGER.getModule("ClickGui")?.disable()
        minecraft!!.openScreen(null as Screen?)
    }
}