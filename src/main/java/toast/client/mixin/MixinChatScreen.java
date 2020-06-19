package toast.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import toast.client.ToastClient;
import toast.client.utils.TwoDRenderUtils;
import java.awt.Color;

@Environment(EnvType.CLIENT)
@Mixin(ChatScreen.class)
public class MixinChatScreen {
    @Shadow protected TextFieldWidget chatField;

    @Inject(at = @At("RETURN"), method = "render", cancellable = true)
    public void render(int mouseX, int mouseY, float delta, CallbackInfo info) {
        if (chatField.getText().startsWith(ToastClient.cmdPrefix)) {
            TwoDRenderUtils.drawHollowRect(chatField.x - 3, chatField.y - 3, chatField.getWidth(), 13, 1, new Color(0, 255, 255).getRGB());
        }
    }
}
