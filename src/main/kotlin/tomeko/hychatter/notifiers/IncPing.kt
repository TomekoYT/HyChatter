package tomeko.hychatter.notifiers

//? if 1.8.9 {
/*import net.minecraft.util.IChatComponent as Component
import tomeko.hychatter.event.ClientReceiveMessageEvents
*///?} else {
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
import net.minecraft.sounds.SoundEvents
import net.minecraft.network.chat.Component
//?}
import net.minecraft.client.Minecraft
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.utils.HypixelPackets

object IncPing {
    private val incPattern = Regex("""\binc\b""", RegexOption.IGNORE_CASE)

    fun register() {
        ClientReceiveMessageEvents.GAME.register(::onGameReceive)
    }

    private fun onGameReceive(message: Component, fromActionBar: Boolean) {
        if (fromActionBar || !HyChatterConfig.incDing || !HypixelPackets.onHypixel) return

        val text =
        //? if 1.8.9 {
                /*message.unformattedText*/
            //?} else {
            message.string
        //?}

        if (!incPattern.containsMatchIn(text)) return

        //? if 1.8.9 {
        /*Minecraft.getMinecraft().thePlayer?.playSound("random.orb", 1f, 1f)
        *///?} else {
        Minecraft.getInstance().player?.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 1f, 1f)
        //?}
    }
}
