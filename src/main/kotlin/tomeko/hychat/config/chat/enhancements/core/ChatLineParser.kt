package tomeko.hychat.config.chat.enhancements.core

//? if 1.8.9 {
/*import net.minecraft.client.gui.FontRenderer
import net.minecraft.util.IChatComponent

interface ChatLineParser {
    val isEnabled: Boolean
    fun parse(text: IChatComponent, raw: String, trimmed: String, chatWidth: Int, font: FontRenderer): List<CustomChatLine>?
}
*///?} else {
import net.minecraft.client.gui.Font
import net.minecraft.network.chat.Component

interface ChatLineParser {
    val isEnabled: Boolean

    fun parse(text: Component, raw: String, trimmed: String, chatWidth: Int, font: Font): List<CustomChatLine>?
}
//?}