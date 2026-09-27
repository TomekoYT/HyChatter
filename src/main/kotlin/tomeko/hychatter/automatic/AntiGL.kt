package tomeko.hychatter.automatic

//? if 1.8.9 {
/*import net.minecraft.client.Minecraft
import net.minecraft.util.IChatComponent as Component
*///?} else {
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.Component
//?}
//? if 1.8.9 {
//import tomeko.hychatter.event.ClientReceiveMessageEvents
//?}
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.LanguageData
import tomeko.hychatter.utils.HypixelPackets

object AntiGL {
    fun register() {
        ClientReceiveMessageEvents.ALLOW_GAME.register(::onGameReceive)
    }

    private fun onGameReceive(component: Component, fromActionBar: Boolean): Boolean {
        if (fromActionBar || !HyChatterConfig.antiGL || !HypixelPackets.onHypixel) return true

        val message =
            //? if 1.8.9 {
            //component.unformattedText
        //?} else {
        component.string
        //?}

        return ((message.startsWith("-") && message.endsWith("-"))
                || (message.startsWith("▬") && message.endsWith("▬"))
                || (message.startsWith("≡") && message.endsWith("≡"))
                || !message.contains(": ")
                || message.contains(
                //? if 1.8.9
            //Minecraft.getMinecraft().thePlayer.name,
                //? else
                Minecraft.getInstance().player?.name?.string ?: return true,
            true
        )) || !message.contains(LanguageData.GL_MESSAGES)
    }
}
