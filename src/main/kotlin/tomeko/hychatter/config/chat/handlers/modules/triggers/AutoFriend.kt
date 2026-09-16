package tomeko.hychatter.config.chat.handlers.modules.triggers

import org.polyfrost.oneconfig.api.notifications.v1.Notifications
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.chat.handlers.ChatReceiveModule
import tomeko.hychatter.config.data.providers.LanguageData
import tomeko.hychatter.config.events.ChatReceiveEvent
import tomeko.hychatter.utils.ChatUtils
import tomeko.hychatter.utils.Constants

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
        get() = HyChatterConfig.autoFriend
}
