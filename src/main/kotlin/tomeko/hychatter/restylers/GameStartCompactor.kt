package tomeko.hychatter.restylers

//? if 1.8.9 {
/*import net.minecraft.client.gui.ChatLine
import net.minecraft.util.IChatComponent as Component
import org.polyfrost.oneconfig.utils.v1.dsl.mc
import tomeko.hychatter.mixins.ChatComponentAccessor
*///?} else {
import net.minecraft.network.chat.Component
import tomeko.hychatter.mixins.ChatComponentAccessor
import org.polyfrost.oneconfig.utils.v1.dsl.mc
//?}
//? if ornithe {
//import tomeko.hychatter.event.ClientReceiveMessageEvents
//?} else {
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
//?}
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.LanguageData

object GameStartCompactor {
    var lastMessage: Component? = null

    fun register() {
        ClientReceiveMessageEvents.MODIFY_GAME.register(::onGameMessage)
    }

    private fun onGameMessage(component: Component, fromActionBar: Boolean): Component {
        if (fromActionBar || !HyChatterConfig.compactGameStartAnnouncements) return component
        val message =
            //? if 1.8.9
            //component.unformattedText
            //? else
            component.string

        if (!message.matches(LanguageData.GAME_STARTING)) return component

        lastMessage?.let(::removePrevious)
        lastMessage = component
        return component
    }

    private fun removePrevious(previous: Component) {
        //? if 1.8.9 {
        /*val chat = mc.ingameGUI.chatGUI as ChatComponentAccessor
        val removed = chat.getAllMessages().filter { it.chatComponent == previous }
        if (removed.isEmpty()) return

        chat.getAllMessages().removeAll(removed)

        val scrollbarPos = chat.getChatScrollbarPos()
        var removedBelowScroll = 0
        var index = 0
        val lines = chat.getTrimmedMessages().iterator()
        while (lines.hasNext()) {
            val line = lines.next()
            val parent = line.chatComponent
            if (removed.any { it === parent }) {
                lines.remove()
                if (index < scrollbarPos) removedBelowScroll++
            }
            index++
        }

        if (removedBelowScroll > 0) {
            chat.setChatScrollbarPos(scrollbarPos - removedBelowScroll)
        }
        *///?} else {
        //~ if <26.2 'gui.hud' -> 'gui'
        val chat = mc.gui.hud.chat as ChatComponentAccessor
        val removed = chat.allMessages.filter { it.content == previous }
        if (removed.isEmpty()) return

        chat.allMessages.removeAll(removed)

        // remove the matching lines directly instead of refreshTrimmedMessages, which re-wraps
        // the entire history and stutters with large chat history limits
        val scrollbarPos = chat.chatScrollbarPos
        var removedBelowScroll = 0
        var index = 0
        val lines = chat.trimmedMessages.iterator()
        while (lines.hasNext()) {
            val parent = lines.next().parent
            if (removed.any { it === parent }) {
                lines.remove()
                if (index < scrollbarPos) removedBelowScroll++
            }
            index++
        }

        if (removedBelowScroll > 0) {
            chat.chatScrollbarPos = scrollbarPos - removedBelowScroll
        }
        //?}
    }
}
