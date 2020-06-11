package toast.client.commands.cmds

import toast.client.ToastClient
import toast.client.commands.Command
import toast.client.utils.Logger

/**
 * Command to regenerate ClickGui category positions according to current window resolution
 */
class GuiReset : Command("GuiReset", "${ToastClient.cmdPrefix}guireset", "Makes ClickGui fit on screen as much as possible", false, "guireset", "gr", "fixgui") {
    override fun run(args: Array<String>) {
        //ToastClient.clickGui.settings.initCategoryPositions()
        //ToastClient.clickGui.settings.savePositions()
        //Logger.message("Re-arranged ClickGui, if you still have problems, try setting your gui scale to minimum.", Logger.INFO, true)
        Logger.message("ClickGui is being rewritten, nothing was done.", Logger.INFO, true)
    }
}