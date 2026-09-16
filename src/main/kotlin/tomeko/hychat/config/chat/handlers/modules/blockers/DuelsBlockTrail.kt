package tomeko.hychat.config.chat.handlers.modules.blockers

import tomeko.hychat.config.HyChatConfig
import tomeko.hychat.config.chat.handlers.ChatReceiveModule
import tomeko.hychat.config.data.providers.LanguageData
import tomeko.hychat.config.events.ChatReceiveEvent

object DuelsBlockTrail : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        if (event.unformattedMessage == LanguageData.DUELS_BLOCK_TRAIL) {
            event.cancelled = true
        }
    }

    override val isEnabled
        get() = HyChatConfig.removeDuelsBlockTrailDisabled
    override val priority = -1
}

