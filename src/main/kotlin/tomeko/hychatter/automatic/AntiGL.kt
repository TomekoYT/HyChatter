package tomeko.hychatter.automatic

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

object AntiGL {
    fun register() {
        ClientReceiveMessageEvents.ALLOW_GAME.register(::onGameReceive)
    }

    private fun onGameReceive(component: Component, fromActionBar: Boolean): Boolean {
        if (fromActionBar || !HyChatterConfig.antiGL) return true

        val message =
            //? if ornithe {
            //component.unformattedText
            //?} else {
            component.string
            //?}

        if (!HypixelUtils.getLocation().inGame()
            || (message.startsWith("-") && message.endsWith("-"))
            || (message.startsWith("▬") && message.endsWith("▬"))
            || (message.startsWith("≡") && message.endsWith("≡"))
            || !message.contains(": ")
            || message.contains(mc.user.name, true)
        ) return true
        if (message.contains(LanguageData.GL_MESSAGES)) {
            return false
        }

        return true
    }
}
