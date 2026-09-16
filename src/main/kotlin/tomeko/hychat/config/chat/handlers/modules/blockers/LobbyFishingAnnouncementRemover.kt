package tomeko.hychat.config.chat.handlers.modules.blockers

import tomeko.hychat.config.HyChatConfig
import tomeko.hychat.config.chat.handlers.ChatReceiveModule
import tomeko.hychat.config.data.providers.LanguageData
import tomeko.hychat.config.events.ChatReceiveEvent

object LobbyFishingAnnouncementRemover : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        if (event.unformattedMessage.matches(LanguageData.LOBBY_FISHING_ANNOUNCEMENT)) {
            event.cancelled = true
        }
    }

    override val isEnabled
        get() = HyChatConfig.removeLobbyFishingMsgs
    override val priority = -1
}

