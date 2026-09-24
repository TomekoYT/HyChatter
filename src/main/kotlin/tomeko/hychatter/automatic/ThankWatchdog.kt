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
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.LanguageData
import tomeko.hychatter.utils.ChatUtils

object ThankWatchdog {
    fun register() {
        ClientReceiveMessageEvents.GAME.register(::onGameReceive)
    }

    private fun onGameReceive(message: Component, fromActionBar: Boolean) {
        if (fromActionBar || !HyChatterConfig.thankWatchdog) return
        val text =
            //? if 1.8.9 {
            //message.unformattedText
            //?} else {
            message.string
            //?}
        if (text == LanguageData.WATCHDOG_ANNOUNCEMENT || text.startsWith(LanguageData.WATCHDOG_BAN)) {
            ChatUtils.queueMessage("/ac Thanks Watchdog!")
        }
    }
}
