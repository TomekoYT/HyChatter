package tomeko.hychat.config.chat.handlers.modules.triggers

import tomeko.hychat.config.HyChatConfig
import tomeko.hychat.config.chat.handlers.ChatReceiveModule
import tomeko.hychat.config.data.providers.LanguageData
import tomeko.hychat.config.events.ChatReceiveEvent
import tomeko.hychat.utils.ChatUtils

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
        get() = HyChatConfig.broadcastAchievements
}
