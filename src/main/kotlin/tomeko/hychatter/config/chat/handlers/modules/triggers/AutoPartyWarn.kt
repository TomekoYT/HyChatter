package tomeko.hychatter.config.chat.handlers.modules.triggers

import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.chat.handlers.ChatReceiveModule
import tomeko.hychatter.config.events.ChatReceiveEvent
import tomeko.hychatter.utils.ChatUtils

object AutoPartyWarn : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        if (event.unformattedMessage.startsWith("A kick")) {
            val message = if (HyChatterConfig.notifyWhenKickInCaps) {
                "REQUEUE, I'VE BEEN KICKED!"
            } else {
                "I've been kicked, please requeue!"
            }
            val separator = "-".repeat(9)

            ChatUtils.queueMessage("/pc $separator$message$separator")
        }
    }

    override val isEnabled
        get() = HyChatterConfig.notifyWhenKick
}
