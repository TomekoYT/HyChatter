package tomeko.hychat.config.chat.handlers.modules.modifiers

import tomeko.hychat.config.HyChatConfig
import tomeko.hychat.config.chat.handlers.ChatReceiveModule
import tomeko.hychat.config.data.providers.ChatEmotesData
import tomeko.hychat.config.events.ChatReceiveEvent

object ChatEmoteReplacer : ChatReceiveModule {
    override fun onChatReceived(event: ChatReceiveEvent) {
        val message = event.message.plainCopy().withStyle(event.message.style)

        var i = 0
        while (i < event.message.siblings.size) {
            val emote = ChatEmotesData.emotes.entries.firstOrNull { (_, sequence) ->
                sequence.isNotEmpty() && i + sequence.size <= event.message.siblings.size
                    && event.message.siblings.subList(i, i + sequence.size) == sequence
            }

            if (emote != null) {
                when (HyChatConfig.chatEmotesReplacementMode) {
                    1 -> emote.value.forEach { message.append(it.string) }
                    2 -> message.append(emote.key)
                }
                i += emote.value.size
            } else {
                message.append(event.message.siblings[i])
                i++
            }
        }

        event.message = message
    }

    override val isEnabled
        get() = HyChatConfig.replaceChatEmotes
    override val priority = -1
}
