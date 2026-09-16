package tomeko.hychat.config.chat.enhancements.core

//? if 1.8.9 {
/*import net.minecraft.client.gui.FontRenderer
import net.minecraft.client.gui.Gui
import net.minecraft.util.IChatComponent

class ChatGraphics(private val font: FontRenderer) {
    var hovered = false

    fun drawString(text: String, x: Int, y: Int, alpha: Float) {
        val a = (alpha.coerceIn(0f, 1f) * 255f).toInt()
        font.drawStringWithShadow(text, x.toFloat(), y.toFloat(), (a shl 24) or 0xFFFFFF)
    }

    fun drawString(component: IChatComponent, x: Int, y: Int, alpha: Float) {
        drawString(component.formattedText, x, y, alpha)
    }

    fun drawCenteredString(component: IChatComponent, x: Int, y: Int, alpha: Float) {
        drawString(component, x - width(component) / 2, y, alpha)
    }

    fun drawCenteredString(text: String, x: Int, y: Int, alpha: Float) {
        drawString(text, x - width(text) / 2, y, alpha)
    }

    fun fill(x: Int, y: Int, width: Int, height: Int, color: Int) {
        Gui.drawRect(x, y, x + width, y + height, color)
    }

    fun width(component: IChatComponent): Int = font.getStringWidth(component.formattedText)
    fun width(text: String): Int = font.getStringWidth(text)
    fun lineHeight(): Int = font.FONT_HEIGHT
}
*///?} else {
import net.minecraft.client.gui.components.ChatComponent
import net.minecraft.network.chat.Component
import net.minecraft.util.FormattedCharSequence
import org.polyfrost.oneconfig.utils.v1.dsl.mc

//~ if <1.21.11 'ChatComponent.ChatGraphicsAccess' -> 'net.minecraft.client.gui.GuiGraphics'
class ChatGraphics(private val graphics: ChatComponent.ChatGraphicsAccess) {
    //? if >=1.21.11
    var hovered = false

    fun drawString(sequence: FormattedCharSequence, x: Int, y: Int, alpha: Float) {
        //? if >=1.21.11 {
        graphics.updatePose { it.translate(x.toFloat(), y.toFloat()) }
        if (graphics.handleMessage(0, alpha, sequence)) {
            hovered = true
        }
        graphics.updatePose { it.translate(-x.toFloat(), -y.toFloat()) }
        //?} else
        //graphics.drawString(mc.font, sequence, x, y, net.minecraft.util.ARGB.white(alpha))
    }

    fun drawString(component: Component, x: Int, y: Int, alpha: Float) {
        drawString(component.visualOrderText, x, y, alpha)
    }

    fun drawCenteredString(component: Component, x: Int, y: Int, alpha: Float) {
        drawString(component, x - width(component) / 2, y, alpha)
    }

    fun drawCenteredString(sequence: FormattedCharSequence, x: Int, y: Int, alpha: Float) {
        drawString(sequence, x - width(sequence) / 2, y, alpha)
    }

    fun fill(x: Int, y: Int, width: Int, height: Int, color: Int) {
        graphics.fill(x, y, width, height, color)
    }
    
    fun width(component: Component): Int = mc.font.width(component)
    
    fun width(sequence: FormattedCharSequence): Int = mc.font.width(sequence)
    
    fun lineHeight(): Int = mc.font.lineHeight
}
//?}