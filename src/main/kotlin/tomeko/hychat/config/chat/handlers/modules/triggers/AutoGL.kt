package tomeko.hychat.config.chat.handlers.modules.triggers

import tomeko.hychat.config.HyChatConfig
import tomeko.hychat.config.chat.handlers.ChatReceiveModule
import tomeko.hychat.config.events.ChatReceiveEvent
import tomeko.hychat.utils.ChatUtils

object AutoGL : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        val message = event.unformattedMessage.trim()
        if (!message.contains(": ") && message.endsWith("The game starts in 5 seconds!")) {
            ChatUtils.queueMessage("/ac ${HyChatConfig.autoGLMessage}")
        }
    }

    override val isEnabled
        get() = HyChatConfig.autoGL
}
