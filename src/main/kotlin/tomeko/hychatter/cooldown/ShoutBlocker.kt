package tomeko.hychatter.cooldown

//? if 1.8.9 {
/*import net.hypixel.data.type.GameType
import net.minecraft.client.Minecraft
import net.minecraft.event.HoverEvent
import net.minecraft.util.ChatComponentText
import net.minecraft.util.EnumChatFormatting
import net.minecraft.util.ChatStyle
import net.minecraft.util.IChatComponent as Component
*///?} else {
import net.hypixel.data.type.GameType
import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.HoverEvent
import net.minecraft.network.chat.Style
//?}
//? if ornithe {
/*import tomeko.hychatter.event.ClientReceiveMessageEvents
import tomeko.hychatter.event.ClientSendMessageEvents
*///?} else {
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents
//?}
import org.polyfrost.oneconfig.api.hypixel.v1.HypixelUtils
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.LanguageData
import tomeko.hychatter.utils.Constants
import java.text.DecimalFormat
import kotlin.jvm.optionals.getOrNull

object ShoutBlocker {
    var shoutCooldown = 0L
    val decimalFormat = DecimalFormat("#.#")

    fun register() {
        ClientSendMessageEvents.ALLOW_COMMAND.register(::onCommandSend)
        ClientReceiveMessageEvents.GAME.register(::onGameReceive)
    }

    private fun onCommandSend(command: String): Boolean {
        if (!HyChatterConfig.preventShoutingOnCooldown || !command.startsWith("shout ")) return true

        if (shoutCooldown < System.currentTimeMillis()) {
            shoutCooldown = System.currentTimeMillis() + getCooldownLengthInSeconds() * 1000L
            return true
        }

        val secondsLeft = (shoutCooldown - System.currentTimeMillis()) / 1000L
        //? 1.8.9
        //Minecraft.getMinecraft().thePlayer.addChatMessage(createCooldownMessage(secondsLeft))
        //? elif >= 26.2
        Minecraft.getInstance().gui.hud.chat.addClientSystemMessage(createCooldownMessage(secondsLeft))
        //? else
        //Minecraft.getInstance().gui.chat.addClientSystemMessage(createCooldownMessage(secondsLeft))
        return false
    }

    private fun onGameReceive(component: Component, fromActionBar: Boolean) {
        if (fromActionBar || !HyChatterConfig.preventShoutingOnCooldown) return

        val message =
            //? if ornithe {
            //component.unformattedText
        //?} else {
        component.string
        //?}

        val location = HypixelUtils.getLocation()
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

    private fun createCooldownMessage(secondsLeft: Long): Component =
        //? if 1.8.9 {
        /*ChatComponentText("Shout command is on cooldown. Please wait ${decimalFormat.format(secondsLeft)} more second${if (secondsLeft == 1L) "" else "s"} before shouting another message.").setChatStyle(
            ChatStyle().setChatHoverEvent(
                HoverEvent(
                    HoverEvent.Action.SHOW_TEXT,
                    ChatComponentText("")
                        .appendSibling(
                            ChatComponentText("${Constants.MOD_NAME}\n")
                                .setChatStyle(
                                    ChatStyle()
                                        .setColor(EnumChatFormatting.GOLD)
                                        .setBold(true)
                                )
                        )
                        .appendSibling(
                            ChatComponentText("Your message was blocked by the \"Shout Cooldown\" setting. \nPlease wait before shouting another message.").setChatStyle(
                                ChatStyle().setColor(EnumChatFormatting.GRAY)
                            )
                        )
                )
            )
        )
    *///?} else {
    Component.literal("Shout command is on cooldown. Please wait ${decimalFormat.format(secondsLeft)} more second${if (secondsLeft == 1L) "" else "s"} before shouting another message.")
        .setStyle(Style.EMPTY.withHoverEvent(
            HoverEvent.ShowText(
                Component.empty()
                    .append(Component.literal("${Constants.MOD_NAME}\n")
                        .withStyle(ChatFormatting.GOLD, ChatFormatting.BOLD))
                    .append(Component.literal("Your message was blocked by the \"Shout Cooldown\" setting. \nPlease wait before shouting another message.")
                        .withStyle(ChatFormatting.GRAY))
            )
        ))
    //?}
}
