package tomeko.hychatter.chat

//? if ornithe {
//import net.minecraft.util.IChatComponent as Component
//?} else {
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
import net.minecraft.network.chat.Component
//?}
import tomeko.hychatter.config.HyChatterConfig
//? if ornithe {
//import tomeko.hychatter.event.ClientReceiveMessageEvents
//?}
import tomeko.hychatter.utils.removeFormatting

object HideGuildMOTD {
    private var guildMOTD = false

    fun register() {
        ClientReceiveMessageEvents.ALLOW_GAME.register(::onChatReceive)
    }

    private fun onChatReceive(message: Component, fromActionBar: Boolean): Boolean {
        return fromActionBar || !shouldCancel(
            //? if ornithe {
            //message.unformattedText.removeFormatting()
            //?} else {
            message.string.removeFormatting()
            //?}
        )
    }

    private fun shouldCancel(message: String): Boolean {
        if (!HyChatterConfig.hideGuildMOTDEnabled) return false

        if (message.startsWith("--------------  Guild: Message Of The Day  --------------")) {
            guildMOTD = true
        }

        if (guildMOTD) {
            if (message.endsWith("-----------------------------------------------------")) {
                guildMOTD = false
            }
            return true
        }

        return false
    }
}