package tomeko.hychatter.config.events

//? if 1.8.9 {
/*import net.minecraft.util.EnumChatFormatting
import net.minecraft.util.IChatComponent
import org.polyfrost.oneconfig.api.event.v1.events.Event
import tomeko.hychatter.utils.string

data class ChatSendEvent(var message: String) : Event.Cancellable()

data class ChatReceiveEvent(var message: IChatComponent, var isOverlay: Boolean) : Event.Cancellable() {
    @Suppress("PROPERTY_HIDES_JAVA_FIELD")
    var cancelled: Boolean
        get() = super.cancelled
        set(value) { super.cancelled = value }

    val plainMessage: String
        get() = message.string

    val unformattedMessage: String
        get() = EnumChatFormatting.getTextWithoutFormattingCodes(plainMessage) ?: plainMessage
}

*///?} else {
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
//?}