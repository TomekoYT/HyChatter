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

object ThankWatchdog {
    fun register() {
        ClientReceiveMessageEvents.GAME.register(::onGameReceive)
    }

    private fun onGameReceive(message: Component, fromActionBar: Boolean) {
        if (fromActionBar || !HyChatterConfig.thankWatchdog || !HypixelPackets.onHypixel) return
        val text =
            //? if 1.8.9 {
            //message.unformattedText
            //?} else {
            message.string
            //?}
        if (text == LanguageData.WATCHDOG_ANNOUNCEMENT || text.startsWith(LanguageData.WATCHDOG_BAN)) {
            //? if 1.8.9
            //Minecraft.getMinecraft().thePlayer?.sendChatMessage("/ac Thanks Watchdog!") ?: return
            //? else
            Minecraft.getInstance().player?.connection?.sendCommand("ac Thanks Watchdog!") ?: return
        }
    }
}
