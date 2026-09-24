package tomeko.hychatter.hiders

//? if ornithe {
//import net.minecraft.util.IChatComponent as Component
//?} else {
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
import net.minecraft.network.chat.Component
//?}
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.utils.HypixelPackets
//? if ornithe {
//import tomeko.hychatter.event.ClientReceiveMessageEvents
//?}
import tomeko.hychatter.utils.removeFormatting

object HideGuildMOTD {
    private var guildMOTD = false

    fun register() {
        ClientReceiveMessageEvents.ALLOW_GAME.register(::allowGuildMOTD)
    }

    private fun allowGuildMOTD(component: Component, fromActionBar: Boolean): Boolean {
        if (fromActionBar || !HyChatterConfig.hideGuildMOTDEnabled || !HypixelPackets.onHypixel) return true

        val message =
            //? if ornithe {
            //component.unformattedText.removeFormatting()
        //?} else {
        component.string.removeFormatting()
        //?}

        if (message.startsWith("--------------  Guild: Message Of The Day  --------------")) {
            guildMOTD = true
        }

        if (guildMOTD) {
            if (message.endsWith("-----------------------------------------------------")) {
                guildMOTD = false
            }
            return false
        }

        return true
    }
}