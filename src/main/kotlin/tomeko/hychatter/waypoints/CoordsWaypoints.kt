package tomeko.hychatter.waypoints

//? if 1.8.9 {
/*import net.minecraft.util.BlockPos
import net.minecraft.util.IChatComponent as Component
*///?} else {
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
//?}
import tomeko.hychatter.config.HyChatterConfig
//? if ornithe {
//import tomeko.hychatter.event.ClientReceiveMessageEvents
//?}
import tomeko.hychatter.utils.removeFormatting
import kotlin.text.get

object CoordsWaypoints {
    fun register() {
        ClientReceiveMessageEvents.GAME.register(::onChatReceive)
    }

    private fun onChatReceive(component: Component, fromActionBar: Boolean) {
        if (fromActionBar || !HyChatterConfig.coordsWaypointsEnabled) return

        val message =
            //? if ornithe {
            //component.unformattedText.removeFormatting()
        //?} else {
        component.string.removeFormatting()
        //?}

        val regex = Regex(
            "^(?:\\w+\\s*>\\s*)?" +
                    "(?:[^:\\s]+\\s+)*" +
                    "(?:<(?<owner1>\\w+)>|(?<owner2>\\w+):)\\s*" +
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
                boxColor = HyChatterConfig.coordsWaypointsBoxColor,
                beamColor = HyChatterConfig.coordsWaypointsBeamColor,
                owner = owner,
                renderOwner = HyChatterConfig.coordsWaypointsRenderOwner,
                ownerColor = HyChatterConfig.coordsWaypointsOwnerColor,
                text = text,
                renderText = HyChatterConfig.coordsWaypointsRenderText,
                textColor = HyChatterConfig.coordsWaypointsTextColor,
                renderDistance = HyChatterConfig.coordsWaypointsRenderDistance,
                distanceTextColor = HyChatterConfig.coordsWaypointsDistanceTextColor,
                tickTime = 20 * HyChatterConfig.coordsWaypointsTime
            )
        )
    }
}