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

object AutoPartyWarpConfirm {
    private var warpTicks = -1

    fun register() {
        ClientReceiveMessageEvents.ALLOW_GAME.register(::onGameReceive)
        ClientTickEvents.END_CLIENT_TICK.register(::onClientTick)
    }

    private fun onGameReceive(message: Component, fromActionBar: Boolean): Boolean {
        if (fromActionBar || !HyChatterConfig.autoPartyWarpConfirm || !HypixelPackets.onHypixel) return true

        val text =
            //? if 1.8.9 {
            //message.unformattedText
            //?} else {
            message.string
            //?}
        if (text != LanguageData.PARTY_CONFIRM_WARP) return true

        warpTicks = 30
        return false
    }

    private fun onClientTick(mc: Minecraft) {
        if (warpTicks <= 0) return

        warpTicks--

        if (warpTicks == 0) {
            //? if 1.8.9 {
            //mc.thePlayer?.sendChatMessage("/p warp")
            //?} else {
            mc.player?.connection?.sendCommand("p warp")
            //?}
        }
    }
}
