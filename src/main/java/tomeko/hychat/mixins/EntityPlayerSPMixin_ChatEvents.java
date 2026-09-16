package tomeko.hychat.mixins;

//? if 1.8.9 {
/*import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.client.entity.EntityPlayerSP;
import org.polyfrost.oneconfig.api.event.v1.EventManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tomeko.hychat.config.events.ChatSendEvent;

@Mixin(value = EntityPlayerSP.class, priority = Integer.MAX_VALUE)
abstract class EntityPlayerSPMixin_ChatEvents {

    @Inject(
            method = "sendChatMessage",
            at = @At("HEAD"),
            cancellable = true
    )
    private void createChatSendEvent(
            String content,
            CallbackInfo ci,
            @Share("chatSendEvent") LocalRef<ChatSendEvent> chatSendEvent
    ) {
        ChatSendEvent event = new ChatSendEvent(content);
        EventManager.INSTANCE.post(event);
        chatSendEvent.set(event);

        if (event.cancelled) {
            ci.cancel();
        }
    }

    @ModifyVariable(
            method = "sendChatMessage",
            at = @At("HEAD"),
            ordinal = 0,
            argsOnly = true
    )
    private String modifySentMessage(
            String content,
            @Share("chatSendEvent") LocalRef<ChatSendEvent> chatSendEvent
    ) {
        return chatSendEvent.get().getMessage();
    }
}
*///?}