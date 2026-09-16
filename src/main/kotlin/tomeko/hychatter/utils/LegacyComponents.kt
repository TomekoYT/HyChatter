package tomeko.hychatter.utils

//? if 1.8.9 {
/*import tomeko.hychatter.utils.string
import tomeko.hychatter.utils.siblings
import tomeko.hychatter.utils.style
import tomeko.hychatter.utils.plainCopy
import tomeko.hychatter.utils.withStyle
import tomeko.hychatter.utils.append
import tomeko.hychatter.utils.copy
import net.minecraft.util.ChatComponentText
import net.minecraft.util.ChatStyle
import net.minecraft.util.EnumChatFormatting
import net.minecraft.util.IChatComponent

object LegacyComponents {
    fun literal(text: String): IChatComponent = ChatComponentText(text)
    fun empty(): IChatComponent = ChatComponentText("")
}

val IChatComponent.string: String
    get() = getUnformattedText()

val IChatComponent.siblings: MutableList<IChatComponent>
    @Suppress("UNCHECKED_CAST")
    get() = getSiblings() as MutableList<IChatComponent>

val IChatComponent.style: ChatStyle
    get() = getChatStyle()

fun IChatComponent.copy(): IChatComponent = createCopy()

fun IChatComponent.plainCopy(): IChatComponent =
    ChatComponentText(getUnformattedText()).also { it.setChatStyle(getChatStyle().createShallowCopy()) }

fun IChatComponent.withStyle(vararg formatting: EnumChatFormatting): IChatComponent {
    val style = getChatStyle().createShallowCopy()
    for (format in formatting) {
        when {
            format.isColor -> style.setColor(format)
            format == EnumChatFormatting.BOLD -> style.setBold(true)
            format == EnumChatFormatting.ITALIC -> style.setItalic(true)
            format == EnumChatFormatting.UNDERLINE -> style.setUnderlined(true)
            format == EnumChatFormatting.STRIKETHROUGH -> style.setStrikethrough(true)
            format == EnumChatFormatting.OBFUSCATED -> style.setObfuscated(true)
        }
    }
    setChatStyle(style)
    return this
}

fun IChatComponent.withStyle(style: ChatStyle): IChatComponent {
    setChatStyle(style)
    return this
}

fun IChatComponent.append(component: IChatComponent): IChatComponent {
    appendSibling(component)
    return this
}

fun IChatComponent.append(text: String): IChatComponent {
    appendText(text)
    return this
}
*///?}