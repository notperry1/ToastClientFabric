package toast.client.gui.clickgui.component

import net.minecraft.client.MinecraftClient
import toast.client.ToastClient.MODULE_MANAGER
import toast.client.modules.ModuleManager
import toast.client.utils.TwoDRenderUtils
import kotlin.math.roundToInt

/**
 * ClickGui component
 */
abstract class Component {
    /**
     * X coordinate of the top left corner of the component on-screen
     */
    abstract var x: Double

    /**
     * Y coordinate of the top left corner of the component on-screen
     */
    abstract var y: Double

    /**
     * Width of the component (positive numbers extend the component to the right)
     */
    abstract var width: Double

    /**
     * Components that are inside of this components area
     */
    abstract var subComponents: ArrayList<Component>

    /**
     * Shorthand accessor for the current minecraft instance
     */
    protected val mc: MinecraftClient = MinecraftClient.getInstance()

    /**
     * How much padding text should have on above and under it
     */
    private val textSpacingVertical = 4

    /**
     * Height of text in the gui
     */
    private val textHeight: Int = mc.textRenderer.fontHeight

    /**
     * Height of the text in the GUI + padding
     */
    protected val textHeightWithSpacing: Int = textHeight + textSpacingVertical * 2

    /**
     * Height of the component (positive numbers extend component downwards)
     */
    var height: Double = textHeightWithSpacing - 2.0

    /**
     * Shorthand accessor for the 2D render utilities
     */
    private val renderUtils: TwoDRenderUtils = TwoDRenderUtils

    /**
     * Shorthand accessor for the 2D render utilities
     */
    protected val mM: ModuleManager = MODULE_MANAGER

    /**
     * Contains code to render the component
     */
    abstract fun render(mouseX: Double, mouseY: Double)

    /**
     * Draws the outline and background for the component
     */
    fun drawBox(prefix: String, text: String, hover: Boolean, on: Boolean) {
        renderUtils.drawTextBox(x.roundToInt(), y.roundToInt(), width.roundToInt(), height.roundToInt(), hover, on, prefix, text)
    }

    /**
     * Function to get the component at a coordinate on screen
     */
    fun getSubComponentAtCoords(x: Double, y: Double): Component? {
        for (comp in subComponents) if (comp.isMouseOver(x, y)) return comp
        for (comp in subComponents) {
            val subComp: Component? = comp.getSubComponentAtCoords(x, y)
            if (subComp != null) return subComp
        }
        return null
    }

    /**
     * Checks if the mouse is over the component
     */
    fun isMouseOver(x: Double, y: Double): Boolean = x >= this.x && y >= this.y && x <= this.x + this.width && y <= this.y + textHeightWithSpacing - 2.0

    /**
     * Updates the X and Y positions of all subcomponents
     */
    fun updateSubComponentsPos(deltaX: Double, deltaY: Double) {
        for (comp in subComponents) {
            comp.x += deltaX
            comp.y += deltaY
            comp.updateSubComponentsPos(deltaX, deltaY)
        }
    }
}