package tomeko.hychatter.restylers

//? if 1.8.9 {
/*import net.minecraft.util.EnumChatFormatting
import net.minecraft.util.IChatComponent
*///?} else {
import net.minecraft.locale.Language
import net.minecraft.network.chat.Component
import net.minecraft.util.FormattedCharSequence
//?}
import net.minecraft.client.Minecraft
import tomeko.hychatter.config.HyChatterConfig

object LineSeparators {
    //? if 1.8.9 {
    /*@JvmStatic
    fun process(lines: List<IChatComponent>): List<IChatComponent> {
        if (!HyChatterConfig.lineBreakerTrim) return lines
        return trim(lines) { EnumChatFormatting.getTextWithoutFormattingCodes(it.unformattedText) }
    }

    private val cache = HashMap<String, String>()

    @JvmStatic
    fun clean(formattedText: String): String {
        if (!HyChatterConfig.cleanLineSeparator) return formattedText
        cache[formattedText]?.let { return it }
        if (!isSeparator(EnumChatFormatting.getTextWithoutFormattingCodes(formattedText))) return formattedText

        val mc = Minecraft.getMinecraft()
        val processed = mc.fontRendererObj.trimStringToWidth(
            formattedText.replace("▬", "§m  ").replace("≡", "§m  ").replace("-", "§m  "),
            mc.ingameGUI.chatGUI.chatWidth + 6
        )
        if (cache.size >= 10000) cache.clear()
        cache[formattedText] = processed
        return processed
    }
    *///?} else {
    @JvmStatic
    fun process(lines: List<FormattedCharSequence>, maxWidth: Int): List<FormattedCharSequence> {
        val trimmed = if (HyChatterConfig.lineBreakerTrim) trim(lines, ::plain) else lines
        if (!HyChatterConfig.cleanLineSeparator) return trimmed
        return trimmed.map { if (isSeparator(plain(it))) clean(it, maxWidth) else it }
    }

    private fun clean(line: FormattedCharSequence, maxWidth: Int): FormattedCharSequence {
        val text = Component.empty()
        line.accept { _, style, codePoint ->
            if (codePoint == '▬'.code || codePoint == '≡'.code || codePoint == '-'.code) {
                text.append(Component.literal("  ").setStyle(style.withStrikethrough(true)))
            } else {
                text.append(Component.literal(Character.toString(codePoint)).setStyle(style))
            }
            true
        }
        return Language.getInstance().getVisualOrder(Minecraft.getInstance().font.substrByWidth(text, maxWidth + 6))
    }

    private fun plain(line: FormattedCharSequence): String {
        val builder = StringBuilder()
        line.accept { _, _, codePoint ->
            builder.appendCodePoint(codePoint)
            true
        }
        return builder.toString()
    }
    //?}

    private fun <T> trim(lines: List<T>, text: (T) -> String): List<T> {
        val result = lines.toMutableList()
        val iterator = result.listIterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            if (iterator.hasNext() && isSeparator(text(next))) {
                while (iterator.hasNext()) {
                    if (isSeparator(text(iterator.next()))) iterator.remove() else break
                }
            }
        }
        return result
    }

    private fun isSeparator(s: String): Boolean =
        (s.startsWith("-") && s.endsWith("-")) || (s.startsWith("▬") && s.endsWith("▬")) || (s.startsWith("≡") && s.endsWith("≡"))
}
