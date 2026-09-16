package tomeko.hychat.config.chat.handlers.modules.blockers

import tomeko.hychat.config.HyChatConfig
import tomeko.hychat.config.chat.handlers.ChatReceiveModule
import tomeko.hychat.config.events.ChatReceiveEvent

object QuestBlocker : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        if (event.unformattedMessage.startsWith("Automatically activated:")) {
            event.cancelled = true
        }
    }

    override val isEnabled
        get() = HyChatConfig.removeAutoQuests
    override val priority = -1
}

