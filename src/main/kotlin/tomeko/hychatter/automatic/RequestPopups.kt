package tomeko.hychatter.automatic

//? if forge {
/*import cc.polyfrost.oneconfig.utils.Notifications
*///?} else {
import org.polyfrost.oneconfig.api.notifications.v1.Notification
import org.polyfrost.oneconfig.api.notifications.v1.NotificationAction
import org.polyfrost.oneconfig.api.notifications.v1.NotificationType
import org.polyfrost.oneconfig.api.notifications.v1.Notifications
import org.polyfrost.oneconfig.api.notifications.v1.NotificationsManager
//?}
//? if 1.8.9 {
/*import net.minecraft.client.audio.PositionedSoundRecord
import net.minecraft.util.IChatComponent as Component
import net.minecraft.util.ResourceLocation
import tomeko.hychatter.event.ClientReceiveMessageEvents
*///?} else {
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
import net.minecraft.client.resources.sounds.SimpleSoundInstance
import net.minecraft.network.chat.Component
import net.minecraft.sounds.SoundEvents
//?}
import net.minecraft.client.Minecraft
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.LanguageData
import tomeko.hychatter.utils.HypixelPackets
import tomeko.hychatter.utils.removeFormatting

// Ported from Sk1er's PopupEvents
object RequestPopups {
    private class Request(
        val regex: () -> Regex,
        val title: String,
        val enabled: () -> Boolean,
        val message: (MatchResult) -> String,
        val accept: (MatchResult) -> String,
        val deny: ((MatchResult) -> String)? = null
    )

    private class Pending(val accept: String, val deny: String?, val expires: Long) {
        //? if !forge
        lateinit var notification: Notification
    }

    private val MatchResult.player get() = groups["player"]!!.value

    private val requests = listOf(
        Request(
            { LanguageData.POPUP_FRIEND_REQUEST }, "Friend Request",
            // Auto Friend already accepts these
            { HyChatterConfig.popupFriendRequests && !HyChatterConfig.autoFriend },
            { "From ${it.player}" }, { "/friend accept ${it.player}" }, { "/friend deny ${it.player}" }
        ),
        Request(
            { LanguageData.POPUP_PARTY_INVITE }, "Party Invite", { HyChatterConfig.popupPartyInvites },
            { "From ${it.player}" }, { "/party accept ${it.player}" }
        ),
        Request(
            { LanguageData.POPUP_GUILD_INVITE }, "Guild Invite", { HyChatterConfig.popupGuildInvites },
            { "From ${it.player}" }, { it.groups["command"]!!.value }
        ),
        Request(
            { LanguageData.POPUP_GUILD_PARTY_INVITE }, "Guild Party Invite", { HyChatterConfig.popupGuildPartyInvites },
            { "From ${it.player} in ${it.groups["guild"]!!.value}" }, { "/party accept ${it.player}" }
        ),
        Request(
            { LanguageData.POPUP_DUEL_REQUEST }, "Duel Request", { HyChatterConfig.popupDuelRequests },
            { "${it.groups["game"]!!.value} Duel from ${it.player}" }, { "/duel accept ${it.player}" }
        ),
        Request(
            { LanguageData.POPUP_SKYBLOCK_TRADE }, "Trade Request", { HyChatterConfig.popupSkyblockTrades },
            { "From ${it.player}" }, { "/trade ${it.player}" }
        ),
    )

    private val pending = ArrayDeque<Pending>()

    fun register() {
        ClientReceiveMessageEvents.GAME.register(::onGameReceive)
    }

    private fun onGameReceive(message: Component, fromActionBar: Boolean) {
        if (fromActionBar || !HypixelPackets.onHypixel) return

        val text =
            //? if 1.8.9 {
            //message.unformattedText.removeFormatting()
            //?} else {
            message.string.removeFormatting()
            //?}

        for (request in requests) {
            val match = request.regex().find(text) ?: continue
            if (request.enabled()) show(request, match)
            return
        }
    }

    private fun show(request: Request, match: MatchResult) {
        val start = System.currentTimeMillis()
        val durationMs = HyChatterConfig.popupDuration * 1000f
        val p = Pending(request.accept(match), request.deny?.invoke(match), start + durationMs.toLong())

        //? if forge {
        /*// legacy OneConfig notifications have no buttons, so clicking accepts
        Notifications.INSTANCE.send(
            request.title,
            "${request.message(match)} - click or press ${HyChatterConfig.popupAcceptKey.display} to accept, ${HyChatterConfig.popupDenyKey.display} to deny",
            durationMs,
            Runnable { answer(p, true) }
        )
        *///?} else {
        p.notification = Notifications.builder(request.title, request.message(match))
            .progress { (System.currentTimeMillis() - start) / durationMs }
            .type(NotificationType.INFO)
            .action(NotificationAction("Accept [${HyChatterConfig.popupAcceptKey.displayName()}]", true) { answer(p, true) })
            .action(NotificationAction("Deny [${HyChatterConfig.popupDenyKey.displayName()}]", false) { answer(p, false) })
            .send()
        //?}
        pending.add(p)

        if (HyChatterConfig.popupPing) {
            //? if 1.8.9 {
            //Minecraft.getMinecraft().soundHandler.playSound(PositionedSoundRecord.create(ResourceLocation("note.pling"), 2f))
            //?} else {
            Minecraft.getInstance().soundManager.play(SimpleSoundInstance.forUI(SoundEvents.NOTE_BLOCK_PLING, 2f))
            //?}
        }
    }

    /** Answers the oldest popup still on screen. */
    fun answer(accepted: Boolean) {
        pending.removeAll {
            //? if forge
            //System.currentTimeMillis() > it.expires
            //? else
            !NotificationsManager.contains(it.notification)
        }
        val p = pending.firstOrNull() ?: return
        answer(p, accepted)
        //? if !forge
        NotificationsManager.remove(p.notification)
    }

    private fun answer(p: Pending, accepted: Boolean) {
        if (!pending.remove(p)) return
        val command = (if (accepted) p.accept else p.deny) ?: return
        //? if 1.8.9 {
        //Minecraft.getMinecraft().thePlayer?.sendChatMessage(command)
        //?} else {
        Minecraft.getInstance().player?.connection?.sendCommand(command.removePrefix("/"))
        //?}
    }
}
