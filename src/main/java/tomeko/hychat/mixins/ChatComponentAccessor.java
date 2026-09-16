package tomeko.hychat.mixins;

//? if 1.8.9 {
/*import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.ChatLine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import java.util.List;

@Mixin(GuiNewChat.class)
public interface ChatComponentAccessor {
    @Accessor("chatLines")
    List<ChatLine> getAllMessages();

    @Accessor("drawnChatLines")
    List<ChatLine> getTrimmedMessages();

    @Accessor("scrollPos")
    int getChatScrollbarPos();

    @Accessor("scrollPos")
    void setChatScrollbarPos(int pos);
}
*///?} else {
import net.minecraft.client.gui.components.ChatComponent;
//~ if <26.1 'multiplayer.chat.GuiMessage' -> 'GuiMessage'
import net.minecraft.client.multiplayer.chat.GuiMessage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(ChatComponent.class)
public interface ChatComponentAccessor {
    @Accessor
    List<GuiMessage> getAllMessages();

    @Accessor
    List<GuiMessage.Line> getTrimmedMessages();

    @Accessor
    int getChatScrollbarPos();

    @Accessor
    void setChatScrollbarPos(int pos);
}
//?}