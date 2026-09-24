package tomeko.hychatter

//? if ornithe
//import net.ornithemc.osl.entrypoints.api.ModInitializer
//? else
import net.fabricmc.api.ClientModInitializer
import tomeko.hychatter.automatic.AntiGG
import tomeko.hychatter.automatic.AntiGL
import tomeko.hychatter.automatic.AutoAFKReply
import tomeko.hychatter.automatic.AutoFriend
import tomeko.hychatter.automatic.AutoGG
import tomeko.hychatter.automatic.AutoGL
import tomeko.hychatter.automatic.AutoPartyWarn
import tomeko.hychatter.automatic.AutoPartyWarpConfirm
import tomeko.hychatter.automatic.AutoWB
import tomeko.hychatter.automatic.BroadcastAchievement
import tomeko.hychatter.automatic.BroadcastLevelUp
import tomeko.hychatter.automatic.GuildWelcomer
import tomeko.hychatter.automatic.ThankWatchdog
import tomeko.hychatter.chat.*
import tomeko.hychatter.commands.*
import tomeko.hychatter.config.*
import tomeko.hychatter.cooldown.NonCooldownBlocker
import tomeko.hychatter.cooldown.ShoutBlocker
import tomeko.hychatter.location.*
import tomeko.hychatter.restylers.ColoredPlayerConnectionStatus
import tomeko.hychatter.restylers.GameStartCompactor
import tomeko.hychatter.restylers.GameStatusRestyler
import tomeko.hychatter.restylers.MVPEmoji
import tomeko.hychatter.restylers.ShortChannelNames
import tomeko.hychatter.restylers.ShortPMChannelNames
import tomeko.hychatter.restylers.WhiteChatMessages
import tomeko.hychatter.utils.*
import tomeko.hychatter.waypoints.CoordsWaypoints
import tomeko.hychatter.waypoints.DangerousTauntWaypoint
import tomeko.hychatter.waypoints.WaypointRenderer

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
        AutoAFKReply.register()
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