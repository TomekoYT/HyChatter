package tomeko.hychatter.mixins;

//? if ornithe {
/*import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.C01PacketChatMessage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tomeko.hychatter.event.ClientSendMessageEvents;

@Mixin(EntityPlayerSP.class)
public abstract class EntityPlayerSPMixin {
    @Shadow
    public NetHandlerPlayClient sendQueue;

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    private void hychatter$onSendChatMessage(String message, CallbackInfo ci) {
        ci.cancel();

        if (message == null || message.isEmpty()) {
            return;
        }

        if (message.startsWith("/")) {
            String command = message.substring(1);

            if (!ClientSendMessageEvents.ALLOW_COMMAND.invoker().allowSendCommandMessage(command)) {
                return;
            }
            command = ClientSendMessageEvents.MODIFY_COMMAND.invoker().modifySendCommandMessage(command);
            if (command == null) {
                return;
            }

            this.sendQueue.addToSendQueue(new C01PacketChatMessage("/" + command));
            ClientSendMessageEvents.COMMAND.invoker().onSendCommandMessage(command);
        } else {
            if (!ClientSendMessageEvents.ALLOW_CHAT.invoker().allowSendChatMessage(message)) {
                return;
            }
            message = ClientSendMessageEvents.MODIFY_CHAT.invoker().modifySendChatMessage(message);
            if (message == null) {
                return;
            }

            this.sendQueue.addToSendQueue(new C01PacketChatMessage(message));
            ClientSendMessageEvents.CHAT.invoker().onSendChatMessage(message);
        }
    }
}
*///?}