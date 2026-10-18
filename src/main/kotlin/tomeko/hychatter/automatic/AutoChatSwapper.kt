package tomeko.hychatter.automatic

//? if 1.8.9 {
/*import net.minecraft.util.IChatComponent as Component
import tomeko.hychatter.event.ClientReceiveMessageEvents
*///?} else {
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
import net.minecraft.network.chat.Component
//?}
//? if forge {
/*import cc.polyfrost.oneconfig.utils.Notifications
*///?} else {
import org.polyfrost.oneconfig.api.notifications.v1.Notifications
//?}
import net.minecraft.client.Minecraft
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.LanguageData
import tomeko.hychatter.utils.Constants
import tomeko.hychatter.utils.HypixelPackets
import tomeko.hychatter.utils.removeFormatting

object AutoChatSwapper {
    private var preventChannelMessageUntil = 0L

    fun register() {
        ClientReceiveMessageEvents.ALLOW_GAME.register(::allowGame)
    }

    private fun allowGame(message: Component, fromActionBar: Boolean): Boolean {
        if (fromActionBar || !HypixelPackets.onHypixel) return true

        val text =
            //? if 1.8.9
            //message.unformattedText
            //? else
            message.string

        if (System.currentTimeMillis() < preventChannelMessageUntil && (text == LanguageData.ALREADY_IN_CHANNEL
                    || (HyChatterConfig.hideAllChatMessage && text.matches(LanguageData.AUTO_CHAT_SWAPPER_CHANNEL_SWAP)))
        ) {
            preventChannelMessageUntil = 0L
            return false
        }

        if (!HyChatterConfig.chatSwapper) return true

        val stripped = text.removeFormatting()
        if (stripped.matches(LanguageData.AUTO_CHAT_SWAPPER_PARTY_LEAVE)) {
            preventChannelMessageUntil = System.currentTimeMillis() + 20_000L
            when (HyChatterConfig.chatSwapperReturnChannel) {
                1 -> swap("g") { it.startsWith("/gc", ignoreCase = true) }
                2 -> swap("o") { it.startsWith("/oc", ignoreCase = true) }
                else -> swap("a") { it.startsWith("/ac", ignoreCase = true) || it.isEmpty() }
            }
        } else if (stripped.matches(LanguageData.AUTO_CHAT_SWAPPER_PARTY_JOIN)) {
            preventChannelMessageUntil = System.currentTimeMillis() + 20_000L
            swap("p") { it.startsWith("/pc", ignoreCase = true) }
        }
        return true
    }

    private fun swap(channel: String, isTab: (String) -> Boolean) {
        //? if 1.8.9
        //Minecraft.getMinecraft().thePlayer?.sendChatMessage("/chat $channel")
        //? else
        Minecraft.getInstance().player?.connection?.sendCommand("chat $channel")

        if (HyChatterConfig.chattingIntegration) switchChattingTab(isTab)
    }

    @Suppress("UNCHECKED_CAST")
    private fun switchChattingTab(isTab: (String) -> Boolean) {
        try {
            val configClass = Class.forName("org.polyfrost.chatting.config.ChattingConfig")
            if (configClass.getMethod("getChatTabs").invoke(configClass.getField("INSTANCE").get(null)) != true) return

            val tabsClass = Class.forName("org.polyfrost.chatting.chat.ChatTabs")
            val chatTabs = tabsClass.getField("INSTANCE").get(null)
            val tabs = tabsClass.getMethod("getTabs").invoke(chatTabs) as List<Any>
            val currentTabs = tabsClass.getMethod("getCurrentTabs").invoke(chatTabs) as MutableList<Any>
            val tab = tabs.firstOrNull { isTab((it.javaClass.getMethod("getPrefix").invoke(it) as String?).orEmpty()) } ?: return
            val refresh = { runCatching { tabsClass.getMethod("refresh").invoke(chatTabs) } }

            val previousTabs = currentTabs.toList()
            currentTabs.clear()
            currentTabs.add(tab)
            refresh()

            val name = tab.javaClass.getMethod("getName").invoke(tab)
            val message = "${Constants.MOD_NAME} has automatically switched to the $name chat tab. Click to revert."
            val revert = Runnable {
                currentTabs.clear()
                currentTabs.addAll(previousTabs)
                refresh()
            }
            //? if forge
            //Notifications.INSTANCE.send(Constants.MOD_NAME, message, revert)
            //? else
            Notifications.builder(Constants.MOD_NAME, message).onClick(revert).send()
        } catch (_: Exception) {
        }
    }
}
