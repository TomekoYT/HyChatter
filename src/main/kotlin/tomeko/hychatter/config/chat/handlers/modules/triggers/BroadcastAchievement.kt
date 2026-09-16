package tomeko.hychatter.config.chat.handlers.modules.triggers

import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.chat.handlers.ChatReceiveModule
import tomeko.hychatter.config.data.providers.LanguageData
import tomeko.hychatter.config.events.ChatReceiveEvent
import tomeko.hychatter.utils.ChatUtils

object BroadcastAchievement : ChatReceiveModule {
    private val achievements = mutableSetOf<String>()

    override fun onChatReceived(event: ChatReceiveEvent) {
        LanguageData.ACHIEVEMENT_UNLOCKED.find(event.unformattedMessage)?.let { match ->
            val achievement = match.groups["achievement"]?.value ?: return
            achievements.add(achievement)
            ChatUtils.queueMessage("/gc Achievement unlocked! I unlocked the $achievement achievement!")
        }
    }

    override val isEnabled
        get() = HyChatterConfig.broadcastAchievements
}
