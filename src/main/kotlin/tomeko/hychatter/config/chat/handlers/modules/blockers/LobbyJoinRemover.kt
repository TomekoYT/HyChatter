package tomeko.hychatter.config.chat.handlers.modules.blockers

import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.chat.handlers.ChatReceiveModule
import tomeko.hychatter.config.data.providers.LanguageData
import tomeko.hychatter.config.events.ChatReceiveEvent

object LobbyJoinRemover : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        if (!event.unformattedMessage.contains(": ") && event.unformattedMessage.contains(LanguageData.LOBBY_JOIN)) {
            event.cancelled = true
        }
    }

    override val isEnabled
        get() = HyChatterConfig.removeLobbyJoin
    override val priority = -1
}

