package toast.client.modules.render

import org.lwjgl.glfw.GLFW
import toast.client.gui.clickgui.ClickGui
import toast.client.modules.Module

/**
 * Module to wrap the ClickGUI
 */
class ClickGui : Module("ClickGui", "The gui for managing modules.", Category.RENDER, GLFW.GLFW_KEY_RIGHT_SHIFT) {
    override fun onEnable() {
        if (mc.player != null) {
            if (mc.currentScreen == null) {
                mc.openScreen(ClickGui())
            }
        }
    }

    override fun onDisable() {
        if (mc.currentScreen is ClickGui && mc.player != null) {
            mc.openScreen(null)
        }
    }

    init {
        settings.addBoolean("Descriptions", true)
    }
}