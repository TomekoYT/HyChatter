package tomeko.hychatter.chat

//? if 1.8.9 {
/*import tomeko.hychatter.utils.LegacyComponents
import tomeko.hychatter.utils.string
import tomeko.hychatter.utils.siblings
import tomeko.hychatter.utils.style
import tomeko.hychatter.utils.plainCopy
import tomeko.hychatter.utils.withStyle
import tomeko.hychatter.utils.append
import tomeko.hychatter.utils.copy
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
import tomeko.hychatter.config.data.providers.LanguageData

object GameStatusRestyler {
    private var playerCount = -1
    private var maxPlayerCount = -1

    fun register() {
        ClientReceiveMessageEvents.MODIFY_GAME.register(::onGameMessage)
    }

    private fun onGameMessage(message: Component, fromActionBar: Boolean): Component {
        if (fromActionBar) return message

        val plainMessage = message.string
        val joinMatch = LanguageData.GAME_JOIN.find(plainMessage)
        joinMatch?.let { match ->
            val amount = match.groups["amount"]?.value?.removeSurrounding("(", ")") ?: return@let
            val amounts = amount.split('/')
            if (amounts.size == 2) {
                playerCount = amounts[0].toIntOrNull() ?: playerCount
                maxPlayerCount = amounts[1].toIntOrNull() ?: maxPlayerCount
            }
        }

        val cleaned = message.copy().apply { siblings.removeIf { it.string.isEmpty() } }
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

        return if (HyChatterConfig.gameStatusRestyle) {
            //? if 1.8.9 {
            /*val prefix = LegacyComponents.empty()
                .append(LegacyComponents.literal("+ ").withStyle(EnumChatFormatting.GREEN, EnumChatFormatting.BOLD))
            *///?} else {
            val prefix = Component.empty()
                .append(Component.literal("+ ").withStyle(ChatFormatting.GREEN, ChatFormatting.BOLD))
            //?}
            if (HyChatterConfig.playerCountBeforePlayerName) {
                prefix.append(amount).append(" ").append(playerName)
            } else {
                prefix.append(playerName).append(" ").append(amount)
            }
        } else {
            //? if 1.8.9 {
            //val base = LegacyComponents.empty().withStyle(EnumChatFormatting.YELLOW)
            //?} else {
            val base = Component.empty().withStyle(ChatFormatting.YELLOW)
            //?}
            if (HyChatterConfig.playerCountBeforePlayerName) {
                base.append(amount).append(" ").append(playerName).append(" has joined!")
            } else if (HyChatterConfig.padPlayerCount) {
                base.append(playerName.copy()).append(" has joined ").append(amount).append("!")
            } else {
                message
            }
        }
    }

    private fun handleLeaveMessage(message: Component, playerName: Component): Component {
        val amount = amountComponent(--playerCount)

        return if (HyChatterConfig.gameStatusRestyle) {
            //? if 1.8.9 {
            /*val prefix = LegacyComponents.empty()
                .append(LegacyComponents.literal("- ").withStyle(EnumChatFormatting.RED, EnumChatFormatting.BOLD))
            *///?} else {
            val prefix = Component.empty()
                .append(Component.literal("- ").withStyle(ChatFormatting.RED, ChatFormatting.BOLD))
            //?}
            if (!HyChatterConfig.playerCountOnPlayerLeave) {
                prefix.append(playerName)
            } else if (HyChatterConfig.playerCountBeforePlayerName) {
                prefix.append(amount).append(" ").append(playerName)
            } else {
                prefix.append(playerName).append(" ").append(amount)
            }
        } else if (HyChatterConfig.playerCountOnPlayerLeave) {
            if (HyChatterConfig.playerCountBeforePlayerName) {
                //? if 1.8.9 {
                //LegacyComponents.empty().append(amount).append(" ").append(message)
                //?} else {
                Component.empty().append(amount).append(" ").append(message)
                //?}
            } else {
                //? if 1.8.9 {
                /*LegacyComponents.empty().withStyle(EnumChatFormatting.YELLOW)
                    .append(playerName.copy()).append(" has quit ").append(amount).append("!")
                *///?} else {
                Component.empty().withStyle(ChatFormatting.YELLOW)
                    .append(playerName.copy()).append(" has quit ").append(amount).append("!")
                //?}
            }
        } else {
            message
        }
    }

    private fun handleGameStatusMessage(message: Component, cleaned: Component, plainMessage: String): Component = when {
        plainMessage.matches(LanguageData.GAME_STARTING) ->
            //? if 1.8.9 {
            /*LegacyComponents.empty()
                .append(LegacyComponents.literal("⁎ ").withStyle(EnumChatFormatting.YELLOW, EnumChatFormatting.BOLD))
                .append(cleaned.siblings[0].copy().withStyle(EnumChatFormatting.GREEN))
                .append(cleaned.siblings[1].copy().withStyle(EnumChatFormatting.AQUA, EnumChatFormatting.BOLD))
                .append(cleaned.siblings[2].copy().withStyle(EnumChatFormatting.GREEN))
            *///?} else {
            Component.empty()
                .append(Component.literal("⁎ ").withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD))
                .append(cleaned.siblings[0].copy().withStyle(ChatFormatting.GREEN))
                .append(cleaned.siblings[1].copy().withStyle(ChatFormatting.AQUA, ChatFormatting.BOLD))
                .append(cleaned.siblings[2].copy().withStyle(ChatFormatting.GREEN))
            //?}

        plainMessage == LanguageData.GAME_START_CANCELLED ->
            //? if 1.8.9 {
            /*LegacyComponents.literal("⁎ ").withStyle(EnumChatFormatting.YELLOW, EnumChatFormatting.BOLD)
                .append(LegacyComponents.literal("Start cancelled.").withStyle(EnumChatFormatting.RED))
            *///?} else {
            Component.literal("⁎ ").withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD)
                .append(Component.literal("Start cancelled.").withStyle(ChatFormatting.RED))
            //?}

        plainMessage == LanguageData.GAME_START_DELAYED ->
            //? if 1.8.9 {
            /*LegacyComponents.literal("⁎ ").withStyle(EnumChatFormatting.YELLOW, EnumChatFormatting.BOLD)
                .append(LegacyComponents.literal("Start delayed.").withStyle(EnumChatFormatting.RED))
            *///?} else {
            Component.literal("⁎ ").withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD)
                .append(Component.literal("Start delayed.").withStyle(ChatFormatting.RED))
            //?}

        else -> message
    }

    private fun amountComponent(count: Int): Component =
        //? if 1.8.9 {
        /*LegacyComponents.literal("(").withStyle(EnumChatFormatting.YELLOW)
            .append(LegacyComponents.literal(pad(count)).withStyle(EnumChatFormatting.AQUA))
            .append(LegacyComponents.literal("/").withStyle(EnumChatFormatting.YELLOW))
            .append(LegacyComponents.literal(maxPlayerCount.toString()).withStyle(EnumChatFormatting.AQUA))
            .append(LegacyComponents.literal(")").withStyle(EnumChatFormatting.YELLOW))
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
