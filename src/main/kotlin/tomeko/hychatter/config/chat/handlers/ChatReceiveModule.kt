package tomeko.hychatter.config.chat.handlers

import tomeko.hychatter.config.events.ChatReceiveEvent

/**
 * must be registered in [ChatHandler] to run
 */
interface ChatReceiveModule : ChatModule {
    /**
     * cancelling stops later modules so [ChatReceiveEvent.cancelled] checks are unnecessary here
     */
    fun onChatReceived(event: ChatReceiveEvent)
}
