package tomeko.hychatter.config.chat.handlers.modules.modifiers

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
import net.minecraft.util.IChatComponent
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.chat.handlers.ChatReceiveModule
import tomeko.hychatter.config.data.providers.LanguageData
import tomeko.hychatter.config.events.ChatReceiveEvent

object GameStatusRestyler : ChatReceiveModule {
    private var playerCount = -1
    private var maxPlayerCount = -1

    override fun onChatReceived(event: ChatReceiveEvent) {
        val joinMatch = LanguageData.GAME_JOIN.find(event.plainMessage)
        joinMatch?.let { match ->
            val amount = match.groups["amount"]?.value?.removeSurrounding("(", ")") ?: return@let

            val amounts = amount.split('/')
            if (amounts.size == 2) {
                playerCount = amounts[0].toIntOrNull() ?: playerCount
                maxPlayerCount = amounts[1].toIntOrNull() ?: maxPlayerCount
            }
        }

        val cleaned = event.message.copy().apply { siblings.removeIf { it.string.isEmpty() } }
        val playerName = cleaned.siblings.firstOrNull() ?: return

        when {
            joinMatch != null
                -> handleJoinMessage(event, playerName)

            event.plainMessage.contains(LanguageData.GAME_LEAVE)
                -> handleLeaveMessage(event, playerName)

            HyChatterConfig.gameStatusRestyle
                -> handleGameStatusMessage(event, cleaned)
        }
    }

    private fun handleJoinMessage(event: ChatReceiveEvent, playerName: IChatComponent) {
        val amount = amountComponent(playerCount)

        event.message = if (HyChatterConfig.gameStatusRestyle) {
            val prefix = LegacyComponents.empty().append(LegacyComponents.literal("+ ").withStyle(EnumChatFormatting.GREEN, EnumChatFormatting.BOLD))
            if (HyChatterConfig.playerCountBeforePlayerName) {
                prefix.append(amount).append(" ").append(playerName)
            } else {
                prefix.append(playerName).append(" ").append(amount)
            }
        } else {
            val base = LegacyComponents.empty().withStyle(EnumChatFormatting.YELLOW)
            if (HyChatterConfig.playerCountBeforePlayerName) {
                base.append(amount).append(" ").append(playerName).append(" has joined!")
            } else if (HyChatterConfig.padPlayerCount) {
                base.append(playerName.copy()).append(" has joined ").append(amount).append("!")
            } else {
                event.message
            }
        }
    }

    private fun handleLeaveMessage(event: ChatReceiveEvent, playerName: IChatComponent) {
        val amount = amountComponent(--playerCount)

        event.message = if (HyChatterConfig.gameStatusRestyle) {
            val prefix = LegacyComponents.empty().append(LegacyComponents.literal("- ").withStyle(EnumChatFormatting.RED, EnumChatFormatting.BOLD))
            if (!HyChatterConfig.playerCountOnPlayerLeave) {
                prefix.append(playerName)
            } else if (HyChatterConfig.playerCountBeforePlayerName) {
                prefix.append(amount).append(" ").append(playerName)
            } else {
                prefix.append(playerName).append(" ").append(amount)
            }
        } else {
            if (HyChatterConfig.playerCountOnPlayerLeave) {
                if (HyChatterConfig.playerCountBeforePlayerName) {
                    LegacyComponents.empty().append(amount).append(" ").append(event.message)
                } else {
                    LegacyComponents.empty().withStyle(EnumChatFormatting.YELLOW)
                        .append(playerName.copy()).append(" has quit ").append(amount).append("!")
                }
            } else {
                event.message
            }
        }
    }

    private fun handleGameStatusMessage(event: ChatReceiveEvent, cleaned: IChatComponent) {
        event.message = when {
            event.plainMessage.matches(LanguageData.GAME_STARTING) -> {
                LegacyComponents.empty()
                    .append(LegacyComponents.literal("⁎ ").withStyle(EnumChatFormatting.YELLOW, EnumChatFormatting.BOLD))
                    .append(cleaned.siblings[0].copy().withStyle(EnumChatFormatting.GREEN))
                    .append(cleaned.siblings[1].copy().withStyle(EnumChatFormatting.AQUA, EnumChatFormatting.BOLD))
                    .append(cleaned.siblings[2].copy().withStyle(EnumChatFormatting.GREEN))
            }

            event.plainMessage == LanguageData.GAME_START_CANCELLED -> {
                LegacyComponents.literal("⁎ ").withStyle(EnumChatFormatting.YELLOW, EnumChatFormatting.BOLD)
                    .append(LegacyComponents.literal("Start cancelled.").withStyle(EnumChatFormatting.RED))
            }

            event.plainMessage == LanguageData.GAME_START_DELAYED -> {
                LegacyComponents.literal("⁎ ").withStyle(EnumChatFormatting.YELLOW, EnumChatFormatting.BOLD)
                    .append(LegacyComponents.literal("Start delayed.").withStyle(EnumChatFormatting.RED))
            }

            else -> event.message
        }
    }

    private fun amountComponent(count: Int) = LegacyComponents.literal("(").withStyle(EnumChatFormatting.YELLOW)
        .append(LegacyComponents.literal(pad(count)).withStyle(EnumChatFormatting.AQUA))
        .append(LegacyComponents.literal("/").withStyle(EnumChatFormatting.YELLOW))
        .append(LegacyComponents.literal(maxPlayerCount.toString()).withStyle(EnumChatFormatting.AQUA))
        .append(LegacyComponents.literal(")").withStyle(EnumChatFormatting.YELLOW))

    private fun pad(n: Int): String {
        val nString = n.toString()
        return if (HyChatterConfig.padPlayerCount) {
            nString.padStart(maxPlayerCount.toString().length, '0')
        } else {
            nString
        }
    }

    // this should run before game start compactor
    override val priority = 1
}
*///?} else {
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.chat.handlers.ChatReceiveModule
import tomeko.hychatter.config.data.providers.LanguageData
import tomeko.hychatter.config.events.ChatReceiveEvent

object GameStatusRestyler : ChatReceiveModule {
    private var playerCount = -1
    private var maxPlayerCount = -1

    override fun onChatReceived(event: ChatReceiveEvent) {
        val joinMatch = LanguageData.GAME_JOIN.find(event.plainMessage)
        joinMatch?.let { match ->
            val amount = match.groups["amount"]?.value?.removeSurrounding("(", ")") ?: return@let

            val amounts = amount.split('/')
            if (amounts.size == 2) {
                playerCount = amounts[0].toIntOrNull() ?: playerCount
                maxPlayerCount = amounts[1].toIntOrNull() ?: maxPlayerCount
            }
        }

        val cleaned = event.message.copy().apply { siblings.removeIf { it.string.isEmpty() } }
        val playerName = cleaned.siblings.firstOrNull() ?: return

        when {
            joinMatch != null
                -> handleJoinMessage(event, playerName)

            event.plainMessage.contains(LanguageData.GAME_LEAVE)
                -> handleLeaveMessage(event, playerName)

            HyChatterConfig.gameStatusRestyle
                -> handleGameStatusMessage(event, cleaned)
        }
    }

    private fun handleJoinMessage(event: ChatReceiveEvent, playerName: Component) {
        val amount = amountComponent(playerCount)

        event.message = if (HyChatterConfig.gameStatusRestyle) {
            val prefix = Component.empty().append(Component.literal("+ ").withStyle(ChatFormatting.GREEN, ChatFormatting.BOLD))
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
                event.message
            }
        }
    }

    private fun handleLeaveMessage(event: ChatReceiveEvent, playerName: Component) {
        val amount = amountComponent(--playerCount)

        event.message = if (HyChatterConfig.gameStatusRestyle) {
            val prefix = Component.empty().append(Component.literal("- ").withStyle(ChatFormatting.RED, ChatFormatting.BOLD))
            if (!HyChatterConfig.playerCountOnPlayerLeave) {
                prefix.append(playerName)
            } else if (HyChatterConfig.playerCountBeforePlayerName) {
                prefix.append(amount).append(" ").append(playerName)
            } else {
                prefix.append(playerName).append(" ").append(amount)
            }
        } else {
            if (HyChatterConfig.playerCountOnPlayerLeave) {
                if (HyChatterConfig.playerCountBeforePlayerName) {
                    Component.empty().append(amount).append(" ").append(event.message)
                } else {
                    Component.empty().withStyle(ChatFormatting.YELLOW)
                        .append(playerName.copy()).append(" has quit ").append(amount).append("!")
                }
            } else {
                event.message
            }
        }
    }

    private fun handleGameStatusMessage(event: ChatReceiveEvent, cleaned: Component) {
        event.message = when {
            event.plainMessage.matches(LanguageData.GAME_STARTING) -> {
                Component.empty()
                    .append(Component.literal("⁎ ").withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD))
                    .append(cleaned.siblings[0].copy().withStyle(ChatFormatting.GREEN))
                    .append(cleaned.siblings[1].copy().withStyle(ChatFormatting.AQUA, ChatFormatting.BOLD))
                    .append(cleaned.siblings[2].copy().withStyle(ChatFormatting.GREEN))
            }

            event.plainMessage == LanguageData.GAME_START_CANCELLED -> {
                Component.literal("⁎ ").withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD)
                    .append(Component.literal("Start cancelled.").withStyle(ChatFormatting.RED))
            }

            event.plainMessage == LanguageData.GAME_START_DELAYED -> {
                Component.literal("⁎ ").withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD)
                    .append(Component.literal("Start delayed.").withStyle(ChatFormatting.RED))
            }

            else -> event.message
        }
    }

    private fun amountComponent(count: Int) = Component.literal("(").withStyle(ChatFormatting.YELLOW)
        .append(Component.literal(pad(count)).withStyle(ChatFormatting.AQUA))
        .append(Component.literal("/").withStyle(ChatFormatting.YELLOW))
        .append(Component.literal(maxPlayerCount.toString()).withStyle(ChatFormatting.AQUA))
        .append(Component.literal(")").withStyle(ChatFormatting.YELLOW))

    private fun pad(n: Int): String {
        val nString = n.toString()
        return if (HyChatterConfig.padPlayerCount) {
            nString.padStart(maxPlayerCount.toString().length, '0')
        } else {
            nString
        }
    }

    // this should run before game start compactor
    override val priority = 1
}
//?}