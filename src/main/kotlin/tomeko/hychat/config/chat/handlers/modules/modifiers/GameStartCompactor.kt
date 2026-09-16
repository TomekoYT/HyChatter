package tomeko.hychat.config.chat.handlers.modules.modifiers

//? if 1.8.9 {
/*import net.minecraft.client.gui.ChatLine
import net.minecraft.util.IChatComponent
import org.polyfrost.oneconfig.utils.v1.dsl.mc
import tomeko.hychat.config.HyChatConfig
import tomeko.hychat.config.chat.handlers.ChatReceiveModule
import tomeko.hychat.config.data.providers.LanguageData
import tomeko.hychat.config.events.ChatReceiveEvent
import tomeko.hychat.mixins.ChatComponentAccessor

object GameStartCompactor : ChatReceiveModule {
    var lastMessage: IChatComponent? = null

    override fun onChatReceived(event: ChatReceiveEvent) {
        if (!event.unformattedMessage.matches(LanguageData.GAME_STARTING)) return

        val previous = lastMessage
        if (previous != null) {
            val chat = mc.ingameGUI.chatGUI as ChatComponentAccessor
            val removed = chat.getAllMessages().filter { it.chatComponent == previous }

            if (removed.isNotEmpty()) {
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
            }
        }

        lastMessage = event.message
    }

    override val isEnabled
        get() = HyChatConfig.compactGameStartAnnouncements

    override val priority = 2
}
*///?} else {
import net.minecraft.network.chat.Component
import tomeko.hychat.mixins.ChatComponentAccessor
import org.polyfrost.oneconfig.utils.v1.dsl.mc
import tomeko.hychat.config.HyChatConfig
import tomeko.hychat.config.chat.handlers.ChatReceiveModule
import tomeko.hychat.config.data.providers.LanguageData
import tomeko.hychat.config.events.ChatReceiveEvent

object GameStartCompactor : ChatReceiveModule {
    var lastMessage: Component? = null

    override fun onChatReceived(event: ChatReceiveEvent) {
        if (!event.unformattedMessage.matches(LanguageData.GAME_STARTING)) return

        if (lastMessage != null) {
            //~ if <26.2 'gui.hud' -> 'gui'
            val chat = (mc.gui.hud.chat as ChatComponentAccessor)
            val removed = chat.allMessages.filter { it.content == lastMessage }

            if (removed.isNotEmpty()) {
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
            }
        }

        lastMessage = event.message
    }

    override val isEnabled
        get() = HyChatConfig.compactGameStartAnnouncements

    // this should run after game status restyler
    override val priority = 2
}
//?}