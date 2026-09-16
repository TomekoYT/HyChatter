package tomeko.hychat.config.chat.handlers

import tomeko.hychat.config.events.ChatReceiveEvent

/**
 * must be registered in [ChatHandler] to run
 */
interface ChatReceiveModule : ChatModule {
    /**
     * cancelling stops later modules so [ChatReceiveEvent.cancelled] checks are unnecessary here
     */
    fun onChatReceived(event: ChatReceiveEvent)
}
