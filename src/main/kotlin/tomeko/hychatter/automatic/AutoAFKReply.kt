package tomeko.hychatter.automatic

//? if 1.8.9 {
/*import tomeko.hychatter.utils.LegacyInputTracker
import net.minecraft.util.IChatComponent as Component
*///?} else {
import tomeko.hychatter.mixins.FramerateLimitTrackerAccessor
import org.polyfrost.oneconfig.utils.v1.dsl.mc
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

object AutoAFKReply {
    fun register() {
        //? if ornithe {
        //ClientReceiveMessageEvents.CHAT.register { message -> onMessage(message) }
        //?} else {
        ClientReceiveMessageEvents.GAME.register { message, overlay -> if (!overlay) onMessage(message) }
        //?}
    }

    private fun onMessage(message: Component) {
        if (!HyChatterConfig.autoReplyAfk) return

        val latestInputTime =
            //? if 1.8.9 {
            //LegacyInputTracker.latestInputTime
            //?} else {
            (mc.framerateLimitTracker as FramerateLimitTrackerAccessor).latestInputTime
            //?}
        if (System.currentTimeMillis() - latestInputTime < HyChatterConfig.afkTimeout * 60L * 1000L) return

        LanguageData.PRIVATE_MESSAGE.find(
            //? if 1.8.9 {
            //message.unformattedText
            //?} else {
            message.string
            //?}
        )?.let { match ->
            val type = match.groups["type"]?.value ?: return@let
            if (type != "From") return@let

            val player = match.groups["player"]?.value ?: return@let
            val reply = HyChatterConfig.afkReplyMessage.replace("%player%", player)
            ChatUtils.queueMessage("/msg $player $reply")
        }
    }
}
