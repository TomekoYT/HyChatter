package tomeko.hychatter.automatic

//? if 1.8.9 {
/*import net.minecraft.util.IChatComponent as Component
import tomeko.hychatter.event.ClientReceiveMessageEvents
*///?} else {
import net.minecraft.network.chat.Component
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
//?}
import net.minecraft.client.Minecraft
import org.polyfrost.oneconfig.api.notifications.v1.Notifications
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.LanguageData
import tomeko.hychatter.utils.Constants
import tomeko.hychatter.utils.HypixelPackets
import kotlin.text.get

object AutoFriend {
    fun register() {
        ClientReceiveMessageEvents.GAME.register(::onGameReceive)
    }

    private fun onGameReceive(message: Component, fromActionBar: Boolean) {
        if (fromActionBar || !HyChatterConfig.autoFriend || !HypixelPackets.onHypixel) return

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

        //? if 1.8.9
        //Minecraft.getMinecraft().thePlayer?.sendChatMessage("/friend $player") ?: return
        //? else
        Minecraft.getInstance().player?.connection?.sendCommand("friend $player") ?: return
        Notifications.info(Constants.MOD_NAME, "Automatically added $player to your friends list.")
    }
}
