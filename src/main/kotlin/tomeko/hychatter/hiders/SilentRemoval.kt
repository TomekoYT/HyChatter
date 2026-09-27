package tomeko.hychatter.hiders

//? if forge
//import cc.polyfrost.oneconfig.utils.Notifications
//? if 1.8.9 {
//import net.minecraft.util.IChatComponent as Component
//?} else {
import net.minecraft.network.chat.Component
//?}
//? if 1.8.9 {
//import tomeko.hychatter.event.ClientReceiveMessageEvents
//?} else {
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
//?}
//? if !forge
import org.polyfrost.oneconfig.api.notifications.v1.Notifications
import net.minecraft.client.Minecraft
import tomeko.hychatter.config.LanguageData
import tomeko.hychatter.utils.Constants
import tomeko.hychatter.utils.HypixelPackets
import java.util.Locale

object SilentRemoval {
    val removalQueue = mutableSetOf<String>()

    fun register() {
        ClientReceiveMessageEvents.GAME.register(::onGameReceive)
    }

    private fun onGameReceive(message: Component, fromActionBar: Boolean) {
        if (fromActionBar || !HypixelPackets.onHypixel) return
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
            //? if 1.8.9
            //Minecraft.getMinecraft().thePlayer?.sendChatMessage("/f remove $player") ?: return
            //? else
            Minecraft.getInstance().player?.connection?.sendCommand("f remove $player") ?: return

            //? if forge
            //Notifications.INSTANCE.send(
            //? else
            Notifications.success(
                Constants.MOD_NAME, "Silently removed $player from your friends list."
            )
            removalQueue.remove(player)
        }
    }
}
