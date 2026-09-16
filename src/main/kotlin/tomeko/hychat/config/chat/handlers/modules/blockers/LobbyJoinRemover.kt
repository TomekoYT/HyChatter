package tomeko.hychat.config.chat.handlers.modules.blockers

import tomeko.hychat.config.HyChatConfig
import tomeko.hychat.config.chat.handlers.ChatReceiveModule
import tomeko.hychat.config.data.providers.LanguageData
import tomeko.hychat.config.events.ChatReceiveEvent

object LobbyJoinRemover : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        if (!event.unformattedMessage.contains(": ") && event.unformattedMessage.contains(LanguageData.LOBBY_JOIN)) {
            event.cancelled = true
        }
    }

    override val isEnabled
        get() = HyChatConfig.removeLobbyJoin
    override val priority = -1
}

