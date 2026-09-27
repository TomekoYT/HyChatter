package tomeko.hychatter.automatic

//? if 1.8.9 {
/*import net.minecraft.client.Minecraft
import net.minecraft.util.IChatComponent as Component
import tomeko.hychatter.event.ClientReceiveMessageEvents
import tomeko.hychatter.event.ClientTickEvents
*///?} else {
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.Component
//?}
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.LanguageData
import tomeko.hychatter.utils.HypixelPackets

object AutoGG {
    private var gameEnded = false
    private var shouldSend = false
    private var previousServerName: String? = null

    private var firstMessageTicks = -1
    private var secondMessageTicks = -1
    private var resetTicks = -1

    fun register() {
        ClientReceiveMessageEvents.GAME.register(::onGameReceive)
        ClientTickEvents.END_CLIENT_TICK.register(::onClientTick)
        ClientTickEvents.END_CLIENT_TICK.register(::onWorldUnload)
    }

    private fun onGameReceive(message: Component, fromActionBar: Boolean) {
        if (fromActionBar || !HyChatterConfig.autoGG || !HypixelPackets.onHypixel || !hasGameEnded(
                //? if 1.8.9 {
                //message.unformattedText
                //?} else {
                message.string
                //?}
            )
        ) return

        shouldSend = true

        firstMessageTicks = HyChatterConfig.autoGGFirstMsgDelay.toInt() * 20

        secondMessageTicks =
            if (HyChatterConfig.autoGGSendSecondMessage)
                (HyChatterConfig.autoGGFirstMsgDelay + HyChatterConfig.autoGGSecondMsgDelay).toInt() * 20
            else
                -1

        resetTicks = (HyChatterConfig.autoGGFirstMsgDelay + HyChatterConfig.autoGGSecondMsgDelay + 5).toInt() * 20
    }

    private fun onClientTick(mc: Minecraft) {
        if (!shouldSend) return

        if (firstMessageTicks > 0) {
            firstMessageTicks--
        }

        if (firstMessageTicks == 0) {
            firstMessageTicks = -1

            //? if 1.8.9
            //mc.thePlayer?.sendChatMessage("/ac ${HyChatterConfig.autoGGMessage}")
            //? else
            mc.player?.connection?.sendCommand("ac ${HyChatterConfig.autoGGMessage}")
        }

        if (secondMessageTicks > 0) {
            secondMessageTicks--
        }

        if (secondMessageTicks == 0) {
            secondMessageTicks = -1

            if (shouldSend) {
                //? if 1.8.9
                //mc.thePlayer?.sendChatMessage("/ac ${HyChatterConfig.autoGGSecondMessage}")
                //? else
                mc.player?.connection?.sendCommand("ac ${HyChatterConfig.autoGGSecondMessage}")
            }
        }

        if (resetTicks > 0) {
            resetTicks--
        }

        if (resetTicks == 0) {
            resetTicks = -1
            gameEnded = false
            shouldSend = false
        }
    }

    fun onWorldUnload(mc: Minecraft) {
        if (previousServerName == HypixelPackets.currentServerName) return

        previousServerName = HypixelPackets.currentServerName
        gameEnded = false
        shouldSend = false
    }

    private fun hasGameEnded(message: String): Boolean {
        if (!gameEnded && message.matches(LanguageData.GAME_END)) {
            gameEnded = true
            return true
        }

        return HyChatterConfig.casualAutoGG && LanguageData.CASUAL_GAME_END.matches(message)
    }
}
