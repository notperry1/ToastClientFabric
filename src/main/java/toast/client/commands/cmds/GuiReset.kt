package toast.client.commands.cmds

import toast.client.ToastClient
import toast.client.commands.Command
import toast.client.gui.clickgui.ClickGuiPositions
import toast.client.utils.Logger

/**
 * Command to regenerate ClickGui category positions according to current window resolution
 */
class GuiReset : Command("GuiReset", "${ToastClient.cmdPrefix}guireset", "Makes ClickGui fit on screen as much as possible", false, "guireset", "gr", "fixgui") {
    override fun run(args: Array<String>) {
        ClickGuiPositions.positions.clear()
        ClickGuiPositions().writePositions()
        Logger.message("ClickGui has been reset.", Logger.INFO, true)
    }
}