package tomeko.hychatter.config.chat.handlers.modules.blockers

import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.chat.handlers.ChatReceiveModule
import tomeko.hychatter.config.events.ChatReceiveEvent

object QuestBlocker : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        if (event.unformattedMessage.startsWith("Automatically activated:")) {
            event.cancelled = true
        }
    }

    override val isEnabled
        get() = HyChatterConfig.removeAutoQuests
    override val priority = -1
}

