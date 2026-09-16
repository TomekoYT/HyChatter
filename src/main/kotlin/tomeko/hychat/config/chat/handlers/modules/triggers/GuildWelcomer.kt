package tomeko.hychat.config.chat.handlers.modules.triggers

import tomeko.hychat.config.HyChatConfig
import tomeko.hychat.config.chat.handlers.ChatReceiveModule
import tomeko.hychat.config.data.providers.LanguageData
import tomeko.hychat.config.events.ChatReceiveEvent
import tomeko.hychat.utils.ChatUtils

object GuildWelcomer : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        LanguageData.GUILD_JOIN.find(event.unformattedMessage)?.let { match ->
            val player = match.groups["player"]?.value ?: return
            ChatUtils.queueMessage("/gc Welcome to the guild $player!")
        }
    }

    override val isEnabled
        get() = HyChatConfig.guildWelcomeMessage
}
