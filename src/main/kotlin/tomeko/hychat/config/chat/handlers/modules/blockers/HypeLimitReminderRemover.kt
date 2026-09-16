package tomeko.hychat.config.chat.handlers.modules.blockers

import tomeko.hychat.config.HyChatConfig
import tomeko.hychat.config.chat.handlers.ChatReceiveModule
import tomeko.hychat.config.data.providers.LanguageData
import tomeko.hychat.config.events.ChatReceiveEvent

object HypeLimitReminderRemover : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        if (event.unformattedMessage.startsWith(LanguageData.HYPE_LIMIT)) {
            event.cancelled = true
        }
    }

    override val isEnabled
        get() = HyChatConfig.removeHypeLimitReminder
    override val priority = -1
}

