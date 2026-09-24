package tomeko.hychatter.restylers

//? if 1.8.9 {
/*import net.minecraft.util.ChatComponentText
import net.minecraft.util.EnumChatFormatting
import net.minecraft.util.IChatComponent as Component
*///?} else {
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
//?}

import tomeko.hychatter.config.HyChatterConfig
//? if ornithe {
//import tomeko.hychatter.event.ClientReceiveMessageEvents
//?}
import tomeko.hychatter.utils.HypixelPackets
import tomeko.hychatter.utils.removeFormatting

object WhiteChatMessages {
    fun register() {
        ClientReceiveMessageEvents.MODIFY_GAME.register(::onChatReceive)
    }

    private fun onChatReceive(message: Component, fromActionBar: Boolean): Component {
        if (fromActionBar || !HypixelPackets.onHypixel) return message

        val unformattedMessage =
            //? if 1.8.9
            //message.unformattedText.removeFormatting()
            //? else
            message.string.removeFormatting()

        if (HyChatterConfig.whitePrivateMessagesEnabled
            && HypixelPackets.onHypixel
            && (unformattedMessage.startsWith("From ")
                    || unformattedMessage.startsWith("To ")
                    || unformattedMessage.startsWith("PM"))
        ) {
            val n = message.siblings.size
            if (n < 1) return message

            val newMessage =
                //? if 1.8.9
                //ChatComponentText(if (unformattedMessage.startsWith("From ")) "From " else if (unformattedMessage.startsWith("To ")) "To " else "PM > ")
                //? else
                message.plainCopy().withStyle(ChatFormatting.LIGHT_PURPLE)

            //? if 1.8.9
            //newMessage.chatStyle.color = EnumChatFormatting.LIGHT_PURPLE

            var colonPassed = false
            for (i in 0 until n - 1) {
                val siblingComponent = message.siblings[i]
                val sibling =
                    //? if 1.8.9
                    //siblingComponent.unformattedText
                    //? else
                    siblingComponent.string

                if (!colonPassed && sibling.contains(":")) {
                    val colonIndex = sibling.indexOf(":")

                    if (colonIndex > 0) {
                        //? if 1.8.9 {
                        /*val beforeColon =
                            ChatComponentText(sibling.substring(0, colonIndex))
                        beforeColon.chatStyle =
                            siblingComponent.chatStyle.createShallowCopy()
                        newMessage.appendSibling(beforeColon)
                        *///?} else {
                        newMessage.append(
                            Component.literal(
                                sibling.substring(0, colonIndex)
                            ).setStyle(siblingComponent.style)
                        )
                        //?}
                    }

                    //? if 1.8.9 {
                    /*val colon = ChatComponentText(":")
                    colon.chatStyle.color = EnumChatFormatting.WHITE
                    newMessage.appendSibling(colon)
                    *///?} else {
                    newMessage.append(
                        Component.literal(":")
                            .withStyle(ChatFormatting.WHITE)
                    )
                    //?}

                    if (colonIndex + 1 <= sibling.length - 1) {
                        //? if 1.8.9 {
                        /*val afterColon =
                            ChatComponentText(sibling.substring(colonIndex + 1))
                        afterColon.chatStyle =
                            siblingComponent.chatStyle.createShallowCopy()
                        newMessage.appendSibling(afterColon)
                        *///?} else {
                        newMessage.append(
                            Component.literal(
                                sibling.substring(colonIndex + 1)
                            ).setStyle(siblingComponent.style)
                        )
                        //?}
                    }

                    colonPassed = true
                    continue
                }

                //? if 1.8.9
                //newMessage.appendSibling(siblingComponent.createCopy())
                //? else
                newMessage.append(siblingComponent)
            }

            if (!colonPassed) return message

            //? if 1.8.9 {
            /*val lastSibling = message.siblings[n - 1].createCopy()
            if (lastSibling.chatStyle.color == EnumChatFormatting.GRAY)
                lastSibling.chatStyle.color = EnumChatFormatting.WHITE
            newMessage.appendSibling(lastSibling)
            *///?} else {
            val actualMessage = message.siblings[n - 1].copy()
            newMessage.append(
                if (actualMessage.style.color != null && actualMessage.style.color?.value == 0xAAAAAA)
                    actualMessage.withStyle(ChatFormatting.WHITE)
                else
                    actualMessage
            )
            //?}

            return newMessage
        }

        //? if 1.8.9 {
        /*val formattedMessage = message.formattedText

        if (HyChatterConfig.whiteNoRankMessagesEnabled
            && HypixelPackets.onHypixel
            && formattedMessage.contains("§7: ")
        ) {
            return ChatComponentText(
                formattedMessage.replace("§7: ", "§f: ")
            )
        }
        *///?} else {
        val formattedMessage = message.string

        if (HyChatterConfig.whiteNoRankMessagesEnabled
            && HypixelPackets.onHypixel
            && formattedMessage.contains("§7: ")
        ) return Component.nullToEmpty(formattedMessage.replace("§7: ", "§f: "))
        //?}

        return message
    }
}