package tomeko.hychat.config.events

import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import org.polyfrost.oneconfig.api.event.v1.events.Event

data class ChatSendEvent(var message: String) : Event.Cancellable()

data class ChatReceiveEvent(var message: Component, var isOverlay: Boolean) : Event.Cancellable() {
    @Suppress("PROPERTY_HIDES_JAVA_FIELD")
    var cancelled: Boolean
        get() = super.cancelled
        set(value) {
            super.cancelled = value
        }

    val plainMessage: String
        get() = this.message.string

    val unformattedMessage: String
        get() = ChatFormatting.stripFormatting(this.plainMessage)!!
}
