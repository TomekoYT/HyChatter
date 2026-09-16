package tomeko.hychat.config.chat.handlers.modules.modifiers

//? if 1.8.9 {
/*import tomeko.hychat.utils.LegacyComponents
import tomeko.hychat.utils.string
import tomeko.hychat.utils.siblings
import tomeko.hychat.utils.style
import tomeko.hychat.utils.plainCopy
import tomeko.hychat.utils.withStyle
import tomeko.hychat.utils.append
import tomeko.hychat.utils.copy
import net.minecraft.util.EnumChatFormatting
import net.minecraft.util.IChatComponent
import net.minecraft.util.ChatComponentText
import tomeko.hychat.config.HyChatConfig
import tomeko.hychat.config.chat.handlers.ChatReceiveModule
import tomeko.hychat.config.data.providers.LanguageData
import tomeko.hychat.config.events.ChatReceiveEvent

object ShortPMChannelNames : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        val type = LanguageData.PRIVATE_MESSAGE.matchEntire(event.plainMessage)
            ?.groups?.get("type")?.value ?: return
        val isOutgoing = type == "To"
        val channelName = "$type "

        val rootText = (event.message as? ChatComponentText)?.getChatComponentText_TextValue() ?: return
        val parts = buildList {
            if (rootText.isNotEmpty()) add(LegacyComponents.literal(rootText).withStyle(event.message.style))
            addAll(event.message.siblings)
        }

        val message = LegacyComponents.empty().withStyle(event.message.style).append(
            LegacyComponents.literal("PM ${if (isOutgoing) ">" else "<"} ")
                .withStyle(if (isOutgoing) EnumChatFormatting.LIGHT_PURPLE else EnumChatFormatting.DARK_PURPLE)
        )

        var shortened = false
        for (part in parts) {
            if (!shortened && part.siblings.isEmpty() && part.string.startsWith(channelName)) {
                shortened = true

                val remainder = part.string.removePrefix(channelName)
                if (remainder.isNotEmpty()) message.append(LegacyComponents.literal(remainder).withStyle(part.style))
            } else {
                message.append(part)
            }
        }

        event.message = message
    }

    override val isEnabled
        get() = HyChatConfig.shortPMChannelNames
    override val priority = 3
}
*///?} else {
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.contents.PlainTextContents
import tomeko.hychat.config.HyChatConfig
import tomeko.hychat.config.chat.handlers.ChatReceiveModule
import tomeko.hychat.config.data.providers.LanguageData
import tomeko.hychat.config.events.ChatReceiveEvent

object ShortPMChannelNames : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        val type = LanguageData.PRIVATE_MESSAGE.matchEntire(event.plainMessage)
            ?.groups?.get("type")?.value ?: return
        val isOutgoing = type == "To"
        val channelName = "$type "

        val rootText = (event.message.contents as? PlainTextContents)?.text() ?: return
        val parts = buildList {
            if (rootText.isNotEmpty()) add(Component.literal(rootText).withStyle(event.message.style))
            addAll(event.message.siblings)
        }

        val message = Component.empty().withStyle(event.message.style).append(
            Component.literal("PM ${if (isOutgoing) ">" else "<"} ")
                .withStyle(if (isOutgoing) ChatFormatting.LIGHT_PURPLE else ChatFormatting.DARK_PURPLE)
        )

        var shortened = false
        for (part in parts) {
            if (!shortened && part.siblings.isEmpty() && part.string.startsWith(channelName)) {
                shortened = true

                val remainder = part.string.removePrefix(channelName)
                if (remainder.isNotEmpty()) message.append(Component.literal(remainder).withStyle(part.style))
            } else {
                message.append(part)
            }
        }

        event.message = message
    }

    override val isEnabled
        get() = HyChatConfig.shortPMChannelNames
    override val priority = 3
}
//?}