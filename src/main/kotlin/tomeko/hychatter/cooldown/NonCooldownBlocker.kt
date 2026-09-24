package tomeko.hychatter.cooldown

//? if 1.8.9 {
/*import net.hypixel.data.rank.PackageRank
import net.minecraft.client.Minecraft
import net.minecraft.event.HoverEvent
import net.minecraft.util.ChatComponentText
import net.minecraft.util.EnumChatFormatting
import net.minecraft.util.ChatStyle
import net.minecraft.util.IChatComponent as Component
*///?} else {
import net.hypixel.data.rank.PackageRank
import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.HoverEvent
import net.minecraft.network.chat.Style
//?}
//? if ornithe {
//import tomeko.hychatter.event.ClientSendMessageEvents
//?} else {
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents
//?}
import org.polyfrost.oneconfig.api.hypixel.v1.HypixelUtils
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.utils.Constants
import java.text.DecimalFormat

object NonCooldownBlocker {
    private var lastSent = 0L
    private val decimalFormat = DecimalFormat("#.#")

    private const val COOLDOWN_SECONDS = 3L

    fun register() {
        ClientSendMessageEvents.ALLOW_CHAT.register(::onChatSend)
    }

    private fun onChatSend(message: String): Boolean {
        if (!HyChatterConfig.preventNonCooldown) return true

        val rank = HypixelUtils.getPlayerInfo().packageRank
        if (rank.isPresent && rank.get() != PackageRank.NONE) return true

        val now = System.currentTimeMillis()
        if (lastSent < now) {
            lastSent = now + COOLDOWN_SECONDS * 1000L
            return true
        }

        val secondsLeft = (lastSent - now) / 1000L
        //? 1.8.9
        //Minecraft.getMinecraft().thePlayer.addChatMessage(createCooldownMessage(secondsLeft))
        //? elif >= 26.2
        Minecraft.getInstance().gui.hud.chat.addClientSystemMessage(createCooldownMessage(secondsLeft))
        //? else
        //Minecraft.getInstance().gui.chat.addClientSystemMessage(createCooldownMessage(secondsLeft))
        return false
    }

    private fun createCooldownMessage(secondsLeft: Long): Component =
        //? if 1.8.9 {
        /*ChatComponentText("Your freedom of speech is on cooldown. Please wait ${decimalFormat.format(secondsLeft)} more second${if (secondsLeft == 1L) "" else "s"} before sending another message.").setChatStyle(
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
                            ChatComponentText("Your message was blocked by the \"Non Speech Cooldown\" setting. \nPlease wait before sending another message.").setChatStyle(
                                ChatStyle().setColor(EnumChatFormatting.GRAY)
                            )
                        )
                )
            )
        )
    *///?} else {
    Component.literal("Your freedom of speech is on cooldown. Please wait ${decimalFormat.format(secondsLeft)} more second${if (secondsLeft == 1L) "" else "s"} before sending another message.")
        .setStyle(Style.EMPTY.withHoverEvent(
            HoverEvent.ShowText(
                Component.empty()
                    .append(Component.literal("${Constants.MOD_NAME}\n")
                        .withStyle(ChatFormatting.GOLD, ChatFormatting.BOLD))
                    .append(Component.literal("Your message was blocked by the \"Non Speech Cooldown\" setting. \nPlease wait before sending another message.")
                        .withStyle(ChatFormatting.GRAY))
            )
        ))
    //?}
}
