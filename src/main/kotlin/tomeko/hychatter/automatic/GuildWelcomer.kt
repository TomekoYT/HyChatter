package tomeko.hychatter.automatic

//? if 1.8.9 {
/*import net.minecraft.util.IChatComponent as Component
import tomeko.hychatter.event.ClientReceiveMessageEvents
import tomeko.hychatter.event.ClientTickEvents
*///?} else {
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
import net.minecraft.network.chat.Component
//?}

import net.minecraft.client.Minecraft
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.LanguageData
import tomeko.hychatter.utils.HypixelPackets
import kotlin.text.get

object GuildWelcomer {
    private var cooldownTicks = -1
    private var pendingCommand: String? = null

    fun register() {
        ClientReceiveMessageEvents.GAME.register(::onGameReceive)
        ClientTickEvents.END_CLIENT_TICK.register(::onClientTick)
    }

    private fun onGameReceive(message: Component, fromActionBar: Boolean) {
        if (fromActionBar || !HyChatterConfig.guildWelcomeMessage || !HypixelPackets.onHypixel) return

        val text =
        //? if 1.8.9 {
        //message.unformattedText
            //?} else {
            message.string
        //?}

        val match = LanguageData.GUILD_JOIN.find(text) ?: return
        val player = match.groups["player"]?.value ?: return

        val chatMessage = if (HyChatterConfig.randomGuildWelcomeMessage) {
            getMessage(player)
        } else {
            HyChatterConfig.guildWelcomeMessageText.replace("%player%", player)
        }

        pendingCommand = "/gc $chatMessage"
        cooldownTicks = HyChatterConfig.guildWelcomeCooldown.toInt() * 20
    }

    private fun onClientTick(mc: Minecraft) {
        if (cooldownTicks <= 0) return

        cooldownTicks--

        if (cooldownTicks == 0) {
            val command = pendingCommand ?: return

            pendingCommand = null

            //? if 1.8.9 {
            //mc.thePlayer?.sendChatMessage(command)
            //?} else {
            mc.player?.connection?.sendCommand(command.removePrefix("/"))
            //?}
        }
    }

    private fun getMessage(name: String): String {
        val validMessages = HyChatterConfig.guildWelcomeMessages.filter {
            it.isNotBlank()
        }

        val message = if (validMessages.isEmpty()) {
            "Welcome to the guild %player%!"
        } else {
            validMessages.random()
        }

        return message.replace("%player%", name)
    }
}