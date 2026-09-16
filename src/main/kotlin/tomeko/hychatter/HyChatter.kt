package tomeko.hychatter

//? if forge {
/*import cc.polyfrost.oneconfig.events.EventManager
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
*///?} elif ornithe {
/*import net.ornithemc.osl.entrypoints.api.ModInitializer
import org.polyfrost.oneconfig.api.event.v1.EventManager
*///?} else {
import net.fabricmc.api.ClientModInitializer
import org.polyfrost.oneconfig.api.event.v1.EventManager
//?}
import tomeko.hychatter.chat.*
import tomeko.hychatter.commands.*
import tomeko.hychatter.config.*
import tomeko.hychatter.config.chat.handlers.ChatHandler
import tomeko.hychatter.location.*
import tomeko.hychatter.utils.*

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

        HyChatterCommand.register()
        SendCoordsCommand.register()

        EventManager.INSTANCE.register(ChatHandler)
        HyChatterConfig.register()

        HypixelPackets.register()

        WaypointRenderer.register()

        Debug.forceLog("${Constants.MOD_VERSION} Initialized!")
    }
}