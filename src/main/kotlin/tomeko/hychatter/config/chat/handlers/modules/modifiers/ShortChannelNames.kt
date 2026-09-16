package tomeko.hychatter.config.chat.handlers.modules.modifiers

//? if 1.8.9 {
/*import tomeko.hychat.utils.LegacyComponents
import tomeko.hychat.utils.string
import tomeko.hychat.utils.siblings
import tomeko.hychat.utils.style
import tomeko.hychat.utils.plainCopy
import tomeko.hychat.utils.withStyle
import tomeko.hychat.utils.append
import tomeko.hychat.utils.copy
import net.minecraft.util.IChatComponent
import net.minecraft.util.ChatComponentText
import tomeko.hychat.config.HyChatterConfig
import tomeko.hychat.config.chat.handlers.ChatReceiveModule
import tomeko.hychat.config.data.providers.LanguageData
import tomeko.hychat.config.events.ChatReceiveEvent

object ShortChannelNames : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        restyle(event, LanguageData.PARTY_CHANNEL, "§9P")
        restyle(event, LanguageData.GUILD_CHANNEL, "§2G")
        restyle(event, LanguageData.FRIEND_CHANNEL, "§aF")
        restyle(event, LanguageData.OFFICER_CHANNEL, "§3O")
    }

    override val isEnabled
        get() = HyChatterConfig.shortChannelNames
    override val priority = 3

    private fun restyle(event: ChatReceiveEvent, regex: Regex, prefix: String) {
        if (!regex.containsMatchIn(event.plainMessage)) return

        var message: IChatComponent
        if ((event.message as? ChatComponentText)?.getChatComponentText_TextValue().isNullOrEmpty()) {
            message = LegacyComponents.empty()
            for (sibling in event.message.siblings) {
                val modifiedSibling = regex.find(sibling.string)?.let {
                    LegacyComponents.literal("$prefix > ${it.groupValues[1]}")
                } ?: sibling

                message.append(modifiedSibling)
            }
        } else {
            message = LegacyComponents.literal("$prefix > ")
            event.message.siblings.forEach { message.append(it) }
        }

        event.message = message
    }
}
*///?} else {
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.contents.PlainTextContents
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.chat.handlers.ChatReceiveModule
import tomeko.hychatter.config.data.providers.LanguageData
import tomeko.hychatter.config.events.ChatReceiveEvent

object ShortChannelNames : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        restyle(event, LanguageData.PARTY_CHANNEL, "§9P")
        restyle(event, LanguageData.GUILD_CHANNEL, "§2G")
        restyle(event, LanguageData.FRIEND_CHANNEL, "§aF")
        restyle(event, LanguageData.OFFICER_CHANNEL, "§3O")
    }

    override val isEnabled
        get() = HyChatterConfig.shortChannelNames
    override val priority = 3

    private fun restyle(event: ChatReceiveEvent, regex: Regex, prefix: String) {
        if (!regex.containsMatchIn(event.plainMessage)) return

        var message: Component
        if ((event.message.contents as? PlainTextContents)?.text().isNullOrEmpty()) {
            message = Component.empty()
            for (sibling in event.message.siblings) {
                val modifiedSibling = regex.find(sibling.string)?.let {
                    Component.literal("$prefix > ${it.groupValues[1]}")
                } ?: sibling

                message.append(modifiedSibling)
            }
        } else {
            message = Component.literal("$prefix > ")
            event.message.siblings.forEach { message.append(it) }
        }

        event.message = message
    }
}
//?}