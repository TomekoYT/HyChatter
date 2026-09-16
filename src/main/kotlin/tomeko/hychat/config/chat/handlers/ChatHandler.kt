package tomeko.hychat.config.chat.handlers

import org.polyfrost.oneconfig.api.event.v1.EventManager
import org.polyfrost.oneconfig.api.event.v1.events.WorldEvent
import org.polyfrost.oneconfig.api.event.v1.invoke.impl.Subscribe
import org.polyfrost.oneconfig.api.hypixel.v1.HypixelUtils
import tomeko.hychat.config.chat.handlers.modules.blockers.*
import tomeko.hychat.config.chat.handlers.modules.modifiers.*
import tomeko.hychat.config.chat.handlers.modules.triggers.*
import tomeko.hychat.config.events.ChatReceiveEvent
import tomeko.hychat.config.events.ChatSendEvent
import tomeko.hychat.utils.Debug

object ChatHandler {
    private val receiveModules = mutableListOf<ChatReceiveModule>()
    private val sendModules = mutableListOf<ChatSendModule>()
    private val resetModules = mutableListOf<ChatReceiveResetModule>()

    init {
        listOf(
            AdBlocker, AntiGG, AntiGL, BedwarsAdvertisementsRemover,
            BridgeOwnGoalDeathRemover, ConnectionStatusRemover,
            CurseOfSpamRemover, DiscordSafetyWarningRemover, DuelsBlockTrail,
            DuelsNoStatsChange, EarnedCoinsAndExpRemover, GameAnnouncementsRemover,
            GameTipsRemover, GiftBlocker, HotPotatoRemover,
            HypeLimitReminderRemover, KarmaRemover, LobbyFishingAnnouncementRemover,
            LobbyJoinRemover, NonCooldownBlocker, OnlineStatusRemover, QuestBlocker,
            ReplayRecordedRemover, SeasonalCollectedRemover, ServerConnectedMessage,
            ShoutBlocker, SkyblockWelcomeRemover, SoulWellAnnouncerRemover,
            StatsMessageRemover, TicketMachineRemover, TipMessageRemover,

            ChatEmoteReplacer, ColoredPlayerConnectionStatus,
            GameStartCompactor, GameStatusRestyler, ShortChannelNames,
            ShortPMChannelNames,

            AutoAFKReply, AutoFriend,
            AutoGG, AutoGL, AutoPartyWarn, AutoPartyWarpConfirm,
            AutoWB, GuildWelcomer, BroadcastAchievement,
            BroadcastLevelUp, SilentRemoval, ThankWatchdog
        ).forEach { this.registerModule(it) }

        // these also listen for non chat events
        listOf(
            AutoGG,
        ).forEach { EventManager.INSTANCE.register(it) }

        receiveModules.sortBy { it.priority }
        sendModules.sortBy { it.priority }
        resetModules.sortBy { it.priority }
    }

    @Subscribe
    fun onChatReceive(event: ChatReceiveEvent) {
        if (!HypixelUtils.isHypixel() || event.isOverlay) return

        for (module in this.receiveModules) {
            try {
                if (module.isEnabled) {
                    module.onChatReceived(event)
                    if (event.cancelled) return
                }
            } catch (e: Exception) {
                Debug.log(
                    "An error occurred while handling a received chat message with module ${module.javaClass.simpleName}"
                )
            }
        }
    }

    @Subscribe
    fun onChatSend(event: ChatSendEvent) {
        if (!HypixelUtils.isHypixel()) return

        for (module in this.sendModules) {
            try {
                if (module.isEnabled) {
                    module.onChatSend(event)
                    if (event.cancelled) return
                }
            } catch (e: Exception) {
                Debug.log(
                    "An error occurred while handling a sent chat message with module ${module.javaClass.simpleName}"
                )
            }
        }
    }

    @Subscribe
    fun onWorldLeave(event: WorldEvent.Unload) {
        for (module in this.resetModules) {
            module.reset()
        }
    }

    private fun registerModule(module: ChatModule) {
        if (module is ChatReceiveModule) receiveModules.add(module)
        if (module is ChatSendModule) sendModules.add(module)
        if (module is ChatReceiveResetModule) resetModules.add(module)
    }
}
