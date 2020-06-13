package toast.client.utils

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.hud.InGameHud
import java.awt.Color

/**
 * A set of utilities for drawing things on screen on a 2D plane
 */
object TwoDRenderUtils {
    private val textRenderer = MinecraftClient.getInstance().textRenderer

    /**
     * Draws text at the given coordinates
     *
     * @param text  Text to draw
     * @param x     X coordinate of the top left corner of the text
     * @param y     Y coordinate of the top left corner of the text
     * @param color Color of the box
     */
    @JvmStatic
    fun drawText(text: String?, x: Int, y: Int, color: Int) {
        textRenderer.drawWithShadow(text, x.toFloat(), y.toFloat(), color)
        RenderSystem.pushMatrix()
        RenderSystem.popMatrix()
    }

    /**
     * Draws a rectangle at the given coordinates
     *
     * @param x      X coordinate of the top left corner of the rectangle
     * @param y      Y coordinate of the top left corner of the rectangle
     * @param width  Width of the box
     * @param height Height of the box
     * @param color  Color of the box
     */
    @JvmStatic
    fun drawRect(x: Int, y: Int, width: Int, height: Int, color: Int) {
        InGameHud.fill(x, y, x + width, y + height, color)
        RenderSystem.pushMatrix()
        RenderSystem.popMatrix()
    }

    /**
     * Draws a box at the given coordinates
     *
     * @param x      X coordinate of the top left corner of the inside of the box
     * @param y      Y coordinate of the top left corner of the inside of the box
     * @param width  Width of the inside of the box
     * @param height Height of the inside of the box
     * @param lW     Width of the lines of the box
     * @param color  Color of the box
     */
    @JvmStatic
    fun drawHollowRect(x: Int, y: Int, width: Int, height: Int, lW: Int, color: Int) {
        drawRect(x, y, width, lW, color) // top line
        drawRect(x, y, lW, height, color) // left line
        drawRect(x, y + height, width + lW, lW, color) // bottom line
        drawRect(x + width, y, lW, height, color) // right line
    }

    /**
     * Draw a text box at the given coordinates
     *
     * @param x           X coordinate of the top left corner of the inside of the text box
     * @param y           Y coordinate of the top left corner of the inside of the text box
     * @param width       Width of the inside of the box
     * @param height      Height of the inside of the box
     * @param color       Color of the box outlines
     * @param textColor   Color of the text
     * @param prefixColor Color of the text prefix
     * @param bgColor     Color of the box's background
     * @param prefix      Text to prepend to the main text
     * @param text        Main text to put in the box
     */
    @JvmStatic
    fun drawTextBox(
            x: Int,
            y: Int,
            width: Int,
            height: Int,
            color: Int,
            textColor: Int,
            prefixColor: Int,
            bgColor: Int,
            prefix: String?,
            text: String?
    ) {
        drawRect(x + 1, y + 1, width - 1, height - 1, bgColor)
        drawHollowRect(x, y, width, height, 1, color)
        drawText(prefix, x + 4, y + 4, prefixColor)
        drawText(text, x + textRenderer.getStringWidth(prefix) + 1, y + 4, textColor)
    }

    /**
     * Draw a text box at the given coordinates with predefined colors
     *
     * @param x           X coordinate of the top left corner of the inside of the text box
     * @param y           Y coordinate of the top left corner of the inside of the text box
     * @param width       Width of the inside of the box
     * @param height      Height of the inside of the box
     * @param prefix      Text to prepend to the main text
     * @param text        Main text to put in the box
     */
    @JvmStatic
    fun drawTextBox(
            x: Int,
            y: Int,
            width: Int,
            height: Int,
            hover: Boolean,
            on: Boolean,
            prefix: String?,
            text: String?
    ) {
        val bgColor = if (hover) Color(131, 212, 252, 92).rgb else Color(0, 0, 0, 64).rgb
        val textColor = if (on) Color(255, 255, 255, 255).rgb else Color(100, 100, 100, 255).rgb
        drawTextBox(x, y, width, height, Color(0, 0, 0, 255).rgb, textColor, Color(8, 189, 8, 255).rgb, bgColor, prefix, text)
    }

    /**
     * Check if the mouse if over a box on screen
     *
     * @param mouseX Current X coordinate of the mouse
     * @param mouseY Current Y coordinate of the mouse
     * @param x      X coordinate of the top left corner of the inside of the text box
     * @param y      Y coordinate of the top left corner of the inside of the text box
     * @param width  Width of the inside of the box
     * @param height Height of the inside of the box
     */
    @JvmStatic
    fun isMouseOverRect(
            mouseX: Double,
            mouseY: Double,
            x: Double,
            y: Double,
            width: Int,
            height: Int
    ): Boolean {
        var xOver = false
        var yOver = false
        if (mouseX >= x && mouseX <= width + x) {
            xOver = true
        }
        if (mouseY >= y && mouseY <= height + y) {
            yOver = true
        }
        return xOver && yOver
    }
}