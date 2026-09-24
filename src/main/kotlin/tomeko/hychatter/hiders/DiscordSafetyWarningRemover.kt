package tomeko.hychatter.hiders

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
import tomeko.hychatter.utils.HypixelPackets

object DiscordSafetyWarningRemover {
    fun register() {
        ClientReceiveMessageEvents.MODIFY_GAME.register(::onGameMessage)
    }

    private fun onGameMessage(component: Component, fromActionBar: Boolean): Component {
        if (fromActionBar || !HyChatterConfig.removeDiscordSafetyWarning || !HypixelPackets.onHypixel) return component

        val message =
            //? if 1.8.9
            //component.unformattedText
            //? else
            component.string
        if (!message.contains(LanguageData.DISCORD_SAFETY_WARNING)) return component

        val copy =
            //? 1.8.9
            //component.createCopy()
            //? else
            component.copy()
        copy.siblings.removeIf {
            //? 1.8.9
            //it.unformattedText == LanguageData.DISCORD_SAFETY_WARNING
            //? else
            it.string == LanguageData.DISCORD_SAFETY_WARNING
        }
        while (copy.siblings.isNotEmpty() &&
            //? if 1.8.9
            //copy.siblings.last().unformattedText.isBlank()
            //? else
            copy.siblings.last().string.isBlank()
        ) {
            copy.siblings.removeLast()
        }
        return copy
    }
}
