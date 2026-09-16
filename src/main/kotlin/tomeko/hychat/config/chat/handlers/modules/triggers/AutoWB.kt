package tomeko.hychat.config.chat.handlers.modules.triggers

import org.polyfrost.oneconfig.utils.v1.Multithreading
import tomeko.hychat.config.HyChatConfig
import tomeko.hychat.config.chat.handlers.ChatReceiveModule
import tomeko.hychat.config.data.providers.LanguageData
import tomeko.hychat.config.events.ChatReceiveEvent
import tomeko.hychat.utils.ChatUtils
import java.util.concurrent.TimeUnit

object AutoWB : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        val match = LanguageData.PLAYER_CONNECTION_STATUS.find(event.unformattedMessage) ?: return

        val status = match.groups["status"]?.value ?: return
        if (status != "joined") return

        val player = match.groups["player"]?.value ?: return
        val type = match.groups["type"]?.value ?: return

        val command = when (type) {
            "Guild" -> if (HyChatConfig.guildAutoWB) "/gc" else return
            "Friend" -> if (HyChatConfig.friendsAutoWB) "/msg $player" else return
            else -> return
        }
        val message = if (HyChatConfig.randomAutoWB) {
            getMessage(player)
        } else {
            HyChatConfig.autoWBMessage1.replace("%player%", player)
        }

        Multithreading.schedule(
            { ChatUtils.queueMessage("$command $message") },
            HyChatConfig.autoWBCooldown.toLong(),
            TimeUnit.SECONDS
        )
    }

    private fun getMessage(name: String): String {
        val validMessages = HyChatConfig.wbMessages.filter { it.isNotBlank() }

        val message = if (validMessages.isEmpty()) {
            "Welcome Back!"
        } else {
            validMessages.random()
        }

        return message.replace("%player%", name)
    }

    override val isEnabled
        get() = HyChatConfig.autoWB
}
