package toast.client.gui.clickgui

import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.LiteralText
import org.lwjgl.glfw.GLFW
import toast.client.gui.clickgui.component.components.ComponentCategory
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

    /**
     * Renders everything
     */
    override fun render(mouseX: Int, mouseY: Int, delta: Float) {
        for (category in categories) {
            category.render()
            if (pressedOnCategory && !mouseIsDragging && !clickedOnce) {
                (clickGuiPositions.positions[category.category]
                        ?: return).expanded = !(clickGuiPositions.positions[category.category]?.expanded ?: return)
                clickedOnce = true
            } else if (pressedOnCategory && mouseIsDragging && catPressedOn == category.category) {
                category.x += dragDeltaX
                category.y += dragDeltaY
                ((clickGuiPositions.positions[category.category]) ?: continue).x = category.x
                (clickGuiPositions.positions[category.category] ?: continue).y = category.y
                category.updateSubComponentsPos(dragDeltaX, dragDeltaY)
                dragDeltaY = 0.0
                dragDeltaX = 0.0
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
        }
        return true
    }

    /**
     * Executes when mouse is released
     */
    override fun mouseReleased(mouseX: Double, mouseY: Double, button: Int): Boolean {
        pressedOnCategory = false
        catPressedOn = null
        mouseIsDragging = false
        dragDeltaY = 0.0
        dragDeltaX = 0.0
        clickedOnce = false
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