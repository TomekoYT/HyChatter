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
import org.polyfrost.oneconfig.utils.v1.dsl.mc
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.LanguageData

object BedwarsAdvertisementsRemover {
    fun register() {
        //? if ornithe {
        //ClientReceiveMessageEvents.ALLOW_CHAT.register(::onChatReceive)
        //?} else {
        ClientReceiveMessageEvents.ALLOW_CHAT.register { component, _, _, _, _ -> onChatReceive(component) }
        //?}
    }

    private fun onChatReceive(component: Component): Boolean {
        if (!HyChatterConfig.removePlayerBedwarsAds) return true

        val message =
            //? if ornithe {
            //component.unformattedText
            //?} else {
            component.string
            //?}

        if ((message.startsWith("-") && message.endsWith("-"))
            || (message.startsWith("▬") && message.endsWith("▬"))
            || (message.startsWith("≡") && message.endsWith("≡"))
            || !message.contains(": ")
            || message.contains(mc.user.name, true)
        ) return true
        val location = HypixelUtils.getLocation()
        if (location.gameType.orElse(null) == GameType.BEDWARS
            && !location.inGame()
            && message.contains(LanguageData.CHAT_BEDWARS_ADVERTISEMENT)
        ) {
            return false
        }

        return true
    }
}
