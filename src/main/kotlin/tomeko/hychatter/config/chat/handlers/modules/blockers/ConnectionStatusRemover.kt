package tomeko.hychatter.config.chat.handlers.modules.blockers

import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.chat.handlers.ChatReceiveModule
import tomeko.hychatter.config.data.providers.LanguageData
import tomeko.hychatter.config.events.ChatReceiveEvent

object ConnectionStatusRemover : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        if (event.unformattedMessage.matches(LanguageData.PLAYER_CONNECTION_STATUS)) {
            event.cancelled = true
        }
    }

    override val isEnabled
        get() = HyChatterConfig.removeConnectionStatus
    override val priority = -1
}
