package toast.client.modules.render;

import toast.client.modules.Module;

public class HUD extends Module {
    public HUD() {
        super("HUD", "Heads up display, gives information about the client.", Category.RENDER, -1);
        this.settings.addBoolean("Rainbow", true);
        this.settings.addBoolean("Watermark", true);
        this.settings.addSlider("Watermark Size", 0.5, 0.75, 1.5);
        this.settings.addBoolean("Inventory", true);
        this.settings.addBoolean("Inventory BG", false);
        this.settings.addBoolean("Armor", false);
        this.settings.addBoolean("SortedSet", true);
        this.settings.addBoolean("Right line", true);
        this.settings.addBoolean("Left line", true);
        this.settings.addBoolean("Middle line", false);
    }
}
