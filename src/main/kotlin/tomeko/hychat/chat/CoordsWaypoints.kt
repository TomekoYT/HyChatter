package tomeko.hychat.chat

//? if 1.8.9 {
/*import net.minecraft.util.BlockPos
//? if forge {
/*import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
*///?} else {
import net.minecraft.util.IChatComponent as Component
//?}
*///?} else {
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
//?}
import tomeko.hychat.config.HyChatConfig
//? if ornithe {
//import tomeko.hychat.event.ClientReceiveMessageEvents
//?}
import tomeko.hychat.utils.Waypoint
import tomeko.hychat.utils.WaypointRenderer
import tomeko.hychat.utils.removeFormatting

object CoordsWaypoints {
    fun register() {
        //? if forge {
        //MinecraftForge.EVENT_BUS.register(this)
        //?} else {
        ClientReceiveMessageEvents.GAME.register(CoordsWaypoints::onChatReceive)
        //?}
    }

    //? if forge {
    //@SubscribeEvent
    //?}
    fun onChatReceive(
        //? if forge {
        //event: ClientChatReceivedEvent
        //?} else {
        component: Component, fromActionBar: Boolean
        //?}
    ) {
        //? if forge {
        //if (event.type.toInt() == 2 || event.message == null) return
        //?} else {
        if (fromActionBar) return
        //?}

        if (!HyChatConfig.coordsWaypointsEnabled) return

        val message =
        //? if forge {
        //event.message.unformattedText.removeFormatting()
            //? elif ornithe {
            //component.unformattedText.removeFormatting()
        //?} else {
        component.string.removeFormatting()
        //?}

        val regex = Regex(
            "^(?:\\w+\\s*>\\s*)?" +
                    "(?:\\[[^]]+]\\s*)*" +
                    "(?:<(?<owner1>\\w+)>|(?<owner2>\\w+)[^:]*:)\\s*" +
                    "x:\\s*(?<x>-?\\d+),\\s*" +
                    "y:\\s*(?<y>-?\\d+),\\s*" +
                    "z:\\s*(?<z>-?\\d+)" +
                    "(?:\\s*(?:\\|\\s*)?(?<text>.*))?$"
        )

        val match = regex.matchEntire(message) ?: return

        val owner = match.groups["owner1"]?.value
            ?: match.groups["owner2"]!!.value

        val x = match.groups["x"]!!.value.toInt()
        val y = match.groups["y"]!!.value.toInt()
        val z = match.groups["z"]!!.value.toInt()
        val text = match.groups["text"]?.value.orEmpty()

        WaypointRenderer.waypoints.add(
            Waypoint(
                pos = BlockPos(x, y, z),
                boxColor = HyChatConfig.coordsWaypointsBoxColor,
                beamColor = HyChatConfig.coordsWaypointsBeamColor,
                owner = owner,
                renderOwner = HyChatConfig.coordsWaypointsRenderOwner,
                ownerColor = HyChatConfig.coordsWaypointsOwnerColor,
                text = text,
                renderText = HyChatConfig.coordsWaypointsRenderText,
                textColor = HyChatConfig.coordsWaypointsTextColor,
                renderDistance = HyChatConfig.coordsWaypointsRenderDistance,
                distanceTextColor = HyChatConfig.coordsWaypointsDistanceTextColor,
                tickTime = 20 * HyChatConfig.coordsWaypointsTime
            )
        )
    }
}