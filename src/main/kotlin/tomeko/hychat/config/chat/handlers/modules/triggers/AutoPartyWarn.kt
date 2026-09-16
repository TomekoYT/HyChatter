package tomeko.hychat.config.chat.handlers.modules.triggers

import tomeko.hychat.config.HyChatConfig
import tomeko.hychat.config.chat.handlers.ChatReceiveModule
import tomeko.hychat.config.events.ChatReceiveEvent
import tomeko.hychat.utils.ChatUtils

object AutoPartyWarn : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        if (event.unformattedMessage.startsWith("A kick")) {
            val message = if (HyChatConfig.notifyWhenKickInCaps) {
                "REQUEUE, I'VE BEEN KICKED!"
            } else {
                "I've been kicked, please requeue!"
            }
            val separator = "-".repeat(9)

            ChatUtils.queueMessage("/pc $separator$message$separator")
        }
    }

    override val isEnabled
        get() = HyChatConfig.notifyWhenKick
}
