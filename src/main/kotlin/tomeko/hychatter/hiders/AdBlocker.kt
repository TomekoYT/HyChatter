package tomeko.hychatter.hiders

//? if 1.8.9 {
//import net.minecraft.util.IChatComponent as Component
//?} else {
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
import net.minecraft.network.chat.Component
//?}
//? if ornithe {
//import tomeko.hychatter.event.ClientReceiveMessageEvents
//?}
import org.polyfrost.oneconfig.api.hypixel.v1.HypixelUtils
import org.polyfrost.oneconfig.utils.v1.dsl.mc
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.LanguageData
import tomeko.hychatter.utils.HypixelPackets

object AdBlocker {
    fun register() {
        //? if ornithe {
        //ClientReceiveMessageEvents.ALLOW_CHAT.register(::onChatReceive)
        //?} else {
        ClientReceiveMessageEvents.ALLOW_CHAT.register { component, _, _, _, _ -> onChatReceive(component) }
        //?}
    }

    private fun onChatReceive(component: Component): Boolean {
        if (!HyChatterConfig.removePlayerAds || !HypixelPackets.onHypixel) return true

        val message =
        //? if ornithe {
        //component.unformattedText
            //?} else {
            component.string
        //?}

        return ((message.startsWith("-") && message.endsWith("-"))
                || (message.startsWith("▬") && message.endsWith("▬"))
                || (message.startsWith("≡") && message.endsWith("≡"))
                || !message.contains(": ")
                || message.contains(mc.user.name, true))
                || !(message.contains(LanguageData.CHAT_ADVERTISEMENTS)
                || (!HypixelUtils.getLocation().inGame() && message.contains(LanguageData.CHAT_RANK_BEGGING))
                )
    }
}
