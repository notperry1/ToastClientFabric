package toast.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import toast.client.ToastClient;
import toast.client.utils.KeyUtil;

import static toast.client.ToastClient.CONFIG_MANAGER;
import static toast.client.ToastClient.MODULE_MANAGER;

@Environment(EnvType.CLIENT)
@Mixin(Keyboard.class)
public class MixinKeyboard {
    @Inject(at = @At(value = "RETURN"), method = "onKey")
    public void onKey(long window, int key, int scancode, int action, int j, CallbackInfo ci) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.currentScreen == null) {
            if (ToastClient.cmdPrefix != null && ToastClient.cmdPrefix.length() == 1 && action == GLFW.GLFW_PRESS && key == KeyUtil.INSTANCE.getKeyCode(ToastClient.cmdPrefix)) mc.openScreen(new ChatScreen(ToastClient.cmdPrefix));
            MODULE_MANAGER.onKey(key, action);
            CONFIG_MANAGER.checkForMacro(key, action);
        }
    }
}
