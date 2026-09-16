package tomeko.hychat.config.chat.handlers.modules.triggers

import tomeko.hychat.config.HyChatConfig
import tomeko.hychat.config.chat.handlers.ChatReceiveModule
import tomeko.hychat.config.data.providers.LanguageData
import tomeko.hychat.config.events.ChatReceiveEvent
import tomeko.hychat.utils.ChatUtils

object ThankWatchdog : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        if (event.unformattedMessage == LanguageData.WATCHDOG_ANNOUNCEMENT
            || event.unformattedMessage.startsWith(LanguageData.WATCHDOG_BAN)
        ) {
            ChatUtils.queueMessage("/ac Thanks Watchdog!")
        }
    }

    override val isEnabled
        get() = HyChatConfig.thankWatchdog
}
