package tomeko.hychatter.restylers

//? if 1.8.9 {
/*import net.minecraft.util.ChatComponentText
import net.minecraft.util.ChatStyle
import net.minecraft.util.EnumChatFormatting
import net.minecraft.util.IChatComponent as Component
*///?} else {
import net.minecraft.ChatFormatting
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
import kotlin.text.get

object ColoredPlayerConnectionStatus {
    fun register() {
        ClientReceiveMessageEvents.MODIFY_GAME.register(::onGameMessage)
    }

    private fun onGameMessage(component: Component, fromActionBar: Boolean): Component {
        if (fromActionBar || !HyChatterConfig.coloredStatuses || !HypixelPackets.onHypixel) return component

        val match = LanguageData.PLAYER_CONNECTION_STATUS.find(
            //? 1.8.9
            //component.unformattedText
            //? else
            component.string
        ) ?: return component
        val status = match.groups["status"]?.value ?: return component
        val isJoin = status == "joined"

        return restyle(component, status, isJoin)
    }

    private fun restyle(component: Component, status: String, isJoin: Boolean): Component {
        //? if 1.8.9 {
        /*val result = ChatComponentText("").setChatStyle(component.chatStyle.createShallowCopy())
        val text = component.unformattedTextForChat
        if (text.isNotEmpty()) {
            val index = text.lastIndexOf(status)

            if (index >= 0) {
                result.appendSibling(
                    ChatComponentText(text.substring(0, index))
                        .setChatStyle(component.chatStyle.createShallowCopy())
                )

                result.appendSibling(
                    ChatComponentText(status)
                        .setChatStyle(
                            component.chatStyle.createShallowCopy()
                                .setColor(
                                    if (isJoin) EnumChatFormatting.GREEN
                                    else EnumChatFormatting.RED
                                )
                        )
                )

                result.appendSibling(
                    ChatComponentText(text.substring(index + status.length))
                        .setChatStyle(component.chatStyle.createShallowCopy())
                )
            } else {
                result.appendSibling(
                    ChatComponentText(text)
                        .setChatStyle(component.chatStyle.createShallowCopy())
                )
            }
        }

        for (sibling in component.siblings) {
            result.appendSibling(restyle(sibling, status, isJoin))
        }
        return result
        *///?} else {
        return component.plainCopy().withStyle(component.style)
        .append(component.siblings.first())
        .append(Component.literal(status).withStyle(if (isJoin) ChatFormatting.GREEN else ChatFormatting.RED))
        .append(Component.literal(".").withStyle(ChatFormatting.YELLOW))
        //?}
    }
}
