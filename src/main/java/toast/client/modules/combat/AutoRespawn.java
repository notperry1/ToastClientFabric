package toast.client.modules.combat;

import net.minecraft.client.gui.screen.DeathScreen;
import toast.client.event.EventImpl;
import toast.client.event.events.player.EventUpdate;
import toast.client.modules.Module;
import toast.client.utils.TimerUtil;

public class AutoRespawn extends Module {

    private final TimerUtil timer = new TimerUtil();

    public AutoRespawn() {
        super("AutoRespawn", "Automatically presses the respawn button for you.", Category.COMBAT, -1);
        this.settings.addSlider("Speed", 1, 2, 20);
    }

    public void onEnable() {
        timer.reset();
    }

    public void onDisable() {
        timer.reset();
    }

    @EventImpl
    public void onEvent(EventUpdate event) {
        if (mc.currentScreen != null) {
            if (this.timer.isDelayComplete((long) (this.getDouble("Speed") * 1000L))) {
                this.timer.setLastMS();
                if (mc.currentScreen instanceof DeathScreen) {
                    mc.player.requestRespawn();
                    mc.openScreen(null);
                }
            }
        }
    }
}
