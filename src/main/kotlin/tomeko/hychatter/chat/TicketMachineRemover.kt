package tomeko.hychatter.chat

//? if 1.8.9 {
//import net.minecraft.util.IChatComponent as Component
//?} else {
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
import net.minecraft.network.chat.Component
//?}
//? if ornithe {
//import tomeko.hychatter.event.ClientReceiveMessageEvents
//?}
import net.hypixel.data.type.GameType
import org.polyfrost.oneconfig.api.hypixel.v1.HypixelUtils
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.data.providers.LanguageData

object TicketMachineRemover {
    fun register() {
        ClientReceiveMessageEvents.ALLOW_GAME.register(::onGameReceive)
    }

    private fun onGameReceive(component: Component, fromActionBar: Boolean): Boolean {
        if (fromActionBar || !HyChatterConfig.removeTicketMachineAnnouncements) return true

        val message =
            //? if ornithe {
            //component.unformattedText
            //?} else {
            component.string
            //?}
        val location = HypixelUtils.getLocation()
        if (location.gameType.orElse(null) == GameType.BEDWARS
            && !location.inGame()
            && LanguageData.TICKET_ANNOUNCER.matches(message)
        ) {
            return false
        }

        return true
    }
}
