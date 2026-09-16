package tomeko.hychatter.config.chat.handlers.modules.blockers

import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.chat.handlers.ChatReceiveModule
import tomeko.hychatter.config.data.providers.LanguageData
import tomeko.hychatter.config.events.ChatReceiveEvent

object EarnedCoinsAndExpRemover : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        val message = event.unformattedMessage.replace("\n", "")
        if (message.matches(LanguageData.EARNED_COINS_AND_EXP)) {
            event.cancelled = true
        }
    }

    override val isEnabled
        get() = HyChatterConfig.removeEarnedCoinsAndExp
    override val priority = -1
}

