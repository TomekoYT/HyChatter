package tomeko.hychatter.automatic

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
import net.minecraft.client.Minecraft
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.LanguageData
import tomeko.hychatter.utils.HypixelPackets
import kotlin.text.get

object BroadcastLevelUp {
    fun register() {
        ClientReceiveMessageEvents.GAME.register(::onGameReceive)
    }

    private fun onGameReceive(message: Component, fromActionBar: Boolean) {
        if (fromActionBar || !HyChatterConfig.broadcastLevelUp || !HypixelPackets.onHypixel) return
        val text =
            //? if 1.8.9 {
            //message.unformattedText
            //?} else {
            message.string
            //?}
        if (text.contains(": ")) return

        LanguageData.LEVEL_UP.find(text)?.let { match ->
            val level = match.groups["level"]?.value ?: return@let
            //? if 1.8.9
            //Minecraft.getMinecraft().thePlayer?.sendChatMessage("/gc Level up! I am now Hypixel Level $level!")
            //? else
            Minecraft.getInstance().player?.connection?.sendCommand("gc Level up! I am now Hypixel Level $level!")
        }
    }
}
