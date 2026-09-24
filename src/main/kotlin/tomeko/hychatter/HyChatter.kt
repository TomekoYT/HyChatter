package tomeko.hychatter

//? if ornithe
//import net.ornithemc.osl.entrypoints.api.ModInitializer
//? else
import net.fabricmc.api.ClientModInitializer
import tomeko.hychatter.automatic.*
import tomeko.hychatter.hiders.*
import tomeko.hychatter.commands.*
import tomeko.hychatter.config.*
import tomeko.hychatter.cooldown.*
import tomeko.hychatter.restylers.*
import tomeko.hychatter.utils.*
import tomeko.hychatter.waypoints.*

class HyChatter
//? if ornithe
    //: ModInitializer {
//? elif fabric
    : ClientModInitializer {

    override fun
    //? if ornithe
            //init(
        //? else
        onInitializeClient(
    ) {
        CoordsWaypoints.register()
        DangerousTauntWaypoint.register()
        HideGuildMOTD.register()
        MVPEmoji.register()
        WhiteChatMessages.register()

        AdBlocker.register()
        AntiGG.register()
        AntiGL.register()
        BedwarsAdvertisementsRemover.register()
        BridgeOwnGoalDeathRemover.register()
        ConnectionStatusRemover.register()
        CurseOfSpamRemover.register()
        DiscordSafetyWarningRemover.register()
        DuelsBlockTrail.register()
        DuelsNoStatsChange.register()
        EarnedCoinsAndExpRemover.register()
        GameAnnouncementsRemover.register()
        GameTipsRemover.register()
        GiftBlocker.register()
        HotPotatoRemover.register()
        HypeLimitReminderRemover.register()
        KarmaRemover.register()
        LobbyFishingAnnouncementRemover.register()
        LobbyJoinRemover.register()
        NonCooldownBlocker.register()
        OnlineStatusRemover.register()
        QuestBlocker.register()
        ReplayRecordedRemover.register()
        SeasonalCollectedRemover.register()
        ServerConnectedMessage.register()
        ShoutBlocker.register()
        SkyblockWelcomeRemover.register()
        SoulWellAnnouncerRemover.register()
        StatsMessageRemover.register()
        TicketMachineRemover.register()
        TipMessageRemover.register()

        ColoredPlayerConnectionStatus.register()
        GameStatusRestyler.register()
        GameStartCompactor.register()
        ShortChannelNames.register()
        ShortPMChannelNames.register()

        AutoGG.register()
        AutoFriend.register()
        AutoGL.register()
        AutoPartyWarn.register()
        AutoPartyWarpConfirm.register()
        AutoWB.register()
        BroadcastAchievement.register()
        BroadcastLevelUp.register()
        GuildWelcomer.register()
        SilentRemoval.register()
        ThankWatchdog.register()

        HyChatterCommand.register()
        SendCoordsCommand.register()

        HyChatterConfig.register()

        HypixelPackets.register()

        WaypointRenderer.register()

        Debug.forceLog("${Constants.MOD_VERSION} Initialized!")
    }
}