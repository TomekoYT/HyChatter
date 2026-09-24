package tomeko.hychatter.chat

//? if 1.8.9 {
/*import net.minecraft.util.ChatComponentText
import net.minecraft.util.EnumChatFormatting
import net.minecraft.util.IChatComponent as Component
import tomeko.hychatter.utils.LegacyComponents
import tomeko.hychatter.utils.append
import tomeko.hychatter.utils.siblings
import tomeko.hychatter.utils.style
import tomeko.hychatter.utils.string
import tomeko.hychatter.utils.withStyle
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
import tomeko.hychatter.config.data.providers.LanguageData

object ShortPMChannelNames {
    fun register() {
        ClientReceiveMessageEvents.MODIFY_GAME.register(::onGameMessage)
    }

    private fun onGameMessage(message: Component, fromActionBar: Boolean): Component {
        if (fromActionBar || !HyChatterConfig.shortPMChannelNames) return message

        val type = LanguageData.PRIVATE_MESSAGE.matchEntire(message.string)
            ?.groups?.get("type")?.value ?: return message
        val isOutgoing = type == "To"
        val channelName = "$type "

        //? if 1.8.9 {
        /*val rootText = (message as? ChatComponentText)?.getChatComponentText_TextValue() ?: return message
        val parts = buildList {
            if (rootText.isNotEmpty()) add(LegacyComponents.literal(rootText).withStyle(message.style))
            addAll(message.siblings)
        }

        val result = LegacyComponents.empty().withStyle(message.style).append(
            LegacyComponents.literal("PM ${if (isOutgoing) ">" else "<"} ")
                .withStyle(if (isOutgoing) EnumChatFormatting.LIGHT_PURPLE else EnumChatFormatting.DARK_PURPLE)
        )

        var shortened = false
        for (part in parts) {
            if (!shortened && part.siblings.isEmpty() && part.string.startsWith(channelName)) {
                shortened = true
                val remainder = part.string.removePrefix(channelName)
                if (remainder.isNotEmpty()) result.append(LegacyComponents.literal(remainder).withStyle(part.style))
            } else {
                result.append(part)
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
