package tomeko.hychat.config.chat.handlers.modules.triggers

import org.polyfrost.oneconfig.api.notifications.v1.Notifications
import tomeko.hychat.config.chat.handlers.ChatReceiveModule
import tomeko.hychat.config.data.providers.LanguageData
import tomeko.hychat.config.events.ChatReceiveEvent
import tomeko.hychat.utils.ChatUtils
import tomeko.hychat.utils.Constants
import java.util.*

object SilentRemoval : ChatReceiveModule {
    val removalQueue = mutableSetOf<String>()

    override fun onChatReceived(event: ChatReceiveEvent) {
        val match = LanguageData.PLAYER_CONNECTION_STATUS.find(event.unformattedMessage) ?: return

        val status = match.groups["status"]?.value ?: return
        if (status != "left") return

        val player = match.groups["player"]?.value ?: return
        if (removalQueue.contains(player.lowercase(Locale.ROOT))) {
            ChatUtils.queueMessage("/f remove $player")
            Notifications.success(
                Constants.MOD_NAME,
                "Silently removed $player from your friends list."
            )
            removalQueue.remove(player)
        }
    }
}
