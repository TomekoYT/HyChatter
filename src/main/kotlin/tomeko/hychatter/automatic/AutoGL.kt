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

object AutoGL {
    fun register() {
        ClientReceiveMessageEvents.GAME.register(::onGameReceive)
    }

    private fun onGameReceive(message: Component, fromActionBar: Boolean) {
        if (fromActionBar || !HyChatterConfig.autoGL || !HypixelPackets.onHypixel) return

        val text =
            //? if 1.8.9 {
            //message.unformattedText
            //?} else {
            message.string
            //?}
        val trimmed = text.trim()
        if (!trimmed.contains(": ") && trimmed.endsWith("The game starts in 5 seconds!")) {
            //? if 1.8.9
            //Minecraft.getMinecraft().thePlayer?.sendChatMessage("/ac ${HyChatterConfig.autoGLMessage}") ?: return
            //? else
            Minecraft.getInstance().player?.connection?.sendCommand("ac ${HyChatterConfig.autoGLMessage}") ?: return
        }
    }
}
