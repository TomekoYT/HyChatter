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