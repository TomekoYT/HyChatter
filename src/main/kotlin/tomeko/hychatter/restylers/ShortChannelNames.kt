package tomeko.hychatter.restylers

//? if 1.8.9 {
/*import net.minecraft.util.ChatComponentText
import net.minecraft.util.IChatComponent as Component
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
import tomeko.hychatter.config.LanguageData
import tomeko.hychatter.utils.HypixelPackets

object ShortChannelNames {
    fun register() {
        ClientReceiveMessageEvents.MODIFY_GAME.register(::onGameMessage)
    }

    private fun onGameMessage(message: Component, fromActionBar: Boolean): Component {
        if (fromActionBar || !HyChatterConfig.shortChannelNames || !HypixelPackets.onHypixel) return message

        var result = message
        result = restyle(result, LanguageData.PARTY_CHANNEL, "§9P")
        result = restyle(result, LanguageData.GUILD_CHANNEL, "§2G")
        result = restyle(result, LanguageData.FRIEND_CHANNEL, "§aF")
        result = restyle(result, LanguageData.OFFICER_CHANNEL, "§3O")
        return result
    }

    private fun restyle(message: Component, regex: Regex, prefix: String): Component {
        if (!regex.containsMatchIn(
                //? if 1.8.9
                //message.unformattedText
                        //? else
                message.string
        )) return message

        //? if 1.8.9 {
        /*if ((message as? ChatComponentText)
                ?.chatComponentText_TextValue
                .isNullOrEmpty()
        ) {
            return ChatComponentText("").also { result ->
                for (sibling in message.siblings) {
                    val modifiedSibling = regex.find(sibling.unformattedText)?.let {
                        ChatComponentText("$prefix > ${it.groupValues[1]}")
                    } ?: sibling

                    result.appendSibling(modifiedSibling)
                }
            }
        }

        return ChatComponentText("$prefix > ").also { result ->
            message.siblings.forEach { result.appendSibling(it) }
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
