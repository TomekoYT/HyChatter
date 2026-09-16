package tomeko.hychatter.config.chat.handlers.modules.blockers

//? if 1.8.9 {
/*import tomeko.hychat.utils.LegacyComponents
import tomeko.hychat.utils.string
import tomeko.hychat.utils.siblings
import tomeko.hychat.utils.style
import tomeko.hychat.utils.plainCopy
import tomeko.hychat.utils.withStyle
import tomeko.hychat.utils.append
import tomeko.hychat.utils.copy
import net.hypixel.data.rank.PackageRank
import net.minecraft.event.HoverEvent
import net.minecraft.util.EnumChatFormatting
import net.minecraft.util.ChatStyle
import org.polyfrost.oneconfig.api.hypixel.v1.HypixelUtils
import tomeko.hychat.config.HyChatterConfig
import tomeko.hychat.config.chat.handlers.ChatSendModule
import tomeko.hychat.config.events.ChatSendEvent
import tomeko.hychat.utils.ChatUtils
import java.text.DecimalFormat

object NonCooldownBlocker : ChatSendModule {
    private var lastSent = 0L
    private val decimalFormat = DecimalFormat("#.#")

    private const val COOLDOWN_SECONDS = 3L

    override fun onChatSend(event: ChatSendEvent) {
        if (event.message.startsWith("/")) return // FIXME: bypassable via chat commands like `/ac`

        val rank = HypixelUtils.getPlayerInfo().packageRank
        if (rank.isPresent && rank.get() != PackageRank.NONE) return

        if (lastSent < System.currentTimeMillis()) {
            lastSent = System.currentTimeMillis() + (COOLDOWN_SECONDS * 1000L)
        } else {
            val secondsLeft = (lastSent - System.currentTimeMillis()) / 1000L
            ChatUtils.displayMessage(
                LegacyComponents.literal("Your freedom of speech is on cooldown. Please wait ${decimalFormat.format(secondsLeft)} more second${if (secondsLeft == 1L) "" else "s"} before sending another message.")
                    .withStyle(ChatStyle().setChatHoverEvent(
                        HoverEvent(
                            HoverEvent.Action.SHOW_TEXT,
                            LegacyComponents.empty()
                                .append(
                                    LegacyComponents.literal("Hytils Reborn\n")
                                        .withStyle(EnumChatFormatting.GOLD, EnumChatFormatting.BOLD)
                                )
                                .append(
                                    LegacyComponents.literal("Your message was blocked by the \"Non Speech Cooldown\" setting. \nPlease wait before sending another message.")
                                        .withStyle(EnumChatFormatting.GRAY)
                                )
                        )
                    ))
            )
            event.cancelled = true
        }
    }

    override val isEnabled
        get() = HyChatterConfig.preventNonCooldown
    override val priority = -1
}
*///?} else {
import net.hypixel.data.rank.PackageRank
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.HoverEvent
import net.minecraft.network.chat.Style
import org.polyfrost.oneconfig.api.hypixel.v1.HypixelUtils
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.chat.handlers.ChatSendModule
import tomeko.hychatter.config.events.ChatSendEvent
import tomeko.hychatter.utils.ChatUtils
import java.text.DecimalFormat

object NonCooldownBlocker : ChatSendModule {
    private var lastSent = 0L
    private val decimalFormat = DecimalFormat("#.#")

    private const val COOLDOWN_SECONDS = 3L

    override fun onChatSend(event: ChatSendEvent) {
        if (event.message.startsWith("/")) return // FIXME: bypassable via chat commands like `/ac`

        val rank = HypixelUtils.getPlayerInfo().packageRank
        if (rank.isPresent && rank.get() != PackageRank.NONE) return

        if (lastSent < System.currentTimeMillis()) {
            lastSent = System.currentTimeMillis() + (COOLDOWN_SECONDS * 1000L)
        } else {
            val secondsLeft = (lastSent - System.currentTimeMillis()) / 1000L
            ChatUtils.displayMessage(
                Component.literal("Your freedom of speech is on cooldown. Please wait ${decimalFormat.format(secondsLeft)} more second${if (secondsLeft == 1L) "" else "s"} before sending another message.")
                    .setStyle(Style.EMPTY.withHoverEvent(
                        //~ if <1.21.5 '.ShowText(' -> '(HoverEvent.Action.SHOW_TEXT,'
                        HoverEvent.ShowText(
                            Component.empty()
                                .append(Component.literal("Hytils Reborn\n")
                                    .withStyle(ChatFormatting.GOLD, ChatFormatting.BOLD))
                                .append(Component.literal("Your message was blocked by the \"Non Speech Cooldown\" setting. \nPlease wait before sending another message.")
                                    .withStyle(ChatFormatting.GRAY))
                        )
                    ))
            )
            event.cancelled = true
        }
    }

    override val isEnabled
        get() = HyChatterConfig.preventNonCooldown
    override val priority = -1
}
//?}