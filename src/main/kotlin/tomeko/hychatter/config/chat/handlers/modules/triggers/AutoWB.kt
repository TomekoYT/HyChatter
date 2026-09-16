package tomeko.hychatter.config.chat.handlers.modules.triggers

import org.polyfrost.oneconfig.utils.v1.Multithreading
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.chat.handlers.ChatReceiveModule
import tomeko.hychatter.config.data.providers.LanguageData
import tomeko.hychatter.config.events.ChatReceiveEvent
import tomeko.hychatter.utils.ChatUtils
import java.util.concurrent.TimeUnit

object AutoWB : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        val match = LanguageData.PLAYER_CONNECTION_STATUS.find(event.unformattedMessage) ?: return

        val status = match.groups["status"]?.value ?: return
        if (status != "joined") return

        val player = match.groups["player"]?.value ?: return
        val type = match.groups["type"]?.value ?: return

        val command = when (type) {
            "Guild" -> if (HyChatterConfig.guildAutoWB) "/gc" else return
            "Friend" -> if (HyChatterConfig.friendsAutoWB) "/msg $player" else return
            else -> return
        }
        val message = if (HyChatterConfig.randomAutoWB) {
            getMessage(player)
        } else {
            HyChatterConfig.autoWBMessage1.replace("%player%", player)
        }

        Multithreading.schedule(
            { ChatUtils.queueMessage("$command $message") },
            HyChatterConfig.autoWBCooldown.toLong(),
            TimeUnit.SECONDS
        )
    }

    private fun getMessage(name: String): String {
        val validMessages = HyChatterConfig.wbMessages.filter { it.isNotBlank() }

        val message = if (validMessages.isEmpty()) {
            "Welcome Back!"
        } else {
            validMessages.random()
        }

        return message.replace("%player%", name)
    }

    override val isEnabled
        get() = HyChatterConfig.autoWB
}
