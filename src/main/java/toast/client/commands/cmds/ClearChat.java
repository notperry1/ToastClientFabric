package toast.client.commands.cmds;

import toast.client.ToastClient;
import toast.client.commands.Command;
import toast.client.utils.Logger;

public class ClearChat extends Command {
    public ClearChat() {
        super("ClearChat", ToastClient.cmdPrefix + "clearchat", "Clears all messages in chat", false, "clearchat");
    }

    @Override
    public void run(String[] args) throws InterruptedException {
        if (mc.player == null) return;
        if (mc.inGameHud.getChatHud() != null) {
            mc.inGameHud.getChatHud().clear(true);
            Logger.message("Cleared chat", Logger.EMPTY, false);
        } else {
            Logger.message("Fuck I don't know chat hud is null ¯\\_(ツ)_/¯", Logger.EMPTY, false);
        }
    }
}
