package tomeko.hychatter

//? if ornithe {
//import net.ornithemc.osl.entrypoints.api.ModInitializer
//?} else {
import net.fabricmc.api.ClientModInitializer
//?}
import org.polyfrost.oneconfig.api.event.v1.EventManager
import tomeko.hychatter.chat.*
import tomeko.hychatter.commands.*
import tomeko.hychatter.config.*
import tomeko.hychatter.config.chat.handlers.ChatHandler
import tomeko.hychatter.location.*
import tomeko.hychatter.utils.*

class HyChatter
//? if ornithe {
//: ModInitializer
//?} elif fabric {
    : ClientModInitializer
//?}
{
    override fun
    //? if ornithe {
    //init(
    //?} else {
            onInitializeClient(
        //?}
    ) {
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