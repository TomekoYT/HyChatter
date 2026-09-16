package tomeko.hychatter.config.chat.handlers.modules.blockers

import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.chat.handlers.ChatReceiveModule
import tomeko.hychatter.config.data.providers.LanguageData
import tomeko.hychatter.config.events.ChatReceiveEvent

object SkyblockWelcomeRemover : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        if (event.unformattedMessage == LanguageData.SKYBLOCK_WELCOME) {
            event.cancelled = true
        }
    }

    override val isEnabled
        get() = HyChatterConfig.removeSkyblockWelcome
    override val priority = -1
}

