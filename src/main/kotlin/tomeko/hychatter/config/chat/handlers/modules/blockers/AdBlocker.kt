package tomeko.hychatter.config.chat.handlers.modules.blockers

import org.polyfrost.oneconfig.api.hypixel.v1.HypixelUtils
import org.polyfrost.oneconfig.utils.v1.dsl.mc
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.chat.handlers.ChatReceiveModule
import tomeko.hychatter.config.data.providers.LanguageData
import tomeko.hychatter.config.events.ChatReceiveEvent

object AdBlocker : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        val message = event.unformattedMessage
        if ((message.startsWith("-") && message.endsWith("-"))
            || (message.startsWith("▬") && message.endsWith("▬"))
            || (message.startsWith("≡") && message.endsWith("≡"))
            || !message.contains(": ")
            || message.contains(mc.user.name, true)
        ) return

        if (message.contains(LanguageData.CHAT_ADVERTISEMENTS)
            || (!HypixelUtils.getLocation().inGame() && message.contains(LanguageData.CHAT_RANK_BEGGING))
        ) {
            event.cancelled = true
        }
    }

    override val isEnabled
        get() = HyChatterConfig.removePlayerAds
    override val priority = -1
}
