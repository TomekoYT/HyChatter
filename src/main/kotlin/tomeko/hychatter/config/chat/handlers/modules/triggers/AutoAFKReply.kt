package tomeko.hychatter.config.chat.handlers.modules.triggers

//? if 1.8.9 {
/*import tomeko.hychat.utils.LegacyInputTracker
import org.polyfrost.oneconfig.utils.v1.dsl.mc
import tomeko.hychat.config.HyChatterConfig
import tomeko.hychat.config.chat.handlers.ChatReceiveModule
import tomeko.hychat.config.data.providers.LanguageData
import tomeko.hychat.config.events.ChatReceiveEvent
import tomeko.hychat.utils.ChatUtils


object AutoAFKReply : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        val latestInputTime = LegacyInputTracker.latestInputTime
        if (System.currentTimeMillis() - latestInputTime < HyChatterConfig.afkTimeout * 60L * 1000L) return

        LanguageData.PRIVATE_MESSAGE.find(event.unformattedMessage)?.let { match ->
            val type = match.groups["type"]?.value ?: return
            if (type != "From") return

            val player = match.groups["player"]?.value ?: return
            val message = HyChatterConfig.afkReplyMessage.replace("%player%", player)
            ChatUtils.queueMessage("/msg $player $message")
        }
    }

    override val isEnabled
        get() = HyChatterConfig.autoReplyAfk
}

*///?} else {
import tomeko.hychatter.mixins.FramerateLimitTrackerAccessor
import org.polyfrost.oneconfig.utils.v1.dsl.mc
import tomeko.hychatter.config.chat.handlers.ChatReceiveModule
import tomeko.hychatter.config.data.providers.LanguageData
import tomeko.hychatter.config.events.ChatReceiveEvent
import tomeko.hychatter.utils.ChatUtils

//? if >=1.21.11 {
import net.minecraft.util.Util
import tomeko.hychatter.config.HyChatterConfig
//?} else
//import net.minecraft.Util

object AutoAFKReply : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        val latestInputTime = (mc.framerateLimitTracker as FramerateLimitTrackerAccessor).latestInputTime
        if (Util.getMillis() - latestInputTime < HyChatterConfig.afkTimeout * 60L * 1000L) return

        LanguageData.PRIVATE_MESSAGE.find(event.unformattedMessage)?.let { match ->
            val type = match.groups["type"]?.value ?: return
            if (type != "From") return

            val player = match.groups["player"]?.value ?: return
            val message = HyChatterConfig.afkReplyMessage.replace("%player%", player)
            ChatUtils.queueMessage("/msg $player $message")
        }
    }

    override val isEnabled
        get() = HyChatterConfig.autoReplyAfk
}
//?}