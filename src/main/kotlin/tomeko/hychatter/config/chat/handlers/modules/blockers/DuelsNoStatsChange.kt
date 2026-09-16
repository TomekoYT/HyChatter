package tomeko.hychatter.config.chat.handlers.modules.blockers

import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.chat.handlers.ChatReceiveModule
import tomeko.hychatter.config.data.providers.LanguageData
import tomeko.hychatter.config.events.ChatReceiveEvent

object DuelsNoStatsChange : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        if (event.unformattedMessage.matches(LanguageData.DUELS_NO_STATS_CHANGE)) {
            event.cancelled = true
        }
    }

    override val isEnabled
        get() = HyChatterConfig.removeDuelsNoStatsChange
    override val priority = -1
}

