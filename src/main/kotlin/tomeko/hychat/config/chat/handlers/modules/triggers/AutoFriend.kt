package tomeko.hychat.config.chat.handlers.modules.triggers

import org.polyfrost.oneconfig.api.notifications.v1.Notifications
import tomeko.hychat.config.HyChatConfig
import tomeko.hychat.config.chat.handlers.ChatReceiveModule
import tomeko.hychat.config.data.providers.LanguageData
import tomeko.hychat.config.events.ChatReceiveEvent
import tomeko.hychat.utils.ChatUtils
import tomeko.hychat.utils.Constants

object AutoFriend : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        if (event.plainMessage.contains(": ")) return

        val match = LanguageData.FRIEND_REQUEST.find(event.unformattedMessage) ?: return
        var player = match.groups["player"]?.value ?: return
        if (player.startsWith("[")) player = player.substringAfter("] ")

        ChatUtils.queueMessage("/friend $player")
        Notifications.info(
            Constants.MOD_NAME,
            "Automatically added $player to your friends list."
        )
    }

    override val isEnabled
        get() = HyChatConfig.autoFriend
}
