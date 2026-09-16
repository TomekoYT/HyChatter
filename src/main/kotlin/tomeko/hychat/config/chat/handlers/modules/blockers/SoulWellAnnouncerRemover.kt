package tomeko.hychat.config.chat.handlers.modules.blockers

import tomeko.hychat.config.HyChatConfig
import tomeko.hychat.config.chat.handlers.ChatReceiveModule
import tomeko.hychat.config.data.providers.LanguageData
import tomeko.hychat.config.events.ChatReceiveEvent

object SoulWellAnnouncerRemover : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        if (event.unformattedMessage.matches(LanguageData.SOUL_WELL_FIND)) {
            event.cancelled = true
        }
    }

    override val isEnabled
        get() = HyChatConfig.removeSoulWellAnnouncements
    override val priority = -1
}

