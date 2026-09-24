package tomeko.hychatter.automatic

import org.polyfrost.oneconfig.api.event.v1.EventManager
import org.polyfrost.oneconfig.api.event.v1.events.WorldEvent
import org.polyfrost.oneconfig.api.event.v1.invoke.impl.Subscribe
import org.polyfrost.oneconfig.utils.v1.Multithreading
//? if ornithe {
//import net.minecraft.util.IChatComponent as Component
//?} else {
import net.minecraft.network.chat.Component
//?}
//? if ornithe {
//import tomeko.hychatter.event.ClientReceiveMessageEvents
//?} else {
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
//?}
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.LanguageData
import tomeko.hychatter.utils.ChatUtils
import java.util.concurrent.TimeUnit

object AutoGG {
    private var gameEnded = false
    private var shouldSend = false

    fun register() {
        ClientReceiveMessageEvents.GAME.register(::onGameReceive)
        EventManager.INSTANCE.register(this)
    }

    private fun onGameReceive(message: Component, fromActionBar: Boolean) {
        if (fromActionBar || !HyChatterConfig.autoGG || !hasGameEnded(
                //? if 1.8.9 {
                //message.unformattedText
                //?} else {
                message.string
                //?}
            )
        ) return

        shouldSend = true

        Multithreading.schedule(
            { if (shouldSend) ChatUtils.queueMessage("/ac ${HyChatterConfig.autoGGMessage}") },
            HyChatterConfig.autoGGFirstMsgDelay.toLong(), TimeUnit.SECONDS
        )
        if (HyChatterConfig.autoGGSendSecondMessage) {
            Multithreading.schedule(
                { if (shouldSend) ChatUtils.queueMessage("/ac ${HyChatterConfig.autoGGSecondMessage}") },
                (HyChatterConfig.autoGGFirstMsgDelay + HyChatterConfig.autoGGSecondMsgDelay).toLong(),
                TimeUnit.SECONDS
            )
        }

        Multithreading.schedule(
            { gameEnded = false; shouldSend = false },
            (HyChatterConfig.autoGGFirstMsgDelay + HyChatterConfig.autoGGSecondMsgDelay + 5).toLong(),
            TimeUnit.SECONDS
        )
    }

    @Subscribe
    fun onWorldUnload(event: WorldEvent.Unload) {
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
