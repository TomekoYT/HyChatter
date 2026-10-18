package tomeko.hychatter.automatic

//? if 1.8.9 {
/*import net.minecraft.util.IChatComponent as Component
import tomeko.hychatter.event.ClientReceiveMessageEvents
*///?} else {
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.network.chat.TextColor
import net.minecraft.sounds.SoundEvents
import java.util.Optional
//?}
import net.minecraft.client.Minecraft
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.utils.HypixelPackets

object LimboPmDing {
    fun register() {
        ClientReceiveMessageEvents.GAME.register(::onGameReceive)
    }

    private fun onGameReceive(message: Component, fromActionBar: Boolean) {
        if (fromActionBar || !HyChatterConfig.limboDing || !HypixelPackets.onHypixel || !HypixelPackets.inLimbo) return

        //? if 1.8.9 {
        /*if (message.formattedText.startsWith("§dFrom §r")) {
            Minecraft.getMinecraft().thePlayer?.playSound("random.orb", 1f, 1f)
        }
        *///?} else {
        val isPrivateMessage = message.visit({ style, text ->
            if (text.isEmpty()) Optional.empty()
            else Optional.of(text.startsWith("From ") && style.color == TextColor.fromLegacyFormat(ChatFormatting.LIGHT_PURPLE))
        }, Style.EMPTY).orElse(false)
        if (isPrivateMessage) {
            Minecraft.getInstance().player?.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 1f, 1f)
        }
        //?}
    }
}
