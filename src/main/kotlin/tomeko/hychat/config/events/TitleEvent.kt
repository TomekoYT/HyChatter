package tomeko.hychat.config.events

//? if 1.8.9 {
/*import net.minecraft.util.EnumChatFormatting
import net.minecraft.util.IChatComponent
import org.polyfrost.oneconfig.api.event.v1.events.Event
import tomeko.hychat.utils.string

data class TitleEvent(val title: IChatComponent, val subtitle: IChatComponent?) : Event.Cancellable() {
    val plainTitle: String get() = title.string
    val unformattedTitle: String get() = EnumChatFormatting.getTextWithoutFormattingCodes(plainTitle) ?: plainTitle
    val plainSubtitle: String? get() = subtitle?.string
    val unformattedSubtitle: String? get() =
        subtitle?.string?.let { EnumChatFormatting.getTextWithoutFormattingCodes(it) ?: it }
}
*///?} else {
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import org.polyfrost.oneconfig.api.event.v1.events.Event

data class TitleEvent(val title: Component, val subtitle: Component?) : Event.Cancellable() {
    val plainTitle: String
        get() = this.title.string

    val unformattedTitle: String
        get() = ChatFormatting.stripFormatting(this.plainTitle)!!

    val plainSubtitle: String?
        get() = this.subtitle?.string

    val unformattedSubtitle: String?
        get() = ChatFormatting.stripFormatting(this.plainSubtitle)
}
//?}