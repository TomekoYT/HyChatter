package tomeko.hychatter.config.chat.handlers.modules.triggers

import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.chat.handlers.ChatReceiveModule
import tomeko.hychatter.config.data.providers.LanguageData
import tomeko.hychatter.config.events.ChatReceiveEvent
import tomeko.hychatter.utils.ChatUtils

object GuildWelcomer : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        LanguageData.GUILD_JOIN.find(event.unformattedMessage)?.let { match ->
            val player = match.groups["player"]?.value ?: return
            ChatUtils.queueMessage("/gc Welcome to the guild $player!")
        }
    }

    override val isEnabled
        get() = HyChatterConfig.guildWelcomeMessage
}
