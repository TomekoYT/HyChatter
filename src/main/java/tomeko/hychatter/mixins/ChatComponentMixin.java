package tomeko.hychatter.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import tomeko.hychatter.restylers.LineSeparators;

import java.util.List;

//? if 1.8.9 {
/*import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.IChatComponent;

@Mixin(GuiNewChat.class)
abstract class ChatComponentMixin {
    @ModifyExpressionValue(method = "setChatLine", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiUtilRenderComponents;splitText(Lnet/minecraft/util/IChatComponent;ILnet/minecraft/client/gui/FontRenderer;ZZ)Ljava/util/List;"))
    private List<IChatComponent> hychatter$trimLineSeparators(List<IChatComponent> lines) {
        return LineSeparators.process(lines);
    }

    @ModifyArg(method = "drawChat", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawStringWithShadow(Ljava/lang/String;FFI)I"))
    private String hychatter$cleanLineSeparator(String text) {
        return LineSeparators.clean(text);
    }
}
*///?} else {
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.util.FormattedCharSequence;

@Mixin(ChatComponent.class)
abstract class ChatComponentMixin {
    @ModifyExpressionValue(method = "addMessageToDisplayQueue", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/chat/GuiMessage;splitLines(Lnet/minecraft/client/gui/Font;I)Ljava/util/List;"))
    private List<FormattedCharSequence> hychatter$processLineSeparators(List<FormattedCharSequence> lines, @Local(ordinal = 0) int maxWidth) {
        return LineSeparators.process(lines, maxWidth);
    }
}
//?}
