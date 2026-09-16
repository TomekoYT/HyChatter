package tomeko.hychat.config.chat.handlers.modules.blockers

import tomeko.hychat.config.HyChatConfig
import tomeko.hychat.config.chat.handlers.ChatReceiveModule
import tomeko.hychat.config.data.providers.LanguageData
import tomeko.hychat.config.events.ChatReceiveEvent

object BridgeOwnGoalDeathRemover : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        if (event.unformattedMessage == LanguageData.BRIDGE_OWN_GOAL) {
            event.cancelled = true
        }
    }

    override val isEnabled
        get() = HyChatConfig.removeBridgeOwnGoalDeathMsg
    override val priority = -1
}
