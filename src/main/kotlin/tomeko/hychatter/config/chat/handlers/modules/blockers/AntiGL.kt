package tomeko.hychatter.config.chat.handlers.modules.blockers

import org.polyfrost.oneconfig.api.hypixel.v1.HypixelUtils
import org.polyfrost.oneconfig.utils.v1.dsl.mc
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.chat.handlers.ChatReceiveModule
import tomeko.hychatter.config.data.providers.LanguageData
import tomeko.hychatter.config.events.ChatReceiveEvent

object AntiGL : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        val message = event.unformattedMessage
        if (!HypixelUtils.getLocation().inGame()
            || (message.startsWith("-") && message.endsWith("-"))
            || (message.startsWith("▬") && message.endsWith("▬"))
            || (message.startsWith("≡") && message.endsWith("≡"))
            || !message.contains(": ")
            || message.contains(mc.user.name, true)
        ) return

        if (message.contains(LanguageData.GL_MESSAGES)) {
            event.cancelled = true
        }
    }

    override val isEnabled
        get() = HyChatterConfig.antiGL
    override val priority = -1
}
