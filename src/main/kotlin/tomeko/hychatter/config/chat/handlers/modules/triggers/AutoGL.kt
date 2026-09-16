package tomeko.hychatter.config.chat.handlers.modules.triggers

import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.chat.handlers.ChatReceiveModule
import tomeko.hychatter.config.events.ChatReceiveEvent
import tomeko.hychatter.utils.ChatUtils

object AutoGL : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        val message = event.unformattedMessage.trim()
        if (!message.contains(": ") && message.endsWith("The game starts in 5 seconds!")) {
            ChatUtils.queueMessage("/ac ${HyChatterConfig.autoGLMessage}")
        }
    }

    override val isEnabled
        get() = HyChatterConfig.autoGL
}
