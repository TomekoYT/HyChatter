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
import net.minecraft.client.Minecraft
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.LanguageData
import tomeko.hychatter.utils.HypixelPackets
import kotlin.text.get

object BroadcastAchievement {
    private val achievements = mutableSetOf<String>()

    fun register() {
        ClientReceiveMessageEvents.GAME.register(::onGameReceive)
    }

    private fun onGameReceive(message: Component, fromActionBar: Boolean) {
        if (fromActionBar || !HyChatterConfig.broadcastAchievements || !HypixelPackets.onHypixel) return
        val text =
            //? if 1.8.9 {
            //message.unformattedText
            //?} else {
            message.string
            //?}
        LanguageData.ACHIEVEMENT_UNLOCKED.find(text)?.let { match ->
            val achievement = match.groups["achievement"]?.value ?: return@let
            achievements.add(achievement)
            //? if 1.8.9
            //Minecraft.getMinecraft().thePlayer?.sendChatMessage("/gc Achievement unlocked! I unlocked the $achievement achievement!")
            //? else
            Minecraft.getInstance().player?.connection?.sendCommand("gc Achievement unlocked! I unlocked the $achievement achievement!")
        }
    }
}
