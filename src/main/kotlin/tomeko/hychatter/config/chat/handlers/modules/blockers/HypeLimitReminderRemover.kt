package tomeko.hychatter.config.chat.handlers.modules.blockers

import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.chat.handlers.ChatReceiveModule
import tomeko.hychatter.config.data.providers.LanguageData
import tomeko.hychatter.config.events.ChatReceiveEvent

object HypeLimitReminderRemover : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        if (event.unformattedMessage.startsWith(LanguageData.HYPE_LIMIT)) {
            event.cancelled = true
        }
    }

    override val isEnabled
        get() = HyChatterConfig.removeHypeLimitReminder
    override val priority = -1
}

