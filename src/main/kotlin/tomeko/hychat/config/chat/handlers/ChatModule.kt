package tomeko.hychat.config.chat.handlers

/**
 * implement [ChatReceiveModule] or [ChatSendModule] rather than this directly
 */
interface ChatModule {
    /**
     * override to gate the module on a [HyChatConfig] value
     */
    val isEnabled: Boolean
        get() = true

    /**
     * lower runs earlier so removers want a negative value and expensive work wants a higher one
     */
    val priority: Int
        get() = 0
}
