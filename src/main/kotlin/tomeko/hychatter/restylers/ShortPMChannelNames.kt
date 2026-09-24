package tomeko.hychatter.restylers

//? if 1.8.9 {
/*import net.minecraft.util.ChatComponentText
import net.minecraft.util.ChatStyle
import net.minecraft.util.EnumChatFormatting
import net.minecraft.util.IChatComponent as Component
*///?} else {
import net.minecraft.ChatFormatting
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

object ShortPMChannelNames {
    fun register() {
        ClientReceiveMessageEvents.MODIFY_GAME.register(::onGameMessage)
    }

    private fun onGameMessage(message: Component, fromActionBar: Boolean): Component {
        if (fromActionBar || !HyChatterConfig.shortPMChannelNames || !HypixelPackets.onHypixel) return message

        val type = LanguageData.PRIVATE_MESSAGE.matchEntire(
            //? if 1.8.9
            //message.unformattedText
                    //? else
            message.string
        )?.groups?.get("type")?.value ?: return message
        val isOutgoing = type == "To"
        val channelName = "$type "

        //? if 1.8.9 {
        /*val rootText = (message as? ChatComponentText)?.chatComponentText_TextValue ?: return message
        val parts = buildList {
            if (rootText.isNotEmpty()) add(
                ChatComponentText(rootText).setChatStyle(message.chatStyle.createShallowCopy())
            )
            addAll(message.siblings)
        }

        val result = ChatComponentText("")
            .setChatStyle(message.chatStyle.createShallowCopy())
            .appendSibling(
                ChatComponentText("PM ${if (isOutgoing) ">" else "<"} ")
                    .setChatStyle(
                        ChatStyle().setColor(
                            if (isOutgoing) EnumChatFormatting.LIGHT_PURPLE
                            else EnumChatFormatting.DARK_PURPLE
                        )
                    )
            )

        var shortened = false
        for (part in parts) {
            if (!shortened && part.siblings.isEmpty() && part.unformattedText.startsWith(channelName)) {
                shortened = true
                val remainder = part.unformattedText.removePrefix(channelName)
                if (remainder.isNotEmpty()) result.appendSibling(ChatComponentText(remainder).setChatStyle(part.chatStyle.createShallowCopy()))
            } else {
                result.appendSibling(part)
            }
        }
        return result
        *///?} else {
        val rootText = (message.contents as? PlainTextContents)?.text() ?: return message
        val parts = buildList {
            if (rootText.isNotEmpty()) add(Component.literal(rootText).withStyle(message.style))
            addAll(message.siblings)
        }

        val result = Component.empty().withStyle(message.style).append(
            Component.literal("PM ${if (isOutgoing) ">" else "<"} ")
                .withStyle(if (isOutgoing) ChatFormatting.LIGHT_PURPLE else ChatFormatting.DARK_PURPLE)
        )

        var shortened = false
        for (part in parts) {
            if (!shortened && part.siblings.isEmpty() && part.string.startsWith(channelName)) {
                shortened = true
                val remainder = part.string.removePrefix(channelName)
                if (remainder.isNotEmpty()) result.append(Component.literal(remainder).withStyle(part.style))
            } else {
                result.append(part)
            }
        }
        return result
        //?}
    }
}
