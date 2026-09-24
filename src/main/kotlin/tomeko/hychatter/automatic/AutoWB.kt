package tomeko.hychatter.automatic

import org.polyfrost.oneconfig.utils.v1.Multithreading
//? if 1.8.9 {
//import net.minecraft.util.IChatComponent as Component
//?} else {
import net.minecraft.network.chat.Component
//?}
//? if ornithe {
//import tomeko.hychatter.event.ClientReceiveMessageEvents
//?} else {
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
//?}
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.LanguageData
import tomeko.hychatter.utils.ChatUtils
import java.util.concurrent.TimeUnit
import kotlin.text.get

object AutoWB {
    fun register() {
        ClientReceiveMessageEvents.GAME.register(::onGameReceive)
    }

    private fun onGameReceive(message: Component, fromActionBar: Boolean) {
        if (fromActionBar || !HyChatterConfig.autoWB) return

        val text =
            //? if 1.8.9 {
            //message.unformattedText
            //?} else {
            message.string
            //?}
        val match = LanguageData.PLAYER_CONNECTION_STATUS.find(text) ?: return
        val status = match.groups["status"]?.value ?: return
        if (status != "joined") return

        val player = match.groups["player"]?.value ?: return
        val type = match.groups["type"]?.value ?: return
        val command = when (type) {
            "Guild" -> if (HyChatterConfig.guildAutoWB) "/gc" else return
            "Friend" -> if (HyChatterConfig.friendsAutoWB) "/msg $player" else return
            else -> return
        }
        val chatMessage = if (HyChatterConfig.randomAutoWB) getMessage(player)
        else HyChatterConfig.autoWBMessage1.replace("%player%", player)

        Multithreading.schedule(
            { ChatUtils.queueMessage("$command $chatMessage") },
            HyChatterConfig.autoWBCooldown.toLong(), TimeUnit.SECONDS
        )
    }

    private fun getMessage(name: String): String {
        val validMessages = HyChatterConfig.wbMessages.filter { it.isNotBlank() }
        val message = if (validMessages.isEmpty()) "Welcome Back!" else validMessages.random()
        return message.replace("%player%", name)
    }
}
