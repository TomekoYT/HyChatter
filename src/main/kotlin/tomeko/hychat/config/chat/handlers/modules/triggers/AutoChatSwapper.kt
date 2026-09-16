/*package tomeko.hychat.config.chat.handlers.modules.triggers

import net.fabricmc.loader.api.FabricLoader
import org.polyfrost.chatting.chat.ChatTabs
import org.polyfrost.chatting.config.ChattingConfig
import org.polyfrost.hytils.HytilsRebornConstants
import org.polyfrost.oneconfig.api.notifications.v1.NotificationAction
import org.polyfrost.oneconfig.api.notifications.v1.NotificationType
import org.polyfrost.oneconfig.api.notifications.v1.Notifications
import org.polyfrost.oneconfig.utils.v1.Multithreading
import tomeko.hychat.config.HyChatConfig
import tomeko.hychat.config.chat.handlers.ChatReceiveModule
import tomeko.hychat.config.data.providers.LanguageData
import tomeko.hychat.config.events.ChatReceiveEvent
import tomeko.hychat.utils.ChatUtils
import java.util.*
import java.util.concurrent.TimeUnit

object AutoChatSwapper : ChatReceiveModule {
    var shouldCancelChannelMessage = false

    override fun onChatReceived(event: ChatReceiveEvent) {
        if (event.plainMessage.matches(LanguageData.PARTY_JOIN)) {
            ChatUtils.queueMessage("/chat party")
            switchChattingTab("PARTY")

            shouldCancelChannelMessage = true
            Multithreading.schedule({ shouldCancelChannelMessage = false }, 5L, TimeUnit.SECONDS)
        } else if (event.plainMessage.matches(LanguageData.PARTY_LEAVE)) {
            val channel = when (HyChatConfig.chatSwapperReturnChannel) {
                1 -> "guild"
                2 -> "officer"
                else -> "all"
            }

            ChatUtils.queueMessage("/chat $channel")
            switchChattingTab(channel.uppercase(Locale.ROOT))

            shouldCancelChannelMessage = true
            Multithreading.schedule({ shouldCancelChannelMessage = false }, 5L, TimeUnit.SECONDS)
        } else if (shouldCancelChannelMessage && (event.plainMessage == LanguageData.ALREADY_IN_CHANNEL
                || (HyChatConfig.chatSwapperHideAllChannelMsg && event.plainMessage.matches(LanguageData.CHANNEL_SWAP)))
        ) {
            event.cancelled = true
        }
    }

    private fun switchChattingTab(channel: String) {
        if (HyChatConfig.chatSwapperChattingIntegration
            && FabricLoader.getInstance().isModLoaded("chatting")
            && ChattingConfig.chatTabs
        ) {
            val currentTabs = ChatTabs.currentTabs
            val tab = ChatTabs.tabs.find { it.name == channel }
            if (tab != null) {
                ChatTabs.currentTabs.clear()
                ChatTabs.currentTabs.add(tab)
                ChatTabs.refresh()

                Notifications.builder(
                    Constants.MOD_NAME,
                    "Automatically switched to the ${tab.name} chat tab."
                ).type(NotificationType.INFO).action(NotificationAction("Switch back to previous tab", true) {
                    ChatTabs.currentTabs.clear()
                    ChatTabs.currentTabs.addAll(currentTabs)
                    ChatTabs.refresh()
                }).send()
            }
        }
    }

    override val isEnabled
        get() = HyChatConfig.chatSwapper
}
*/