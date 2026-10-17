package tomeko.hychatter.mixins;

//? if 1.8.9 {
/*import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import tomeko.hychatter.event.ClientSendMessageEvents;

@Mixin(EntityPlayerSP.class)
abstract class EntityPlayerSPMixin {
    @WrapMethod(method = "sendChatMessage")
    private void hychatter$wrapSendChatMessage(String message, Operation<Void> original) {
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

            original.call("/" + command);
            ClientSendMessageEvents.COMMAND.invoker().onSendCommandMessage(command);
        } else {
            if (!ClientSendMessageEvents.ALLOW_CHAT.invoker().allowSendChatMessage(message)) {
                return;
            }

            message = ClientSendMessageEvents.MODIFY_CHAT.invoker().modifySendChatMessage(message);
            if (message == null) {
                return;
            }

            original.call(message);
            ClientSendMessageEvents.CHAT.invoker().onSendChatMessage(message);
        }
    }
}
*///?}