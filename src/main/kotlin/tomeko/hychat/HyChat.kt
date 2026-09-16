package tomeko.hychat

//? if forge {
/*import cc.polyfrost.oneconfig.events.EventManager
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
*///?} elif ornithe {
//import net.ornithemc.osl.entrypoints.api.ModInitializer
//?} else {
import net.fabricmc.api.ClientModInitializer
import tomeko.hychat.utils.Constants
import tomeko.hychat.utils.Debug
import tomeko.hychat.utils.WaypointRenderer
//?}
import tomeko.hychat.chat.*
import tomeko.hychat.commands.*
import tomeko.hychat.config.*
import tomeko.hychat.location.*
import tomeko.hychat.utils.*

//? if forge {
/*@Mod(
    modid = Constants.MOD_ID,
    name = Constants.MOD_NAME,
    version = Constants.MOD_VERSION,
    modLanguageAdapter = "cc.polyfrost.oneconfig.utils.KotlinLanguageAdapter",
    dependencies = "required-after:hypixel_mod_api"
)
*///?}
class HyChat
//? if ornithe {
//: ModInitializer
//?} elif fabric {
    : ClientModInitializer
//?}
{
    //? if forge {
    //@Mod.EventHandler
    //?} else {
    override
    //?}
    fun
    //? if ornithe {
    //init(
    //?} else {
            onInitializeClient(
        //?}
        //? if forge {
        //event: FMLInitializationEvent
        //?}
    ) {
        //? if forge {
        //EventManager.INSTANCE.register(this)
        //?}

        CoordsWaypoints.register()
        DangerousTauntWaypoint.register()
        HideGuildMOTD.register()
        //? if fabric {
        MVPEmoji.register()
        //?}
        WhiteChatMessages.register()

        HyChatCommand.register()
        SendCoordsCommand.register()

        HyChatConfig.register()

        HypixelPackets.register()

        WaypointRenderer.register()

        Debug.forceLog("${Constants.MOD_VERSION} Initialized!")
    }
}