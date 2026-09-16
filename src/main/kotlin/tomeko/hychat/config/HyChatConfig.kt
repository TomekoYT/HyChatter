package tomeko.hychat.config

//? if forge {
/*import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.*
import cc.polyfrost.oneconfig.config.core.OneColor as PolyColor
import cc.polyfrost.oneconfig.config.data.InfoType
import cc.polyfrost.oneconfig.config.data.Mod
import cc.polyfrost.oneconfig.config.data.ModType
*///?} else {
import org.polyfrost.compose.render.PolyColor
import org.polyfrost.oneconfig.api.config.v1.Config
import org.polyfrost.oneconfig.api.config.v1.annotations.*
//?}
import tomeko.hychat.utils.Constants

object HyChatConfig : Config(
    //? if forge {
    /*Mod(
        Constants.MOD_NAME,
        ModType.HYPIXEL,
        Constants.MOD_ICON
    ),
    "${Constants.MOD_ID}.json"
    *///?} else {
    "${Constants.MOD_ID}.json",
    Constants.MOD_ICON,
    Constants.MOD_NAME,
    Category.HYPIXEL
    //?}
) {
    //? if !forge {
    val DEPENDENCIES: List<Pair<String, List<String>>> = listOf(
        "coordsWaypointsEnabled" to listOf(
            "coordsWaypointsBoxColor",
            "coordsWaypointsBeamColor",
            "coordsWaypointsRenderOwner",
            "coordsWaypointsOwnerColor",
            "coordsWaypointsRenderText",
            "coordsWaypointsTextColor",
            "coordsWaypointsRenderDistance",
            "coordsWaypointsDistanceTextColor",
            "coordsWaypointsTime"
        )
    )
    //?}

    fun register() {
        //? if forge {
        //initialize()
        //?} else {
        preload()
        for ((condition, dependencies) in DEPENDENCIES) {
            for (dependency in dependencies) {
                addDependency(dependency, condition)
            }
        }
        //?}
    }

    //? if forge {
    //@Exclude
    //?}
    private const val CATEGORY_ARCADE = "Arcade"

    //? if forge {
    //@Exclude
    //?}
    private const val SUBCATEGORY_FARM_HUNT = "Farm Hunt"

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Dangerous Taunt Waypoint",
        description = "Show waypoint on dangerous taunt message in Farm Hunt",
        category = CATEGORY_ARCADE,
        subcategory = SUBCATEGORY_FARM_HUNT
    )
    var dangerousTauntWaypointEnabled = true


    //? if forge {
    //@Exclude
    //?}
    private const val CATEGORY_CHAT = "Chat"

    //? if forge {
    //@Exclude
    //?}
    private const val SUBCATEGORY_WHITE_CHAT_MESSAGES = "White Chat Messages"

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "White Private Messages",
        description = "Color private messages white instead of gray on Hypixel",
        category = CATEGORY_CHAT,
        subcategory = SUBCATEGORY_WHITE_CHAT_MESSAGES
    )
    var whitePrivateMessagesEnabled = true

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "White No Rank Messages",
        description = "Color messages from players with no rank white instead of gray on Hypixel",
        category = CATEGORY_CHAT,
        subcategory = SUBCATEGORY_WHITE_CHAT_MESSAGES
    )
    var whiteNoRankMessagesEnabled = true

    //? if forge {
    //@Exclude
    //?}
    private const val SUBCATEGORY_HIDE_GUILD_MOTD = "Hide Guild MOTD"

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Hide Guild MOTD",
        description = "Hide guild message of the day on Hypixel",
        category = CATEGORY_CHAT,
        subcategory = SUBCATEGORY_HIDE_GUILD_MOTD
    )
    var hideGuildMOTDEnabled = false

    //? if forge {
    //@Exclude
    //?}
    private const val SUBCATEGORY_MVP_EMOJIS = "MVP++ Emojis"

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "MVP++ Emojis",
        description = "Replace emojis like <3 with ❤ on Hypixel",
        category = CATEGORY_CHAT,
        subcategory = SUBCATEGORY_MVP_EMOJIS
    )
    var mvpEmojisEnabled = true

    //? if forge {
    //@Exclude
    //?}
    private const val SUBCATEGORY_SENDCOORDS_COMMAND = "/sendcoords Command"

    @Dropdown(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Default Mode",
        description = "Set default /sendcoords command mode",
        options = [
            "All",
            "Party",
            "Guild"
        ],
        category = CATEGORY_CHAT,
        subcategory = SUBCATEGORY_SENDCOORDS_COMMAND
    )
    var sendcoordsMode = 1

    //? if forge {
    //@Exclude
    //?}
    private const val SUBCATEGORY_COORDS_WAYPOINTS = "Coords Waypoints"

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Coords Waypoints",
        description = "Show waypoint on coords from chat",
        category = CATEGORY_CHAT,
        subcategory = SUBCATEGORY_COORDS_WAYPOINTS
    )
    var coordsWaypointsEnabled = true

    @Color(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Box Color",
        //? if forge {
        //allowAlpha = true,
        //?}
        category = CATEGORY_CHAT,
        subcategory = SUBCATEGORY_COORDS_WAYPOINTS
    )
    var coordsWaypointsBoxColor = PolyColor(0xFFFFFFFF.toInt())

    @Color(
        //? if forge {
        //name =
        //?} else {
        title =
            //?}
            "Box Color",
        //? if forge {
        //allowAlpha = true,
        //?}
        category = CATEGORY_CHAT,
        subcategory = SUBCATEGORY_COORDS_WAYPOINTS
    )
    var coordsWaypointsBeamColor = PolyColor(0xC0FFFFFF.toInt())

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Render Owner",
        category = CATEGORY_CHAT,
        subcategory = SUBCATEGORY_COORDS_WAYPOINTS
    )
    var coordsWaypointsRenderOwner = true

    @Color(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Owner Color",
        //? if forge {
        //allowAlpha = false,
        //?} else {
        alpha = false,
        //?}
        category = CATEGORY_CHAT,
        subcategory = SUBCATEGORY_COORDS_WAYPOINTS
    )
    var coordsWaypointsOwnerColor = PolyColor(0xFFFFFFFF.toInt())

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Render Text",
        category = CATEGORY_CHAT,
        subcategory = SUBCATEGORY_COORDS_WAYPOINTS
    )
    var coordsWaypointsRenderText = true

    @Color(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Text Color",
        //? if forge {
        //allowAlpha = false,
        //?} else {
        alpha = false,
        //?}
        category = CATEGORY_CHAT,
        subcategory = SUBCATEGORY_COORDS_WAYPOINTS
    )
    var coordsWaypointsTextColor = PolyColor(0xFFFFFFFF.toInt())

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Render Distance",
        category = CATEGORY_CHAT,
        subcategory = SUBCATEGORY_COORDS_WAYPOINTS
    )
    var coordsWaypointsRenderDistance = true

    @Color(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Distance Text Color",
        //? if forge {
        //allowAlpha = false,
        //?} else {
        alpha = false,
        //?}
        category = CATEGORY_CHAT,
        subcategory = SUBCATEGORY_COORDS_WAYPOINTS
    )
    var coordsWaypointsDistanceTextColor = PolyColor(0xFFFFFF00.toInt())

    @Slider(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Time",
        min = 0f,
        max = 120f,
        step =
            //? if forge {
            //1,
        //?} else {
        1f,
    //?}
        category = CATEGORY_CHAT,
        subcategory = SUBCATEGORY_COORDS_WAYPOINTS
    )
    var coordsWaypointsTime = 60

    //region Chat
    @Switch(
        title = "Auto GG",
        description = "Send a \"gg\" message at the end of a game.",
        category = "Chat", subcategory = "Automatic"
    )
    var autoGG = true

    @Switch(
        title = "Auto GG Second Message",
        description = "Send a secondary message that will be sent after the first GG message.",
        category = "Chat", subcategory = "Automatic"
    )
    var autoGGSendSecondMessage = false

    @Switch(
        title = "Casual Auto GG",
        description = "Send a \"gg\" message at the end of minigames/events that don't give out Karma, such as SkyBlock and The Pit events.",
        category = "Chat", subcategory = "Automatic"
    )
    var casualAutoGG = false

    @Text(
        title = "Auto GG First Message",
        description = "Choose what message is said on game completion.",
        category = "Chat", subcategory = "Automatic"
    )
    var autoGGMessage = "gg"

    @Slider(
        title = "Auto GG First Message Delay",
        description = "Delay after the game ends to say the first message in seconds.",
        category = "Chat", subcategory = "Automatic",
        min = 0f, max = 5f, step = 1f
    )
    var autoGGFirstMsgDelay = 1f

    @Text(
        title = "Auto GG Second Message",
        description = "Choose the secondary message that will be sent.",
        category = "Chat", subcategory = "Automatic"
    )
    var autoGGSecondMessage = "Have a good day!"

    @Slider(
        title = "Auto GG Second Message Delay",
        description = "Delay after the game ends to say the second message in seconds.",
        category = "Chat", subcategory = "Automatic",
        min = 0f, max = 5f, step = 1f
    )
    var autoGGSecondMsgDelay = 1f

    @Switch(
        title = "Anti GG",
        description = "Remove GG messages from chat.",
        category = "Chat", subcategory = "Automatic"
    )
    var antiGG = false

    @Switch(
        title = "Auto GL",
        description = "Send a message 5 seconds before a Hypixel game starts.",
        category = "Chat", subcategory = "Automatic"
    )
    var autoGL = false

    @Text(
        title = "Auto GL Message",
        description = "Choose what message is said.",
        category = "Chat", subcategory = "Automatic"
    )
    var autoGLMessage = "glhf"

    @Switch(
        title = "Anti GL",
        description = "Remove all GL messages from chat.",
        category = "Chat", subcategory = "Automatic"
    )
    var antiGL = false

    @Switch(
        title = "Auto Friend",
        description = "Automatically accept friend requests.",
        category = "Chat", subcategory = "Automatic"
    )
    var autoFriend = false

    @Switch(
        title = "Auto Party Warp Confirm",
        description = "Automatically confirms party warps.",
        category = "Chat", subcategory = "Automatic"
    )
    var autoPartyWarpConfirm = false

    @Switch(
        title = "Auto Reply When AFK",
        description = "Automatically sends a reply to anyone who PMs you while you are AFK.",
        category = "Chat", subcategory = "Automatic"
    )
    var autoReplyAfk = false

    @Slider(
        title = "AFK Timeout",
        description = "How long you need to be inactive before being considered AFK in minutes.",
        category = "Chat", subcategory = "Automatic",
        min = 1f, max = 60f, step = 1f
    )
    var afkTimeout = 5f

    @Text(
        title = "AFK Reply Message",
        description = "Choose what message is sent when someone PMs you while you are AFK. \"%player%\" will be replaced with the player's name.",
        category = "Chat", subcategory = "Automatic"
    )
    var afkReplyMessage = "Hey %player%, I am currently AFK!"

    @Switch(
        title = "Game Status Restyle",
        description = "Replace common game status messages with a new style.\nExamples:\n+ Steve (1/12)\n- Steve\n⁎ Game starts in 5 seconds.",
        category = "Chat", subcategory = "Restyler"
    )
    var gameStatusRestyle = true

    @Switch(
        title = "Player Count Before Player Name",
        description = "Put the player count before the player name in game join/leave messages.\nExample: + (1/12) Steve",
        category = "Chat", subcategory = "Restyler"
    )
    var playerCountBeforePlayerName = true

    @Switch(
        title = "Player Count on Player Leave",
        description = "Include the player count when players leave.\nExample: - Steve (1/12)",
        category = "Chat", subcategory = "Restyler"
    )
    var playerCountOnPlayerLeave = true

    @Switch(
        title = "Player Count Padding",
        description = "Place zeros at the beginning of the player count to align with the max player count.\nExample: + Steve (001/100)",
        category = "Chat", subcategory = "Restyler"
    )
    var padPlayerCount = false

    @Switch(
        title = "Clean Separator Lines",
        description = "Make line separators smoother and properly trimmed to the width of chat.",
        category = "Chat", subcategory = "Visual"
    )
    var cleanSeparatorLines = true

    @Switch(
        title = "White Chat",
        description = "Make nons' chat messages appear as the normal chat message color.",
        category = "Chat", subcategory = "Visual"
    )
    var whiteChat = false

    @Switch(
        title = "White Private Messages",
        description = "Make private messages appear as the normal chat message color.",
        category = "Chat", subcategory = "Visual"
    )
    var whitePrivateMessages = true

    @Switch(
        title = "Colored Friend/Guild Statuses",
        description = "Colors the join/leave status of friends and guild members.",
        category = "Chat", subcategory = "Visual"
    )
    var coloredStatuses = true

    @Switch(
        title = "Compact Game Start Announcements",
        description = "Compacts game start/counting announcements.\nExample: The game starts in 20 seconds!",
        category = "Chat", subcategory = "Visual"
    )
    var compactGameStartAnnouncements = true

    @Switch(
        title = "Short Channel Names",
        description = "Abbreviate chat channel names.\nExample: Guild -> G, Party -> P, Friend -> F",
        category = "Chat", subcategory = "Visual"
    )
    var shortChannelNames = false

    @Switch(
        title = "Short Private Message Channel Names",
        description = "Abbreviate private message channel names.\nExample: To and From -> PM",
        category = "Chat", subcategory = "Visual"
    )
    var shortPMChannelNames = false

    @Switch(
        title = "Replace Chat Emotes",
        description = "Replace chat emotes.\nExample: ( ﾟ◡ﾟ)/",
        category = "Chat", subcategory = "Visual"
    )
    var replaceChatEmotes = false

    @RadioButton(
        title = "Chat Emotes Replacement Mode",
        description = "Choose how chat emotes are replaced.\nRemove Emote will completely remove emotes from messages.\nRemove Formatting will remove colors/formatting from emotes.\nReplace With Shortcuts will replace emotes with their shortcuts. Example: ( ﾟ◡ﾟ)/ -> o/",
        category = "Chat", subcategory = "Visual",
        options = ["Remove Emote", "Remove Formatting", "Replace With Shortcuts"],
    )
    var chatEmotesReplacementMode = 0

    @Switch(
        title = "Party Chat Swapper",
        description = "Automatically change to and out of a party channel when joining/leaving a party.",
        category = "Chat", subcategory = "Parties"
    )
    var chatSwapper = false

    @Dropdown(
        title = "Chat Swapper Channel",
        description = "The channel to return to when leaving a party.",
        category = "Chat", subcategory = "Parties",
        options = ["ALL", "GUILD", "OFFICER"]
    )
    var chatSwapperReturnChannel = 0

    @Switch(
        title = "Swap Chatting Tab",
        description = "Automatically switch your Chatting chat tab when Chat Swapper swaps your chat channel.",
        category = "Chat", subcategory = "Parties"
    )
    var chatSwapperChattingIntegration = false

    @Checkbox(
        title = "Remove All Chat Message",
        description = "Hide the \"You are now in the ALL channel\" message when auto-switching.",
        category = "Chat", subcategory = "Parties"
    )
    var chatSwapperHideAllChannelMsg = false

    @Switch(
        title = "Notify When Kicked From Game",
        description = "Notify in party chat when you are kicked from the game due to a connection issue.",
        category = "Chat", subcategory = "Parties"
    )
    var notifyWhenKick = false

    @Checkbox(
        title = "Put Notify Message In Capital Letters",
        description = "Put the message in capital messages instead of proper formatting.",
        category = "Chat", subcategory = "Parties"
    )
    var notifyWhenKickInCaps = false

    @Switch(
        title = "Broadcast Achievements",
        description = "Announce in Guild chat when you get an achievement.",
        category = "Chat", subcategory = "Guild"
    )
    var broadcastAchievements = false

    @Switch(
        title = "Broadcast Level Up",
        description = "Announce in Guild chat when you level up.",
        category = "Chat", subcategory = "Guild"
    )
    var broadcastLevelUp = false

    @Switch(
        title = "Guild Welcome Message",
        description = "Send a friendly welcome message when a player joins your guild.\nExample: Welcome to the guild Steve!",
        category = "Chat", subcategory = "Guild"
    )
    var guildWelcomeMessage = false

    @Switch(
        title = "Thank Watchdog",
        description = "Compliment Watchdog when someone is banned, or a Watchdog announcement is sent.\nExample: Thanks Watchdog!",
        category = "Chat", subcategory = "Watchdog"
    )
    var thankWatchdog = false

    @Switch(
        title = "Non Speech Cooldown",
        description = "Show the amount of time remaining until you can speak if you are a non.\nExample: Your freedom of speech is on cooldown. Please wait 3 more seconds.",
        category = "Chat", subcategory = "Cooldown"
    )
    var preventNonCooldown = false

    @Switch(
        title = "Shout Cooldown",
        description = "Show the amount of time remaining until /shout can be reused.\nExample: Shout command is on cooldown. Please wait 30 more seconds.",
        category = "Chat", subcategory = "Cooldown"
    )
    var preventShoutingOnCooldown = true

    @Switch(
        title = "Remove Karma Messages",
        description = "Remove Karma messages from the chat.",
        category = "Chat", subcategory = "Toggles"
    )
    var hideKarmaMessages = false

    @Switch(
        title = "Remove Lobby Join Messages",
        description = "Remove lobby join messages from chat.\nExample: [MVP+] Steve joined the lobby!",
        category = "Chat", subcategory = "Toggles"
    )
    var removeLobbyJoin = false

    @Switch(
        title = "Remove Ticket Machine Rewards",
        description = "Remove ticket machine messages from chat and only show your own.\nExample: Steve has found a COMMON Figurine",
        category = "Chat", subcategory = "Toggles"
    )
    var removeTicketMachineAnnouncements = false

    @Switch(
        title = "Remove Soul Well Announcements",
        description = "Remove soul well announcements from chat.\nExample: [MVP+] Steve has found a Bulldozer Perk I (Insane) in the Soul Well!",
        category = "Chat", subcategory = "Toggles"
    )
    var removeSoulWellAnnouncements = false

    @Switch(
        title = "Remove Game Announcements",
        description = "Remove game announcements from chat.\nExample: A Mega Skywars game is available to join! CLICK HERE to join!",
        category = "Chat", subcategory = "Toggles"
    )
    var removeGameAnnouncements = false

    @Switch(
        title = "Remove Hype Limit Reminder",
        description = "Remove Hype limit reminders from chat.\nExample: You have reached your Hype limit...",
        category = "Chat", subcategory = "Toggles"
    )
    var removeHypeLimitReminder = false

    @Switch(
        title = "Player AdBlocker",
        description = "Remove spam messages from players, usually advertising something or begging for ranks.",
        category = "Chat", subcategory = "Toggles"
    )
    var removePlayerAds = false

    @Switch(
        title = "Remove BedWars Advertisements",
        description = "Remove player messages asking to join BedWars parties.\nExample: [MVP+] Steve: Join BedWars 2/4 party!",
        category = "Chat", subcategory = "Toggles"
    )
    var removePlayerBedwarsAds = false

    @Switch(
        title = "Remove Friend/Guild Statuses",
        description = "Remove join/quit messages from friend/guild members.\nExample: Friend > Steve joined.",
        category = "Chat", subcategory = "Toggles"
    )
    var removeConnectionStatus = false

    @Switch(
        title = "Remove Guild MOTD",
        description = "Remove the guild Message Of The Day.",
        category = "Chat", subcategory = "Toggles"
    )
    var removeGuildMotd = false

    @Switch(
        title = "Remove Server Connected Messages",
        description = "Remove messages informing you of the lobby name you've just joined, or what lobby you're being sent to.\nExample: You are currently connected to server mini104H.",
        category = "Chat", subcategory = "Toggles"
    )
    var removedServerConnectedMsgs = false

    @Switch(
        title = "Remove Game Tips Messages",
        description = "Remove tips about the game you are playing.\nExample: Teaming is not allowed on Solo mode!",
        category = "Chat", subcategory = "Toggles"
    )
    var removeGameTips = false

    @Switch(
        title = "Remove Auto Activated Quest Messages",
        description = "Remove automatically activated quest messages.\nExample: Automatically activated: Daily Quest: Duels Winner",
        category = "Chat", subcategory = "Toggles"
    )
    var removeAutoQuests = false

    @Switch(
        title = "Remove Stats Messages",
        description = "Remove the \"view your stats\" messages.\nExample: Click to view the stats of your SkyWars game!",
        category = "Chat", subcategory = "Toggles"
    )
    var removeViewStats = false

    @Switch(
        title = "Remove Curse of Spam Messages",
        description = "Hides the constant spam of Kali's curse of spam.\nExample: KALI HAS STRIKEN YOU WITH THE CURSE OF SPAM",
        category = "Chat", subcategory = "Toggles"
    )
    var removeCurseOfSpam = false

    @Switch(
        title = "Remove Bridge Self Goal Death Messages",
        description = "Hides the death message when you jump into your own goal in Bridge.\nExample: You just jumped through your own goal, enjoy the void death! :)",
        category = "Chat", subcategory = "Toggles"
    )
    var removeBridgeOwnGoalDeathMsg = false

    @Switch(
        title = "Remove Duels No Stats Change Messages",
        description = "Hides the message explaining that your stats did not change for dueling through /duel or within in a party.\nExamples:\nYour stats did not change because you /duel'ed your opponent!\nYour stats did not change because you dueled someone in your party!\nNo stats will be affected in this round!",
        category = "Chat", subcategory = "Toggles"
    )
    var removeDuelsNoStatsChange = false

    @Switch(
        title = "Remove Block Trail Disabled Messages",
        description = "Hides the message explaining that your duel's block trail cosmetic was disabled in specific gamemodes.\nExample: Your block trail aura is disabled in this mode!",
        category = "Chat", subcategory = "Toggles"
    )
    var removeDuelsBlockTrailDisabled = false

    @Switch(
        title = "Remove SkyBlock Welcome Messages",
        description = "Removes \"Welcome to Hypixel SkyBlock!\" messages from chat.",
        category = "Chat", subcategory = "Toggles"
    )
    var removeSkyblockWelcome = false

    @Switch(
        title = "Remove Gift Messages",
        description = "Removes \"They have gifted x ranks so far!\" messages from chat.",
        category = "Chat", subcategory = "Toggles"
    )
    var removeGiftedRanksAmount = false

    @Switch(
        title = "Remove Seasonal Simulator Collection Messages",
        description = "Removes personal and global collected messages from chat for the Easter, Christmas, and Halloween variants.\nExamples:\nYou found a gift! (5 total)\n[MVP+] Steve has reached 20 gifts!",
        category = "Chat", subcategory = "Toggles"
    )
    var removeSimulatorCollectedMsgs = false

    @Switch(
        title = "Remove Earned Coins and Experience Messages",
        description = "Removes the earned coins and experience messages from chat.\nExamples:\n+25 Bed Wars Experience\n+10 coins!\nYou earned 500 GEXP from playing SkyBlock!",
        category = "Chat", subcategory = "Toggles"
    )
    var removeEarnedCoinsAndExp = false

    @Switch(
        title = "Remove Replay Messages",
        description = "Removes replay messages from chat.\nExample: This game has been recorded. Click here to watch the Replay!",
        category = "Chat", subcategory = "Toggles"
    )
    var removeReplayMessage = false

    @Switch(
        title = "Remove Tip Messages",
        description = "Removes tip messages from chat.\nExample: You tipped 5 players in 10 different games!",
        category = "Chat", subcategory = "Toggles"
    )
    var removeTipMessages = false

    @Switch(
        title = "Remove Online Status Messages",
        description = "Removes the online status messages from chat.\nExample: REMINDER: Your Online Status is currently set to Appear Offline",
        category = "Chat", subcategory = "Toggles"
    )
    var removeOnlineStatus = false

    @Switch(
        title = "Remove Main Lobby Fishing Announcements",
        description = "Removes Main Lobby Fishing announcements from chat when a player catches a special fish.\nExample: [MVP+] Steve caught Nemo! Maybe he's lost again?",
        category = "Chat", subcategory = "Toggles"
    )
    var removeLobbyFishingMsgs = false

    @Switch(
        title = "Remove Hot Potato Messages",
        description = "Removes Hot Potato messages from chat.\nExample: Steve burnt to a crisp due to a hot potato!",
        category = "Chat", subcategory = "Toggles"
    )
    var removeHotPotato = false

    @Switch(
        title = "Remove Discord Safety Warning Messages",
        description = "Removes \"Please be mindful of Discord links in chat as they may pose a security risk\"",
        category = "Chat", subcategory = "Toggles"
    )
    var removeDiscordSafetyWarning = false

    @Switch(
        title = "AutoWB",
        description = "Says a configurable message to your friends/guild when they join.",
        category = "Chat", subcategory = "AutoWB"
    )
    var autoWB = false

    @Checkbox(
        title = "Guild AutoWB",
        description = "Send messages in guild chat when a guild member joins.",
        category = "Chat", subcategory = "AutoWB"
    )
    var guildAutoWB = true

    @Checkbox(
        title = "Friend AutoWB",
        description = "Messages your friends when they join.",
        category = "Chat", subcategory = "AutoWB"
    )
    var friendsAutoWB = true

    @Slider(
        title = "AutoWB Delay",
        description = "Delay after a friend/guild member joins to say the message in seconds.",
        category = "Chat", subcategory = "AutoWB",
        min = 2f, max = 10f, step = 1f
    )
    var autoWBCooldown = 2f

    @Text(
        title = "AutoWB Message",
        description = "Choose what message is said when a friend/guild member joins. \"%player%\" will be replaced with the player's name.",
        category = "Chat", subcategory = "AutoWB"
    )
    var autoWBMessage1 = "Welcome Back!"

    @Switch(
        title = "Random AutoWB Messages",
        description = "Send a random message when a friend/guild member joins.",
        category = "Chat", subcategory = "AutoWB"
    )
    var randomAutoWB = false

    @Text(
        title = "First Random Message",
        category = "Chat", subcategory = "AutoWB"
    )
    var autoWBMessage2 = "Welcome back... General %player%"

    @Text(
        title = "Second Random Message",
        category = "Chat", subcategory = "AutoWB"
    )
    var autoWBMessage3 = "WB!"

    @Text(
        title = "Third Random Message",
        category = "Chat", subcategory = "AutoWB"
    )
    var autoWBMessage4 = "Greetings! %player%"

    @Text(
        title = "Fourth Random Message",
        category = "Chat", subcategory = "AutoWB"
    )
    var autoWBMessage5 = "Thanks for coming back to hell >:)"

    @Text(
        title = "Fifth Random Message",
        category = "Chat", subcategory = "AutoWB"
    )
    var autoWBMessage6 = "Its nice having you here today %player%"

    @Text(
        title = "Sixth Random Message",
        category = "Chat", subcategory = "AutoWB"
    )
    var autoWBMessage7 = "Yooooooooo Mr. %player%"

    @Text(
        title = "Seventh Random Message",
        category = "Chat", subcategory = "AutoWB"
    )
    var autoWBMessage8 = "Welcome back Padawan %player%"

    @Text(
        title = "Eighth Random Message",
        category = "Chat", subcategory = "AutoWB"
    )
    var autoWBMessage9 = "Welcome Back! <3"

    @Text(
        title = "Ninth Random Message",
        category = "Chat", subcategory = "AutoWB"
    )
    var autoWBMessage10 = "Thanks for coming to my TED talk."

    val wbMessages: List<String>
        get() = listOf(
            autoWBMessage1, autoWBMessage2, autoWBMessage3, autoWBMessage4, autoWBMessage5,
            autoWBMessage6, autoWBMessage7, autoWBMessage8, autoWBMessage9, autoWBMessage10
        )
    //endregion


    //? if forge {
    //@Exclude
    //?}
    private const val CATEGORY_DEBUG = "Debug"

    @Info(
        //? if forge {
        //text =
        //?} else {
        title =
        //?}
        "Probably should stay disabled",
        //? if forge {
        //type = InfoType.WARNING,
        //?}
        category = CATEGORY_DEBUG,
    )
    var debugModeInfo: Nothing? = null

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Debug Mode",
        category = CATEGORY_DEBUG,
    )
    var debugModeEnabled = false
}