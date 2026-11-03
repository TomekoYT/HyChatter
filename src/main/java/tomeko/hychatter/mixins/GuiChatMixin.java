package tomeko.hychatter.mixins;

//? if 1.8.9 {
/*import net.minecraft.client.gui.GuiChat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tomeko.hychatter.automatic.NameAutocomplete;

@Mixin(GuiChat.class)
abstract class GuiChatMixin {
    @Shadow private boolean waitingOnAutocomplete;

    @Inject(method = "sendAutocompleteRequest", at = @At("HEAD"), cancellable = true)
    private void hychatter$completeNames(String textBeforeCursor, String word, CallbackInfo ci) {
        if (NameAutocomplete.INSTANCE.completeTab((GuiChat) (Object) this, textBeforeCursor, word)) {
            this.waitingOnAutocomplete = true;
            ci.cancel();
        }
    }
}
*///?}
