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

object GameStatusRestyler {
    private var playerCount = -1
    private var maxPlayerCount = -1

    fun register() {
        ClientReceiveMessageEvents.MODIFY_GAME.register(::onGameMessage)
    }

    private fun onGameMessage(message: Component, fromActionBar: Boolean): Component {
        if (fromActionBar || !HypixelPackets.onHypixel) return message

        val plainMessage =
            //? if 1.8.9
            //message.unformattedText
            //? else
            message.string
        val joinMatch = LanguageData.GAME_JOIN.find(plainMessage)
        joinMatch?.let { match ->
            val amount = match.groups["amount"]?.value?.removeSurrounding("(", ")") ?: return@let
            val amounts = amount.split('/')
            if (amounts.size == 2) {
                playerCount = amounts[0].toIntOrNull() ?: playerCount
                maxPlayerCount = amounts[1].toIntOrNull() ?: maxPlayerCount
            }
        }

        val cleaned =
            //? if 1.8.9
            //message.createCopy().apply { siblings.removeIf { it.unformattedText.isEmpty() } }
            //? else
            message.copy().apply { siblings.removeIf { it.string.isEmpty() } }
        val playerName = cleaned.siblings.firstOrNull() ?: return message

        return when {
            joinMatch != null -> handleJoinMessage(message, playerName)
            plainMessage.contains(LanguageData.GAME_LEAVE) -> handleLeaveMessage(message, playerName)
            HyChatterConfig.gameStatusRestyle -> handleGameStatusMessage(message, cleaned, plainMessage)
            else -> message
        }
    }

    private fun handleJoinMessage(message: Component, playerName: Component): Component {
        val amount = amountComponent(playerCount)

        //? if 1.8.9 {
        /*return if (HyChatterConfig.gameStatusRestyle) {
            val prefix = ChatComponentText("")
                .appendSibling(
                    ChatComponentText("+ ")
                        .setChatStyle(
                            ChatStyle()
                                .setColor(EnumChatFormatting.GREEN)
                                .setBold(true)
                        )
                )

            if (HyChatterConfig.playerCountBeforePlayerName) {
                prefix
                    .appendSibling(amount)
                    .appendSibling(ChatComponentText(" "))
                    .appendSibling(playerName)
            } else {
                prefix
                    .appendSibling(playerName)
                    .appendSibling(ChatComponentText(" "))
                    .appendSibling(amount)
            }
        } else {
            val base = ChatComponentText("")
                .setChatStyle(
                    ChatStyle().setColor(EnumChatFormatting.YELLOW)
                )

            if (HyChatterConfig.playerCountBeforePlayerName) {
                base
                    .appendSibling(amount)
                    .appendSibling(ChatComponentText(" "))
                    .appendSibling(playerName)
                    .appendSibling(ChatComponentText(" has joined!"))
            } else if (HyChatterConfig.padPlayerCount) {
                base
                    .appendSibling(playerName.createCopy())
                    .appendSibling(ChatComponentText(" has joined "))
                    .appendSibling(amount)
                    .appendSibling(ChatComponentText("!"))
            } else {
                message
            }
        }
        *///?} else {
        return if (HyChatterConfig.gameStatusRestyle) {
            val prefix = Component.empty()
                .append(Component.literal("+ ").withStyle(ChatFormatting.GREEN, ChatFormatting.BOLD))
            if (HyChatterConfig.playerCountBeforePlayerName) {
                prefix.append(amount).append(" ").append(playerName)
            } else {
                prefix.append(playerName).append(" ").append(amount)
            }
        } else {
            val base = Component.empty().withStyle(ChatFormatting.YELLOW)
            if (HyChatterConfig.playerCountBeforePlayerName) {
                base.append(amount).append(" ").append(playerName).append(" has joined!")
            } else if (HyChatterConfig.padPlayerCount) {
                base.append(playerName.copy()).append(" has joined ").append(amount).append("!")
            } else {
                message
            }
        }
        //?}
    }

    private fun handleLeaveMessage(message: Component, playerName: Component): Component {
        val amount = amountComponent(--playerCount)

        //? if 1.8.9 {
        /*return if (HyChatterConfig.gameStatusRestyle) {
            val prefix = ChatComponentText("")
                .appendSibling(
                    ChatComponentText("- ")
                        .setChatStyle(
                            ChatStyle()
                                .setColor(EnumChatFormatting.RED)
                                .setBold(true)
                        )
                )

            when {
                !HyChatterConfig.playerCountOnPlayerLeave ->
                    prefix.appendSibling(playerName)

                HyChatterConfig.playerCountBeforePlayerName ->
                    prefix
                        .appendSibling(amount)
                        .appendSibling(ChatComponentText(" "))
                        .appendSibling(playerName)

                else ->
                    prefix
                        .appendSibling(playerName)
                        .appendSibling(ChatComponentText(" "))
                        .appendSibling(amount)
            }
        } else if (HyChatterConfig.playerCountOnPlayerLeave) {
            if (HyChatterConfig.playerCountBeforePlayerName) {
                ChatComponentText("")
                    .appendSibling(amount)
                    .appendSibling(ChatComponentText(" "))
                    .appendSibling(message)
            } else {
                ChatComponentText("")
                    .setChatStyle(
                        ChatStyle().setColor(EnumChatFormatting.YELLOW)
                    )
                    .appendSibling(playerName.createCopy())
                    .appendSibling(ChatComponentText(" has quit "))
                    .appendSibling(amount)
                    .appendSibling(ChatComponentText("!"))
            }
        } else {
            message
        }
        *///?} else {
        return if (HyChatterConfig.gameStatusRestyle) {
            val prefix = Component.empty()
                .append(Component.literal("- ").withStyle(ChatFormatting.RED, ChatFormatting.BOLD))
            if (!HyChatterConfig.playerCountOnPlayerLeave) {
                prefix.append(playerName)
            } else if (HyChatterConfig.playerCountBeforePlayerName) {
                prefix.append(amount).append(" ").append(playerName)
            } else {
                prefix.append(playerName).append(" ").append(amount)
            }
        } else if (HyChatterConfig.playerCountOnPlayerLeave) {
            if (HyChatterConfig.playerCountBeforePlayerName) {
                Component.empty().append(amount).append(" ").append(message)
            } else {
                Component.empty().withStyle(ChatFormatting.YELLOW)
                    .append(playerName.copy()).append(" has quit ").append(amount).append("!")
            }
        } else {
            message
        }
        //?}
    }

    private fun handleGameStatusMessage(message: Component, cleaned: Component, plainMessage: String): Component = when {
        //? if 1.8.9 {
        /*plainMessage.matches(LanguageData.GAME_STARTING) ->
            ChatComponentText("")
                .appendSibling(
                    ChatComponentText("⁎ ")
                        .setChatStyle(
                            ChatStyle()
                                .setColor(EnumChatFormatting.YELLOW)
                                .setBold(true)
                        )
                )
                .appendSibling(
                    cleaned.siblings[0].createCopy()
                        .setChatStyle(
                            ChatStyle().setColor(EnumChatFormatting.GREEN)
                        )
                )
                .appendSibling(
                    cleaned.siblings[1].createCopy()
                        .setChatStyle(
                            ChatStyle()
                                .setColor(EnumChatFormatting.AQUA)
                                .setBold(true)
                        )
                )
                .appendSibling(
                    cleaned.siblings[2].createCopy()
                        .setChatStyle(
                            ChatStyle().setColor(EnumChatFormatting.GREEN)
                        )
                )

        plainMessage == LanguageData.GAME_START_CANCELLED ->
            ChatComponentText("⁎ ")
                .setChatStyle(
                    ChatStyle()
                        .setColor(EnumChatFormatting.YELLOW)
                        .setBold(true)
                )
                .appendSibling(
                    ChatComponentText("Start cancelled.")
                        .setChatStyle(
                            ChatStyle().setColor(EnumChatFormatting.RED)
                        )
                )

        plainMessage == LanguageData.GAME_START_DELAYED ->
            ChatComponentText("⁎ ")
                .setChatStyle(
                    ChatStyle()
                        .setColor(EnumChatFormatting.YELLOW)
                        .setBold(true)
                )
                .appendSibling(
                    ChatComponentText("Start delayed.")
                        .setChatStyle(
                            ChatStyle().setColor(EnumChatFormatting.RED)
                        )
                )
        *///?} else {
        plainMessage.matches(LanguageData.GAME_STARTING) ->
            Component.empty()
                .append(Component.literal("⁎ ").withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD))
                .append(cleaned.siblings[0].copy().withStyle(ChatFormatting.GREEN))
                .append(cleaned.siblings[1].copy().withStyle(ChatFormatting.AQUA, ChatFormatting.BOLD))
                .append(cleaned.siblings[2].copy().withStyle(ChatFormatting.GREEN))

        plainMessage == LanguageData.GAME_START_CANCELLED ->
            Component.literal("⁎ ").withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD)
                .append(Component.literal("Start cancelled.").withStyle(ChatFormatting.RED))

        plainMessage == LanguageData.GAME_START_DELAYED ->
            Component.literal("⁎ ").withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD)
                .append(Component.literal("Start delayed.").withStyle(ChatFormatting.RED))
        //?}
        else -> message
    }

    private fun amountComponent(count: Int): Component =
        //? if 1.8.9 {
        /*ChatComponentText("(")
            .setChatStyle(
                ChatStyle().setColor(EnumChatFormatting.YELLOW)
            )
            .appendSibling(
                ChatComponentText(pad(count))
                    .setChatStyle(
                        ChatStyle().setColor(EnumChatFormatting.AQUA)
                    )
            )
            .appendSibling(
                ChatComponentText("/")
                    .setChatStyle(
                        ChatStyle().setColor(EnumChatFormatting.YELLOW)
                    )
            )
            .appendSibling(
                ChatComponentText(maxPlayerCount.toString())
                    .setChatStyle(
                        ChatStyle().setColor(EnumChatFormatting.AQUA)
                    )
            )
            .appendSibling(
                ChatComponentText(")")
                    .setChatStyle(
                        ChatStyle().setColor(EnumChatFormatting.YELLOW)
                    )
            )
        *///?} else {
        Component.literal("(").withStyle(ChatFormatting.YELLOW)
            .append(Component.literal(pad(count)).withStyle(ChatFormatting.AQUA))
            .append(Component.literal("/").withStyle(ChatFormatting.YELLOW))
            .append(Component.literal(maxPlayerCount.toString()).withStyle(ChatFormatting.AQUA))
            .append(Component.literal(")").withStyle(ChatFormatting.YELLOW))
        //?}

    private fun pad(n: Int): String {
        val nString = n.toString()
        return if (HyChatterConfig.padPlayerCount) {
            nString.padStart(maxPlayerCount.toString().length, '0')
        } else {
            nString
        }
    }
}
