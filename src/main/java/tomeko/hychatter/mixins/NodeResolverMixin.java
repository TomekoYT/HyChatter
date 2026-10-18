package tomeko.hychatter.mixins;

//? if fabric {
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import tomeko.hychatter.commands.PlayCommand;

@Mixin(targets = "net.minecraft.network.protocol.game.ClientboundCommandsPacket$NodeResolver")
abstract class NodeResolverMixin {
    @ModifyExpressionValue(method = "resolve", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/protocol/game/ClientboundCommandsPacket$NodeResolver;resolve(I)Lcom/mojang/brigadier/tree/CommandNode;", ordinal = 1))
    private CommandNode<?> hychatter$overridePlayCommand(CommandNode<?> original) {
        if (original instanceof LiteralCommandNode<?> literal && literal.getLiteral().equals("play")) {
            return PlayCommand.getCommandNode();
        }
        return original;
    }
}
//?}
