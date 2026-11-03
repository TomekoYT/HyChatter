package tomeko.hychatter.mixins;

//? if fabric {
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import net.minecraft.client.gui.components.CommandSuggestions;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tomeko.hychatter.automatic.AutocompleteCore;
import tomeko.hychatter.automatic.NameAutocomplete;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Mixin(CommandSuggestions.class)
abstract class CommandSuggestionsMixin {
    @Final @Shadow EditBox input;
    @Shadow private CompletableFuture<Suggestions> pendingSuggestions;
    @Shadow private ParseResults<ClientSuggestionProvider> currentParse;

    @Shadow
    protected abstract void updateUsageInfo(ParseResults<ClientSuggestionProvider> parse, Suggestions suggestions);

    @Inject(
            method = "updateCommandInfo",
            at = @At(value = "INVOKE", target = "Lcom/mojang/brigadier/CommandDispatcher;getCompletionSuggestions(Lcom/mojang/brigadier/ParseResults;I)Ljava/util/concurrent/CompletableFuture;"),
            cancellable = true
    )
    private void hychatter$completeNames(CallbackInfo ci) {
        AutocompleteCore.Completion completion = NameAutocomplete.INSTANCE.complete(this.input.getValue(), this.input.getCursorPosition());
        if (completion == null) {
            return;
        }

        List<Suggestion> list = new ArrayList<>();
        StringRange range = StringRange.between(completion.getStart(), completion.getEnd());
        for (String option : completion.getOptions()) {
            list.add(new Suggestion(range, option));
        }
        if (list.isEmpty()) {
            range = StringRange.at(completion.getEnd());
            list.add(new Suggestion(range, ""));
        }

        Suggestions suggestions = new Suggestions(range, list);
        this.pendingSuggestions = CompletableFuture.completedFuture(suggestions);
        this.updateUsageInfo(this.currentParse, suggestions);
        ci.cancel();
    }
}
//?}
