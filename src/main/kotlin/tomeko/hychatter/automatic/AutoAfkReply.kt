package tomeko.hychatter.automatic

//? if 1.8.9 {
/*import net.minecraft.util.IChatComponent as Component
import tomeko.hychatter.event.ClientReceiveMessageEvents
*///?} else {
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
import net.minecraft.network.chat.Component
//?}
import net.minecraft.client.Minecraft
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.LanguageData
import tomeko.hychatter.utils.HypixelPackets

object AutoAfkReply {
    fun register() {
        ClientReceiveMessageEvents.GAME.register(::onGameReceive)
    }

    private fun onGameReceive(message: Component, fromActionBar: Boolean) {
        if (fromActionBar || !HyChatterConfig.autoReplyAfk || !HypixelPackets.onHypixel || !HypixelPackets.inLimbo) return

        val text =
            //? if 1.8.9
            //message.unformattedText
            //? else
            message.string
        val player = LanguageData.AUTO_AFK_REPLY.matchEntire(text)?.groupValues?.get(2) ?: return

        //? if 1.8.9
        //Minecraft.getMinecraft().thePlayer?.sendChatMessage("/msg $player Hey $player, I am currently AFK!")
        //? else
        Minecraft.getInstance().player?.connection?.sendCommand("msg $player Hey $player, I am currently AFK!")
    }
}
