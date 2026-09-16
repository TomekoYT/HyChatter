package tomeko.hychat.config.chat.handlers.modules.blockers

import tomeko.hychat.config.HyChatConfig
import tomeko.hychat.config.chat.handlers.ChatReceiveModule
import tomeko.hychat.config.data.providers.LanguageData
import tomeko.hychat.config.events.ChatReceiveEvent

object GameTipsRemover : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        if (event.unformattedMessage.matches(LanguageData.GAME_TIPS)) {
            event.cancelled = true
        }
    }

    override val isEnabled
        get() = HyChatConfig.removeGameTips
    override val priority = -1
}

