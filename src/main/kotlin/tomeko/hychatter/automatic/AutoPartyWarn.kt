package tomeko.hychatter.automatic

//? if 1.8.9 {
//import net.minecraft.util.IChatComponent as Component
//?} else {
import net.minecraft.network.chat.Component
//?}
//? if ornithe {
//import tomeko.hychatter.event.ClientReceiveMessageEvents
//?} else {
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
//?}
import net.minecraft.client.Minecraft
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.utils.HypixelPackets

object AutoPartyWarn {
    fun register() {
        ClientReceiveMessageEvents.GAME.register(::onGameReceive)
    }

    private fun onGameReceive(message: Component, fromActionBar: Boolean) {
        if (fromActionBar || !HyChatterConfig.notifyWhenKick || !HypixelPackets.onHypixel) return

        val text =
            //? if 1.8.9 {
            //message.unformattedText
            //?} else {
            message.string
            //?}
        if (text.startsWith("A kick")) {
            val warning = if (HyChatterConfig.notifyWhenKickInCaps) {
                "REQUEUE, I'VE BEEN KICKED!"
            } else {
                "I've been kicked, please requeue!"
            }
            val separator = "-".repeat(9)
            //? if 1.8.9
            //Minecraft.getMinecraft().thePlayer?.sendChatMessage("/pc $separator$warning$separator") ?: return
            //? else
            Minecraft.getInstance().player?.connection?.sendCommand("pc $separator$warning$separator") ?: return
        }
    }
}
