package tomeko.hychatter.mixins;

//? if 1.8.9 {
/*import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C01PacketChatMessage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import tomeko.hychatter.event.ClientSendMessageEvents;

@Mixin(EntityPlayerSP.class)
abstract class EntityPlayerSPMixin {
    @WrapOperation(
            method = "sendChatMessage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/NetHandlerPlayClient;addToSendQueue(Lnet/minecraft/network/Packet;)V"
            )
    )
    private void hychatter$wrapSendChatMessage(
            NetHandlerPlayClient instance,
            Packet<?> packet,
            Operation<Void> original,
            @Local(argsOnly = true) String message
    ) {
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

            original.call(instance, new C01PacketChatMessage("/" + command));
            ClientSendMessageEvents.COMMAND.invoker().onSendCommandMessage(command);
        } else {
            if (!ClientSendMessageEvents.ALLOW_CHAT.invoker().allowSendChatMessage(message)) {
                return;
            }
            message = ClientSendMessageEvents.MODIFY_CHAT.invoker().modifySendChatMessage(message);
            if (message == null) {
                return;
            }

            original.call(instance, new C01PacketChatMessage(message));
            ClientSendMessageEvents.CHAT.invoker().onSendChatMessage(message);
        }
    }
}
*///?}