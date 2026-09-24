package tomeko.hychatter.automatic

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
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.LanguageData
import tomeko.hychatter.utils.ChatUtils
import tomeko.hychatter.utils.Constants
import kotlin.text.get

object AutoFriend {
    fun register() {
        ClientReceiveMessageEvents.GAME.register(::onGameReceive)
    }

    private fun onGameReceive(message: Component, fromActionBar: Boolean) {
        if (fromActionBar || !HyChatterConfig.autoFriend) return

        val text =
            //? if 1.8.9 {
            //message.unformattedText
            //?} else {
            message.string
            //?}
        if (text.contains(": ")) return

        val match = LanguageData.FRIEND_REQUEST.find(text) ?: return
        var player = match.groups["player"]?.value ?: return
        if (player.startsWith("[")) player = player.substringAfter("] ")

        ChatUtils.queueMessage("/friend $player")
        Notifications.info(Constants.MOD_NAME, "Automatically added $player to your friends list.")
    }
}
