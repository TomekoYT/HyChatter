package tomeko.hychat.config.chat.handlers.modules.blockers

import tomeko.hychat.config.HyChatConfig
import tomeko.hychat.config.chat.handlers.ChatReceiveModule
import tomeko.hychat.config.data.providers.LanguageData
import tomeko.hychat.config.events.ChatReceiveEvent

object EarnedCoinsAndExpRemover : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        val message = event.unformattedMessage.replace("\n", "")
        if (message.matches(LanguageData.EARNED_COINS_AND_EXP)) {
            event.cancelled = true
        }
    }

    override val isEnabled
        get() = HyChatConfig.removeEarnedCoinsAndExp
    override val priority = -1
}

