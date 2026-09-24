package tomeko.hychatter.chat

//? if 1.8.9 {
/*import tomeko.hychatter.utils.string
import tomeko.hychatter.utils.siblings
import tomeko.hychatter.utils.copy
import net.minecraft.util.IChatComponent as Component
*///?} else {
import net.minecraft.network.chat.Component
//?}
//? if ornithe {
//import tomeko.hychatter.event.ClientReceiveMessageEvents
//?} else {
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
//?}
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.LanguageData

object DiscordSafetyWarningRemover {
    fun register() {
        ClientReceiveMessageEvents.MODIFY_GAME.register(::onGameMessage)
    }

    private fun onGameMessage(message: Component, fromActionBar: Boolean): Component {
        if (fromActionBar || !HyChatterConfig.removeDiscordSafetyWarning) return message
        if (!message.string.contains(LanguageData.DISCORD_SAFETY_WARNING)) return message

        val copy = message.copy()
        copy.siblings.removeIf { it.string == LanguageData.DISCORD_SAFETY_WARNING }
        while (copy.siblings.isNotEmpty() && copy.siblings.last().string.isBlank()) {
            copy.siblings.removeLast()
        }
        return copy
    }
}
