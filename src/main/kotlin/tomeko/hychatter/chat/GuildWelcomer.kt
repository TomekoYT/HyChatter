package tomeko.hychatter.chat

//? if 1.8.9 {
//import net.minecraft.util.IChatComponent as Component
//?} else {
import net.minecraft.network.chat.Component
//?}
//? if ornithe {
//import tomeko.hychatter.event.ClientReceiveMessageEvents
//?} else {
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
//?}
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.data.providers.LanguageData
import tomeko.hychatter.utils.ChatUtils

object GuildWelcomer {
    fun register() {
        ClientReceiveMessageEvents.GAME.register(::onGameReceive)
    }

    private fun onGameReceive(message: Component, fromActionBar: Boolean) {
        if (fromActionBar || !HyChatterConfig.guildWelcomeMessage) return
        val text =
            //? if 1.8.9 {
            //message.unformattedText
            //?} else {
            message.string
            //?}
        LanguageData.GUILD_JOIN.find(text)?.let { match ->
            val player = match.groups["player"]?.value ?: return@let
            ChatUtils.queueMessage("/gc Welcome to the guild $player!")
        }
    }
}
