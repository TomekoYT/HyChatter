package tomeko.hychatter.cooldown

//? if forge {
/*import cc.polyfrost.oneconfig.utils.hypixel.HypixelUtils
import cc.polyfrost.oneconfig.utils.JsonUtils
*///?}
//? if 1.8.9 {
/*import net.hypixel.data.rank.PackageRank
import net.minecraft.client.Minecraft
import net.minecraft.event.HoverEvent
import net.minecraft.util.ChatComponentText
import net.minecraft.util.EnumChatFormatting
import net.minecraft.util.ChatStyle
import net.minecraft.util.IChatComponent as Component
import tomeko.hychatter.event.ClientSendMessageEvents

import com.google.gson.JsonObject
import com.mojang.authlib.GameProfile
import com.mojang.authlib.exceptions.AuthenticationException
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.UUID
*///?} else {
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents
import net.hypixel.data.rank.PackageRank
import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.HoverEvent
import net.minecraft.network.chat.Style
//?}
//? if !forge
import org.polyfrost.oneconfig.api.hypixel.v1.HypixelUtils
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.utils.Constants
import tomeko.hychatter.utils.HypixelPackets
import java.text.DecimalFormat

object NonCooldownBlocker {
    private var lastSent = 0L
    private val decimalFormat = DecimalFormat("#.#")

    private const val COOLDOWN_SECONDS = 3L

    fun register() {
        ClientSendMessageEvents.ALLOW_CHAT.register(::onChatSend)
    }

    private fun onChatSend(message: String): Boolean {
        if (!HyChatterConfig.preventNonCooldown || !HypixelPackets.onHypixel || !isNon()) return true

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

    private fun isNon(): Boolean {
        //? if forge {
        /*val mc = Minecraft.getMinecraft()
        val player = mc.thePlayer ?: return false

        var connection: HttpURLConnection? = null

        return try {
            val uuid = player.uniqueID.toString().replace("-", "")
            val username = player.gameProfile.name

            connection = URL("https://api.polyfrost.cc/ursa/v1/hypixel/player/$uuid").openConnection() as HttpURLConnection

            connection.requestMethod = "GET"
            connection.useCaches = false
            connection.connectTimeout = 5000
            connection.readTimeout = 5000
            connection.doOutput = true

            connection.addRequestProperty(
                "User-Agent",
                "Hytils-Reborn"
            )

            connection.addRequestProperty(
                "x-ursa-username",
                username
            )

            connection.addRequestProperty(
                "x-ursa-serverid",
                uuid
            )

            val response: JsonObject

            connection.inputStream.reader(StandardCharsets.UTF_8).use { reader ->
                val element = JsonUtils.parseString(reader.readText())

                if (element == null || !element.isJsonObject) {
                    return false
                }

                response = element.asJsonObject
            }

            if (!response.has("player")) {
                return false
            }

            val playerData = response.getAsJsonObject("player")

            val rankValues = arrayOf(
                "rank",
                "monthlyPackageRank",
                "newPackageRank",
                "packageRank"
            )

            for (rank in rankValues) {
                if (!playerData.has(rank)) {
                    continue
                }

                val value = playerData.get(rank).asString

                if (value != "NONE" && value != "NORMAL") {
                    return false
                }
            }

            true
        } catch (_: Exception) {
            false
        } finally {
            connection?.disconnect()
        }
        *///?} else {
        val rank = HypixelUtils.getPlayerInfo().packageRank
        return (rank.isPresent && rank.get() == PackageRank.NONE)
        //?}
    }
}
