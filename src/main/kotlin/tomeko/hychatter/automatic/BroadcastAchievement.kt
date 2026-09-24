package tomeko.hychatter.automatic

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
import tomeko.hychatter.config.LanguageData
import tomeko.hychatter.utils.ChatUtils
import kotlin.text.get

object BroadcastAchievement {
    private val achievements = mutableSetOf<String>()

    fun register() {
        ClientReceiveMessageEvents.GAME.register(::onGameReceive)
    }

    private fun onGameReceive(message: Component, fromActionBar: Boolean) {
        if (fromActionBar || !HyChatterConfig.broadcastAchievements) return
        val text =
            //? if 1.8.9 {
            //message.unformattedText
            //?} else {
            message.string
            //?}
        LanguageData.ACHIEVEMENT_UNLOCKED.find(text)?.let { match ->
            val achievement = match.groups["achievement"]?.value ?: return@let
            achievements.add(achievement)
            ChatUtils.queueMessage("/gc Achievement unlocked! I unlocked the $achievement achievement!")
        }
    }
}
