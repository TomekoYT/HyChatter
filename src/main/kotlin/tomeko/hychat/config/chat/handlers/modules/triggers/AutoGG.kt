package tomeko.hychat.config.chat.handlers.modules.triggers

import org.polyfrost.oneconfig.api.event.v1.events.WorldEvent
import org.polyfrost.oneconfig.api.event.v1.invoke.impl.Subscribe
import org.polyfrost.oneconfig.utils.v1.Multithreading
import tomeko.hychat.config.HyChatConfig
import tomeko.hychat.config.chat.handlers.ChatReceiveModule
import tomeko.hychat.config.data.providers.LanguageData
import tomeko.hychat.config.events.ChatReceiveEvent
import tomeko.hychat.utils.ChatUtils
import java.util.concurrent.TimeUnit

object AutoGG : ChatReceiveModule {
    private var gameEnded = false
    private var shouldSend = false

    override fun onChatReceived(event: ChatReceiveEvent) {
        if (!hasGameEnded(event.unformattedMessage)) return

        shouldSend = true

        Multithreading.schedule(
            { if (shouldSend) ChatUtils.queueMessage("/ac ${HyChatConfig.autoGGMessage}") },
            HyChatConfig.autoGGFirstMsgDelay.toLong(), TimeUnit.SECONDS
        )
        if (HyChatConfig.autoGGSendSecondMessage) {
            Multithreading.schedule(
                { if (shouldSend) ChatUtils.queueMessage("/ac ${HyChatConfig.autoGGSecondMessage}") },
                (HyChatConfig.autoGGFirstMsgDelay + HyChatConfig.autoGGSecondMsgDelay).toLong(),
                TimeUnit.SECONDS
            )
        }

        Multithreading.schedule(
            { gameEnded = false; shouldSend = false },
            (HyChatConfig.autoGGFirstMsgDelay + HyChatConfig.autoGGSecondMsgDelay + 5).toLong(),
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

        return HyChatConfig.casualAutoGG && LanguageData.CASUAL_GAME_END.matches(message)
    }

    override val isEnabled
        get() = HyChatConfig.autoGG

    // this should be one of the first modules to run
    override val priority = -3
}
