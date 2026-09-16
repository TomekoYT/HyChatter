package tomeko.hychat.config.chat.enhancements.core

//? if 1.8.9 {
/*import net.minecraft.util.ChatStyle
import net.minecraft.util.IChatComponent
import tomeko.hychat.utils.LegacyComponents
import tomeko.hychat.utils.style
import tomeko.hychat.utils.string

class ChatTextBuilder {
    private val texts = mutableListOf<IChatComponent>()
    private var currentStyle: ChatStyle? = null
    private val builder = StringBuilder()

    fun append(component: IChatComponent) {
        for (part in component) {
            val style = part.style
            val text = part.string
            if (currentStyle == null) currentStyle = style
            if (currentStyle != style) {
                flush()
                currentStyle = style
            }
            if (text.contains('\n')) {
                val pieces = text.split('\n')
                pieces.forEachIndexed { i, piece ->
                    builder.append(piece)
                    if (i != pieces.lastIndex) {
                        flush()
                        texts.add(LegacyComponents.empty())
                    }
                }
            } else {
                builder.append(text)
            }
        }
        flush()
    }

    private fun flush() {
        if (builder.isNotEmpty()) {
            val component = LegacyComponents.literal(builder.toString())
            component.setChatStyle((currentStyle ?: ChatStyle()).createShallowCopy())
            texts.add(component)
            builder.clear()
        }
    }

    fun getTexts(): List<IChatComponent> {
        flush()
        return texts
    }
}
*///?} else {
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.chat.Style
import net.minecraft.util.FormattedCharSink
import java.lang.StringBuilder

class ChatTextBuilder : FormattedCharSink {
    private val texts = mutableListOf(Component.empty())
    private var currentStyle: Style? = null
    private val builder = StringBuilder()
    private var currentIndex = -1

    override fun accept(index: Int, style: Style, codePoint: Int): Boolean {
        currentIndex++

        if (currentStyle == null) currentStyle = style

        if (currentStyle != style) {
            flush()
            currentStyle = style
        }

        if (codePoint == '\n'.code) {
            flush()
            texts.add(Component.empty())
            return true
        }
        builder.appendCodePoint(codePoint)
        return true
    }

    private fun flush() {
        if (builder.isNotEmpty()) {
            texts.last().append(Component.literal(builder.toString()).setStyle(currentStyle ?: Style.EMPTY))
            builder.clear()
        }
    }

    fun getTexts(): List<MutableComponent> {
        flush()
        return texts
    }
}
//?}