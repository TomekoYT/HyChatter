package tomeko.hychatter.config.chat.handlers.modules.triggers

import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.chat.handlers.ChatReceiveModule
import tomeko.hychatter.config.data.providers.LanguageData
import tomeko.hychatter.config.events.ChatReceiveEvent
import tomeko.hychatter.utils.ChatUtils

object BroadcastLevelUp : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        if (event.unformattedMessage.contains(": ")) return
        LanguageData.LEVEL_UP.find(event.unformattedMessage)?.let { match ->
            val level = match.groups["level"]?.value ?: return
            ChatUtils.queueMessage("/gc Level up! I am now Hypixel Level $level!")
        }
    }

    override val isEnabled
        get() = HyChatterConfig.broadcastLevelUp
}
