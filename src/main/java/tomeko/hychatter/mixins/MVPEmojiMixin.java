package tomeko.hychatter.mixins;

//? if 1.8.9 {
/*import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import tomeko.hychatter.chat.MVPEmoji;

@Mixin(GuiScreen.class)
public abstract class MVPEmojiMixin {
    @ModifyVariable(method = "sendChatMessage(Ljava/lang/String;Z)V", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private String hychatter$replaceMessage(String message) {
        return MVPEmoji.INSTANCE.replaceWithEmoji(message);
    }
}
*///?}