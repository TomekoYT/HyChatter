package tomeko.hychatter.config.chat.handlers.modules.triggers

import org.polyfrost.oneconfig.utils.v1.Multithreading
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.chat.handlers.ChatReceiveModule
import tomeko.hychatter.config.data.providers.LanguageData
import tomeko.hychatter.config.events.ChatReceiveEvent
import tomeko.hychatter.utils.ChatUtils
import java.util.concurrent.TimeUnit

object AutoPartyWarpConfirm : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        if (event.unformattedMessage == LanguageData.PARTY_CONFIRM_WARP) {
            event.cancelled = true
            Multithreading.schedule({ ChatUtils.queueMessage("/p warp") }, 1500L, TimeUnit.MILLISECONDS)
        }
    }

    override val isEnabled
        get() = HyChatterConfig.autoPartyWarpConfirm
}
