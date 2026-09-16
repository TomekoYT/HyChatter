package tomeko.hychatter.config.chat.handlers.modules.blockers

import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.chat.handlers.ChatReceiveModule
import tomeko.hychatter.config.data.providers.LanguageData
import tomeko.hychatter.config.events.ChatReceiveEvent

object GiftBlocker : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        if (event.unformattedMessage.matches(LanguageData.RANK_GIFTING)) {
            event.cancelled = true
        }
    }

    override val isEnabled
        get() = HyChatterConfig.removeGiftedRanksAmount
    override val priority = -1
}

