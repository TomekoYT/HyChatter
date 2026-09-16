package tomeko.hychatter.config.chat.handlers

import tomeko.hychatter.config.events.ChatSendEvent

/**
 * must be registered in [ChatHandler] to run
 */
interface ChatSendModule : ChatModule {
    /**
     * cancelling stops later modules so [ChatSendEvent.cancelled] checks are unnecessary here
     */
    fun onChatSend(event: ChatSendEvent)
}
