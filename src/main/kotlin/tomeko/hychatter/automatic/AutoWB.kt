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

object AutoWB {
    private var cooldownTicks = -1
    private var pendingCommand: String? = null

    fun register() {
        ClientReceiveMessageEvents.GAME.register(::onGameReceive)
        ClientTickEvents.END_CLIENT_TICK.register(::onClientTick)
    }

    private fun onGameReceive(message: Component, fromActionBar: Boolean) {
        if (fromActionBar || !HyChatterConfig.autoWB || !HypixelPackets.onHypixel) return

        val text =
            //? if 1.8.9 {
            //message.unformattedText
            //?} else {
            message.string
            //?}
        val match = LanguageData.PLAYER_CONNECTION_STATUS.find(text) ?: return
        val status = match.groups["status"]?.value ?: return
        if (status != "joined") return

        val player = match.groups["player"]?.value ?: return
        val type = match.groups["type"]?.value ?: return
        val command = when (type) {
            "Guild" -> if (HyChatterConfig.guildAutoWB) "gc" else return
            "Friend" -> if (HyChatterConfig.friendsAutoWB) "msg $player" else return
            else -> return
        }
        val chatMessage = if (HyChatterConfig.randomAutoWB) getMessage(player)
        else HyChatterConfig.autoWBMessage1.replace("%player%", player)

        pendingCommand = "/$command $chatMessage"
        cooldownTicks = HyChatterConfig.autoWBCooldown.toInt() * 20
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
        val validMessages = HyChatterConfig.wbMessages.filter { it.isNotBlank() }
        val message = if (validMessages.isEmpty()) "Welcome Back!" else validMessages.random()
        return message.replace("%player%", name)
    }
}
