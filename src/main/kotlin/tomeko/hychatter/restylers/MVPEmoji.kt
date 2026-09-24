package tomeko.hychatter.restylers

//? if fabric
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.utils.HypixelPackets
import kotlin.collections.iterator
//? if ornithe
//import tomeko.hychatter.event.ClientSendMessageEvents

object MVPEmoji {
    private val emojis = mapOf(
        "<3" to "❤",
        ":arrow:" to "➜",
        ":bum:" to "♿",
        ":cat:" to "= ＾● ⋏ ●＾ =",
        ":cute:" to "(✿◠‿◠)",
        ":dab:" to "<o/",
        ":dj:" to "ヽ(⌐■_■)ノ♬",
        ":dog:" to "(ᵔᴥᵔ)",
        ":gimme:" to "༼つ◕_◕༽つ",
        ":java:" to "☕",
        ":maths:" to "√(π+x)=L",
        ":no:" to "✖",
        ":peace:" to "✌",
        ":puffer:" to "<('O')>",
        ":pvp:" to "⚔",
        ":shrug:" to "¯\\_(ツ)_/¯",
        ":skull:" to "☠",
        ":sloth:" to "(・⊝・)",
        ":snail:" to "@'-'",
        ":snow:" to "☃",
        ":star:" to "✮",
        ":tableflip:" to "(╯°□°）╯︵ ┻━┻",
        ":thinking:" to "(0.o?)",
        ":totem:" to "☉_☉",
        ":typing:" to "✎...",
        ":wizard:" to "('-')⊃━☆ﾟ.*･｡ﾟ",
        ":yes:" to "✔",
        ":yey:" to "ヽ (◕◡◕) ﾉ",
        "h/" to "ヽ(^◇^*)/",
        "o/" to "( ﾟ◡ﾟ)/"
    )

    fun register() {
        ClientSendMessageEvents.MODIFY_CHAT.register(::replaceWithEmoji)
        ClientSendMessageEvents.MODIFY_COMMAND.register(::replaceWithEmoji)
    }

    private fun replaceWithEmoji(message: String): String {
        if (!HyChatterConfig.mvpEmojisEnabled || !HypixelPackets.onHypixel) return message

        var result = message
        for ((key, value) in emojis) {
            result = result.replace(key, value)
        }

        return result
    }
}