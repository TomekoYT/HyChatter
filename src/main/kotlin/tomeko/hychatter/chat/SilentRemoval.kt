package tomeko.hychatter.chat

import org.polyfrost.oneconfig.api.notifications.v1.Notifications
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
import tomeko.hychatter.config.data.providers.LanguageData
import tomeko.hychatter.utils.ChatUtils
import tomeko.hychatter.utils.Constants
import java.util.Locale

object SilentRemoval {
    val removalQueue = mutableSetOf<String>()

    fun register() {
        ClientReceiveMessageEvents.GAME.register(::onGameReceive)
    }

    private fun onGameReceive(message: Component, fromActionBar: Boolean) {
        if (fromActionBar) return
        val text =
            //? if 1.8.9 {
            //message.unformattedText
            //?} else {
            message.string
            //?}
        val match = LanguageData.PLAYER_CONNECTION_STATUS.find(text) ?: return
        val status = match.groups["status"]?.value ?: return
        if (status != "left") return

        val player = match.groups["player"]?.value ?: return
        if (removalQueue.contains(player.lowercase(Locale.ROOT))) {
            ChatUtils.queueMessage("/f remove $player")
            Notifications.success(Constants.MOD_NAME, "Silently removed $player from your friends list.")
            removalQueue.remove(player)
        }
    }
}
