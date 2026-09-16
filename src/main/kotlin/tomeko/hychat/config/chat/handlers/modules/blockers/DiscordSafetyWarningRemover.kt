package tomeko.hychat.config.chat.handlers.modules.blockers

//? if 1.8.9 {
/*import tomeko.hychat.utils.string
import tomeko.hychat.utils.siblings
import tomeko.hychat.utils.copy
import tomeko.hychat.config.HyChatConfig
import tomeko.hychat.config.chat.handlers.ChatReceiveModule
import tomeko.hychat.config.data.providers.LanguageData
import tomeko.hychat.config.events.ChatReceiveEvent

object DiscordSafetyWarningRemover : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        if (event.unformattedMessage.contains(LanguageData.DISCORD_SAFETY_WARNING)) {
            val message = event.message.copy()
            message.siblings.removeIf { it.string == LanguageData.DISCORD_SAFETY_WARNING }
            while (message.siblings.isNotEmpty() && message.siblings.last().string.isBlank()) {
                message.siblings.removeLast()
            }
            event.message = message
        }
    }

    override val isEnabled
        get() = HyChatConfig.removeDiscordSafetyWarning
    override val priority = -1
}

*///?} else {
import tomeko.hychat.config.HyChatConfig
import tomeko.hychat.config.chat.handlers.ChatReceiveModule
import tomeko.hychat.config.data.providers.LanguageData
import tomeko.hychat.config.events.ChatReceiveEvent

object DiscordSafetyWarningRemover : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        if (event.unformattedMessage.contains(LanguageData.DISCORD_SAFETY_WARNING)) {
            val message = event.message.copy()
            message.siblings.removeIf { it.string == LanguageData.DISCORD_SAFETY_WARNING }
            while (message.siblings.isNotEmpty() && message.siblings.last().string.isBlank()) {
                message.siblings.removeLast()
            }
            event.message = message
        }
    }

    override val isEnabled
        get() = HyChatConfig.removeDiscordSafetyWarning
    override val priority = -1
}
//?}