package tomeko.hychatter.config.chat.handlers.modules.blockers

import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.chat.handlers.ChatReceiveModule
import tomeko.hychatter.config.data.providers.LanguageData
import tomeko.hychatter.config.events.ChatReceiveEvent

object ReplayRecordedRemover : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        if (event.unformattedMessage.matches(LanguageData.REPLAY_RECORDED)) {
            event.cancelled = true
        }
    }

    override val isEnabled
        get() = HyChatterConfig.removeReplayMessage
    override val priority = -1
}

