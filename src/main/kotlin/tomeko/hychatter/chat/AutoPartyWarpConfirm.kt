package tomeko.hychatter.chat

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
import tomeko.hychatter.config.data.providers.LanguageData
import tomeko.hychatter.utils.ChatUtils
import java.util.concurrent.TimeUnit

object AutoPartyWarpConfirm {
    fun register() {
        ClientReceiveMessageEvents.ALLOW_GAME.register(::onGameReceive)
    }

    private fun onGameReceive(message: Component, fromActionBar: Boolean): Boolean {
        if (fromActionBar || !HyChatterConfig.autoPartyWarpConfirm) return true

        val text =
            //? if 1.8.9 {
            //message.unformattedText
            //?} else {
            message.string
            //?}
        if (text != LanguageData.PARTY_CONFIRM_WARP) return true

        Multithreading.schedule({ ChatUtils.queueMessage("/p warp") }, 1500L, TimeUnit.MILLISECONDS)
        return false
    }
}
