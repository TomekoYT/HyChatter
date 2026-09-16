package tomeko.hychat.config.chat.handlers.modules.triggers

import tomeko.hychat.mixins.FramerateLimitTrackerAccessor
import org.polyfrost.oneconfig.utils.v1.dsl.mc
import tomeko.hychat.config.chat.handlers.ChatReceiveModule
import tomeko.hychat.config.data.providers.LanguageData
import tomeko.hychat.config.events.ChatReceiveEvent
import tomeko.hychat.utils.ChatUtils

//? if >=1.21.11 {
import net.minecraft.util.Util
import tomeko.hychat.config.HyChatConfig
//?} else
//import net.minecraft.Util

object AutoAFKReply : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        val latestInputTime = (mc.framerateLimitTracker as FramerateLimitTrackerAccessor).latestInputTime
        if (Util.getMillis() - latestInputTime < HyChatConfig.afkTimeout * 60L * 1000L) return

        LanguageData.PRIVATE_MESSAGE.find(event.unformattedMessage)?.let { match ->
            val type = match.groups["type"]?.value ?: return
            if (type != "From") return

            val player = match.groups["player"]?.value ?: return
            val message = HyChatConfig.afkReplyMessage.replace("%player%", player)
            ChatUtils.queueMessage("/msg $player $message")
        }
    }

    override val isEnabled
        get() = HyChatConfig.autoReplyAfk
}
