package tomeko.hychatter.chat

//? if 1.8.9 {
/*import net.minecraft.util.ChatComponentText
import net.minecraft.util.IChatComponent as Component
import tomeko.hychatter.utils.LegacyComponents
import tomeko.hychatter.utils.append
import tomeko.hychatter.utils.siblings
import tomeko.hychatter.utils.string
*///?} else {
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.contents.PlainTextContents
//?}
//? if ornithe {
//import tomeko.hychatter.event.ClientReceiveMessageEvents
//?} else {
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
//?}
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.data.providers.LanguageData

object ShortChannelNames {
    fun register() {
        ClientReceiveMessageEvents.MODIFY_GAME.register(::onGameMessage)
    }

    private fun onGameMessage(message: Component, fromActionBar: Boolean): Component {
        if (fromActionBar || !HyChatterConfig.shortChannelNames) return message

        var result = message
        result = restyle(result, LanguageData.PARTY_CHANNEL, "§9P")
        result = restyle(result, LanguageData.GUILD_CHANNEL, "§2G")
        result = restyle(result, LanguageData.FRIEND_CHANNEL, "§aF")
        result = restyle(result, LanguageData.OFFICER_CHANNEL, "§3O")
        return result
    }

    private fun restyle(message: Component, regex: Regex, prefix: String): Component {
        if (!regex.containsMatchIn(message.string)) return message

        //? if 1.8.9 {
        /*if ((message as? ChatComponentText)?.getChatComponentText_TextValue().isNullOrEmpty()) {
            return LegacyComponents.empty().also { result ->
                for (sibling in message.siblings) {
                    val modifiedSibling = regex.find(sibling.string)?.let {
                        LegacyComponents.literal("$prefix > ${it.groupValues[1]}")
                    } ?: sibling
                    result.append(modifiedSibling)
                }
            }
        }

        return LegacyComponents.literal("$prefix > ").also { result ->
            message.siblings.forEach { result.append(it) }
        }
        *///?} else {
        if ((message.contents as? PlainTextContents)?.text().isNullOrEmpty()) {
            return Component.empty().also { result ->
                for (sibling in message.siblings) {
                    val modifiedSibling = regex.find(sibling.string)?.let {
                        Component.literal("$prefix > ${it.groupValues[1]}")
                    } ?: sibling
                    result.append(modifiedSibling)
                }
            }
        }

        return Component.literal("$prefix > ").also { result ->
            message.siblings.forEach { result.append(it) }
        }
        //?}
    }
}
