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
import net.minecraft.util.EnumChatFormatting
import net.minecraft.util.IChatComponent
import tomeko.hychat.config.HyChatterConfig
import tomeko.hychat.config.chat.handlers.ChatReceiveModule
import tomeko.hychat.config.data.providers.LanguageData
import tomeko.hychat.config.events.ChatReceiveEvent

object ColoredPlayerConnectionStatus : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        val match = LanguageData.PLAYER_CONNECTION_STATUS.find(event.plainMessage) ?: return
        val status = match.groups["status"]?.value ?: return
        val isJoin = status == "joined"

        event.message = event.message.plainCopy().withStyle(event.message.style)
            .append(event.message.siblings.first())
            .append(LegacyComponents.literal(status).withStyle(if (isJoin) EnumChatFormatting.GREEN else EnumChatFormatting.RED))
            .append(LegacyComponents.literal(".").withStyle(EnumChatFormatting.YELLOW))
    }

    override val isEnabled
        get() = HyChatterConfig.coloredStatuses
}
*///?} else {
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.chat.handlers.ChatReceiveModule
import tomeko.hychatter.config.data.providers.LanguageData
import tomeko.hychatter.config.events.ChatReceiveEvent

object ColoredPlayerConnectionStatus : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        val match = LanguageData.PLAYER_CONNECTION_STATUS.find(event.plainMessage) ?: return
        val status = match.groups["status"]?.value ?: return
        val isJoin = status == "joined"

        event.message = event.message.plainCopy().withStyle(event.message.style)
            .append(event.message.siblings.first())
            .append(Component.literal(status).withStyle(if (isJoin) ChatFormatting.GREEN else ChatFormatting.RED))
            .append(Component.literal(".").withStyle(ChatFormatting.YELLOW))
    }

    override val isEnabled
        get() = HyChatterConfig.coloredStatuses
}
//?}