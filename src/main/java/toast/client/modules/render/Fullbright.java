package toast.client.modules.render;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import toast.client.event.EventImpl;
import toast.client.event.events.player.EventUpdate;
import toast.client.modules.Module;

import java.util.Objects;

public class Fullbright extends Module {

    private static boolean increasedGamma = false;
    private static double previousGamma = 0;
    private static String lastMode;

    public Fullbright() {
        super("Fullbright", "Night Vision, allows you to see in the dark.", Module.Category.RENDER, -1);
        this.settings.addMode("Mode", "Gamma", "Potion", "Gamma");
    }

    @EventImpl
    public void onTick(EventUpdate event) {
        if (mc.player == null) return; // avoid excessive logs and client crashing
        if (this.isMode("Gamma") && !increasedGamma) {
            if (lastMode != null) {
                if (lastMode.equals("Potion")) {
                    assert mc.player != null;
                    if (Objects.requireNonNull(mc.player.getStatusEffect(StatusEffects.NIGHT_VISION)).getAmplifier() == 69)
                        mc.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
                }
            } else {
                lastMode = this.getMode();
            }
            if (mc.options.gamma < 16) increaseGamma();
            lastMode = "Gamma";
        } else if (this.isMode("Potion")) {
            assert mc.player != null;
            mc.player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 82820, 42069));
            lastMode = "Potion";
        }
    }

    public void onDisable() {
        if (mc.player == null) return;
        mc.options.gamma = previousGamma;
        increasedGamma = false;
        if (mc.player.hasStatusEffect(StatusEffects.NIGHT_VISION)) {
            if (Objects.requireNonNull(mc.player.getStatusEffect(StatusEffects.NIGHT_VISION)).getAmplifier() == 42069)
                mc.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
        }
    }

    public void onEnable() {
        if (mc.player == null) return;
        previousGamma = mc.options.gamma;
    }

    private void increaseGamma() {
        mc.options.gamma = 1000;
        increasedGamma = true;
    }
}
