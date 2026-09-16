package tomeko.hychat.config.chat.handlers.modules.triggers

import org.polyfrost.oneconfig.utils.v1.Multithreading
import tomeko.hychat.config.HyChatConfig
import tomeko.hychat.config.chat.handlers.ChatReceiveModule
import tomeko.hychat.config.data.providers.LanguageData
import tomeko.hychat.config.events.ChatReceiveEvent
import tomeko.hychat.utils.ChatUtils
import java.util.concurrent.TimeUnit

object AutoPartyWarpConfirm : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        if (event.unformattedMessage == LanguageData.PARTY_CONFIRM_WARP) {
            event.cancelled = true
            Multithreading.schedule({ ChatUtils.queueMessage("/p warp") }, 1500L, TimeUnit.MILLISECONDS)
        }
    }

    override val isEnabled
        get() = HyChatConfig.autoPartyWarpConfirm
}
