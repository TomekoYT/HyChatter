package tomeko.hychat.config.chat.handlers.modules.blockers

//? if 1.8.9 {
/*import tomeko.hychat.utils.LegacyComponents
import tomeko.hychat.utils.string
import tomeko.hychat.utils.siblings
import tomeko.hychat.utils.style
import tomeko.hychat.utils.plainCopy
import tomeko.hychat.utils.withStyle
import tomeko.hychat.utils.append
import tomeko.hychat.utils.copy
import net.hypixel.data.type.GameType
import net.minecraft.event.HoverEvent
import net.minecraft.util.EnumChatFormatting
import net.minecraft.util.ChatStyle
import org.polyfrost.oneconfig.api.hypixel.v1.HypixelUtils
import tomeko.hychat.config.HyChatConfig
import tomeko.hychat.config.chat.handlers.ChatReceiveModule
import tomeko.hychat.config.chat.handlers.ChatSendModule
import tomeko.hychat.config.data.providers.LanguageData
import tomeko.hychat.config.events.ChatReceiveEvent
import tomeko.hychat.config.events.ChatSendEvent
import tomeko.hychat.utils.ChatUtils
import java.text.DecimalFormat
import kotlin.jvm.optionals.getOrNull

object ShoutBlocker : ChatSendModule, ChatReceiveModule {
    var shoutCooldown = 0L
    val decimalFormat = DecimalFormat("#.#")

    override fun onChatSend(event: ChatSendEvent) {
        if (!event.message.startsWith("/shout ")) return

        if (shoutCooldown < System.currentTimeMillis()) {
            shoutCooldown = System.currentTimeMillis() + getCooldownLengthInSeconds() * 1000
        } else {
            val secondsLeft = (shoutCooldown - System.currentTimeMillis()) / 1000L
            ChatUtils.displayMessage(
                LegacyComponents.literal("Shout command is on cooldown. Please wait ${decimalFormat.format(secondsLeft)} more second${if (secondsLeft == 1L) "" else "s"} before shouting another message.")
                    .withStyle(ChatStyle().setChatHoverEvent(
                        HoverEvent(
                            HoverEvent.Action.SHOW_TEXT,
                            LegacyComponents.empty()
                                .append(
                                    LegacyComponents.literal("Hytils Reborn\n")
                                        .withStyle(EnumChatFormatting.GOLD, EnumChatFormatting.BOLD)
                                )
                                .append(
                                    LegacyComponents.literal("Your message was blocked by the \"Shout Cooldown\" setting. \nPlease wait before shouting another message.")
                                        .withStyle(EnumChatFormatting.GRAY)
                                )
                        )
                    ))
            )
            event.cancelled = true
        }
    }

    override fun onChatReceived(event: ChatReceiveEvent) {
        val location = HypixelUtils.getLocation()

        val message = event.plainMessage
        if ((location.gameType.getOrNull() == GameType.SKYWARS && message == LanguageData.CANNOT_SHOUT_BEFORE_SKYWARS)
            || message == LanguageData.CANNOT_SHOUT_BEFORE_GAME
            || message == LanguageData.CANNOT_SHOUT_AFTER_GAME
            || message == LanguageData.NO_SPECTATOR_COMMANDS
        ) {
            shoutCooldown = 0L
        }
    }

    private fun getCooldownLengthInSeconds(): Long {
        val location = HypixelUtils.getLocation()
        if ("LOBBY" != location.mode.getOrNull() && location.gameType.isPresent) {
            when (location.gameType.get()) {
                GameType.BEDWARS -> if ("BEDWARS_EIGHT_ONE" != location.mode.getOrNull()) return 60L
                GameType.SKYWARS -> return 3L
                GameType.ARCADE -> if ("PVP_CTW" == location.mode.orElse(null)) return 10L
                GameType.UHC -> if ("TEAMS" == location.mode.orElse(null)) return 90L
                else -> {}
            }
        }
        return 0L
    }

    override val isEnabled
        get() = HyChatConfig.preventShoutingOnCooldown
    override val priority = -1
}
*///?} else {
import net.hypixel.data.type.GameType
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.HoverEvent
import net.minecraft.network.chat.Style
import org.polyfrost.oneconfig.api.hypixel.v1.HypixelUtils
import tomeko.hychat.config.HyChatConfig
import tomeko.hychat.config.chat.handlers.ChatReceiveModule
import tomeko.hychat.config.chat.handlers.ChatSendModule
import tomeko.hychat.config.data.providers.LanguageData
import tomeko.hychat.config.events.ChatReceiveEvent
import tomeko.hychat.config.events.ChatSendEvent
import tomeko.hychat.utils.ChatUtils
import java.text.DecimalFormat
import kotlin.jvm.optionals.getOrNull

object ShoutBlocker : ChatSendModule, ChatReceiveModule {
    var shoutCooldown = 0L
    val decimalFormat = DecimalFormat("#.#")

    override fun onChatSend(event: ChatSendEvent) {
        if (!event.message.startsWith("/shout ")) return

        if (shoutCooldown < System.currentTimeMillis()) {
            shoutCooldown = System.currentTimeMillis() + getCooldownLengthInSeconds() * 1000
        } else {
            val secondsLeft = (shoutCooldown - System.currentTimeMillis()) / 1000L
            ChatUtils.displayMessage(
                Component.literal("Shout command is on cooldown. Please wait ${decimalFormat.format(secondsLeft)} more second${if (secondsLeft == 1L) "" else "s"} before shouting another message.")
                    .setStyle(Style.EMPTY.withHoverEvent(
                        //~ if <1.21.5 '.ShowText(' -> '(HoverEvent.Action.SHOW_TEXT,'
                        HoverEvent.ShowText(
                            Component.empty()
                                .append(Component.literal("Hytils Reborn\n")
                                    .withStyle(ChatFormatting.GOLD, ChatFormatting.BOLD))
                                .append(Component.literal("Your message was blocked by the \"Shout Cooldown\" setting. \nPlease wait before shouting another message.")
                                    .withStyle(ChatFormatting.GRAY))
                        )
                    ))
            )
            event.cancelled = true
        }
    }

    override fun onChatReceived(event: ChatReceiveEvent) {
        val location = HypixelUtils.getLocation()

        val message = event.plainMessage
        if ((location.gameType.getOrNull() == GameType.SKYWARS && message == LanguageData.CANNOT_SHOUT_BEFORE_SKYWARS)
            || message == LanguageData.CANNOT_SHOUT_BEFORE_GAME
            || message == LanguageData.CANNOT_SHOUT_AFTER_GAME
            || message == LanguageData.NO_SPECTATOR_COMMANDS
        ) {
            shoutCooldown = 0L
        }
    }

    private fun getCooldownLengthInSeconds(): Long {
        val location = HypixelUtils.getLocation()
        if ("LOBBY" != location.mode.getOrNull() && location.gameType.isPresent) {
            when (location.gameType.get()) {
                GameType.BEDWARS -> if ("BEDWARS_EIGHT_ONE" != location.mode.getOrNull()) return 60L
                GameType.SKYWARS -> return 3L
                GameType.ARCADE -> if ("PVP_CTW" == location.mode.orElse(null)) return 10L
                GameType.UHC -> if ("TEAMS" == location.mode.orElse(null)) return 90L
                else -> {}
            }
        }
        return 0L
    }

    override val isEnabled
        get() = HyChatConfig.preventShoutingOnCooldown
    override val priority = -1
}
//?}