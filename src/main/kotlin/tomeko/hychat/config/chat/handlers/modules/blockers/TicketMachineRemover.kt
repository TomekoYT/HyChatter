package tomeko.hychat.config.chat.handlers.modules.blockers

import net.hypixel.data.type.GameType
import org.polyfrost.oneconfig.api.hypixel.v1.HypixelUtils
import tomeko.hychat.config.HyChatConfig
import tomeko.hychat.config.chat.handlers.ChatReceiveModule
import tomeko.hychat.config.data.providers.LanguageData
import tomeko.hychat.config.events.ChatReceiveEvent

object TicketMachineRemover : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        val location = HypixelUtils.getLocation()
        val message = event.unformattedMessage
        if (location.gameType.orElse(null) == GameType.BEDWARS
            && !location.inGame()
            && LanguageData.TICKET_ANNOUNCER.matches(message)
        ) {
            event.cancelled = true
        }
    }

    override val isEnabled
        get() = HyChatConfig.removeTicketMachineAnnouncements
    override val priority = -1
}

