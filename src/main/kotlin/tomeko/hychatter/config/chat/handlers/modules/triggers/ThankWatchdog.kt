package tomeko.hychatter.config.chat.handlers.modules.triggers

import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.chat.handlers.ChatReceiveModule
import tomeko.hychatter.config.data.providers.LanguageData
import tomeko.hychatter.config.events.ChatReceiveEvent
import tomeko.hychatter.utils.ChatUtils

object ThankWatchdog : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        if (event.unformattedMessage == LanguageData.WATCHDOG_ANNOUNCEMENT
            || event.unformattedMessage.startsWith(LanguageData.WATCHDOG_BAN)
        ) {
            ChatUtils.queueMessage("/ac Thanks Watchdog!")
        }
    }

    override val isEnabled
        get() = HyChatterConfig.thankWatchdog
}
