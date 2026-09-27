package tomeko.hychatter.mixins;

//? if 1.8.9 {
/*import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.IChatComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tomeko.hychatter.event.ClientPlayConnectionEvents;
import tomeko.hychatter.event.ClientReceiveMessageEvents;

@Mixin(NetHandlerPlayClient.class)
public abstract class NetHandlerPlayClientMixin {
    @Inject(
            method = "handleChat",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/PacketThreadUtil;checkThreadAndEnqueue(Lnet/minecraft/network/Packet;Lnet/minecraft/network/INetHandler;Lnet/minecraft/util/IThreadListener;)V",
                    shift = At.Shift.AFTER
            ),
            cancellable = true
    )
    private void hychatter$onHandleChat(S02PacketChat packet, CallbackInfo ci) {
        boolean overlay = packet.getType() == 2;

        IChatComponent message = hychatter$process(packet.getChatComponent(), overlay);

        ci.cancel();

        if (message == null) {
            return;
        }

        Minecraft mc = Minecraft.getMinecraft();
        if (overlay) {
            mc.ingameGUI.setRecordPlaying(message, false);
        } else {
            mc.ingameGUI.getChatGUI().printChatMessage(message);
        }
    }

    private static IChatComponent hychatter$process(IChatComponent message, boolean overlay) {
        boolean allowed = ClientReceiveMessageEvents.ALLOW_GAME.invoker().allowReceiveGameMessage(message, overlay);
        if (!overlay) {
            allowed &= ClientReceiveMessageEvents.ALLOW_CHAT.invoker().allowReceiveChatMessage(message);
        }
        if (!allowed) {
            return null;
        }

        message = ClientReceiveMessageEvents.MODIFY_GAME.invoker().modifyReceivedGameMessage(message, overlay);
        if (message == null) {
            return null;
        }
        if (!overlay) {
            message = ClientReceiveMessageEvents.MODIFY_CHAT.invoker().modifyReceivedChatMessage(message);
            if (message == null) {
                return null;
            }
        }

        ClientReceiveMessageEvents.GAME.invoker().onReceiveGameMessage(message, overlay);
        if (!overlay) {
            ClientReceiveMessageEvents.CHAT.invoker().onReceiveChatMessage(message);
        }
        return message;
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void hychatter$onInit(Minecraft minecraft, GuiScreen previousScreen, NetworkManager networkManager, GameProfile gameProfile, CallbackInfo ci) {
        ClientPlayConnectionEvents.INIT.invoker().onInit((NetHandlerPlayClient) (Object) this, networkManager, minecraft);
    }

    @Inject(method = "handleJoinGame", at = @At("RETURN"))
    private void hychatter$onJoinGame(S01PacketJoinGame packet, CallbackInfo ci) {
        ClientPlayConnectionEvents.JOIN.invoker().onJoin((NetHandlerPlayClient) (Object) this, Minecraft.getMinecraft());
    }

    @Inject(method = "onDisconnect", at = @At("HEAD"))
    private void hychatter$onDisconnect(IChatComponent reason, CallbackInfo ci) {
        ClientPlayConnectionEvents.DISCONNECT.invoker().onDisconnect((NetHandlerPlayClient) (Object) this, Minecraft.getMinecraft());
    }
}
*///?}