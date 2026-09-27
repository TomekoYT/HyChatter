package tomeko.hychatter

//? if forge {
/*import cc.polyfrost.oneconfig.events.EventManager
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
*///?} elif ornithe {
//import net.ornithemc.osl.entrypoints.api.ModInitializer
//?} else {
import net.fabricmc.api.ClientModInitializer
//?}
import tomeko.hychatter.automatic.*
import tomeko.hychatter.hiders.*
import tomeko.hychatter.commands.*
import tomeko.hychatter.config.*
import tomeko.hychatter.cooldown.*
import tomeko.hychatter.restylers.*
import tomeko.hychatter.utils.*
import tomeko.hychatter.waypoints.*

//? if forge {
/*@Mod(
    modid = Constants.MOD_ID,
    name = Constants.MOD_NAME,
    version = Constants.MOD_VERSION,
    modLanguageAdapter = "cc.polyfrost.oneconfig.utils.KotlinLanguageAdapter",
    dependencies = "required-after:hypixel_mod_api"
)
*///?}
class HyChatter
//? if ornithe
//: ModInitializer
//? elif fabric
    : ClientModInitializer
{
    //? if forge
    //@Mod.EventHandler
    //? else
    override
    fun
    //? if ornithe
    //init(
    //? else
            onInitializeClient(
        //? if forge
        //event: FMLInitializationEvent
    ) {
        //? if forge
        //EventManager.INSTANCE.register(this)

        AntiGG.register()
        AntiGL.register()
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
        LanguageData.register()

        NonCooldownBlocker.register()
        ShoutBlocker.register()

        AdBlocker.register()
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
        HideGuildMOTD.register()
        HotPotatoRemover.register()
        HypeLimitReminderRemover.register()
        KarmaRemover.register()
        LobbyFishingAnnouncementRemover.register()
        LobbyJoinRemover.register()
        OnlineStatusRemover.register()
        QuestBlocker.register()
        ReplayRecordedRemover.register()
        SeasonalCollectedRemover.register()
        ServerConnectedMessage.register()
        //? if fabric
        SkyblockWelcomeRemover.register()
        SoulWellAnnouncerRemover.register()
        StatsMessageRemover.register()
        TicketMachineRemover.register()
        TipMessageRemover.register()

        ColoredPlayerConnectionStatus.register()
        GameStartCompactor.register()
        GameStatusRestyler.register()
        MVPEmoji.register()
        ShortChannelNames.register()
        ShortPMChannelNames.register()
        WhiteChatMessages.register()

        HypixelPackets.register()

        CoordsWaypoints.register()
        DangerousTauntWaypoint.register()
        WaypointRenderer.register()

        Debug.forceLog("${Constants.MOD_VERSION} Initialized!")
    }
}