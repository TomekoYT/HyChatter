package tomeko.hychatter.utils

import net.hypixel.modapi.HypixelModAPI
import net.hypixel.modapi.packet.impl.clientbound.ClientboundHelloPacket
import net.hypixel.modapi.packet.impl.clientbound.event.ClientboundLocationPacket
//? if ornithe {
//import net.ornithemc.osl.networking.api.client.ClientConnectionEvents
//?} else {
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents
//?}

object HypixelPackets {
    var onHypixel = false
        private set

    var currentServerName: String? = null
        private set

    var inLobby = false
        private set

    var inBedwars = false
        private set
    var inSkywars = false
        private set
    var inDuels = false
        private set
    var inArcade = false
        private set

    var inFarmHunt = false
        private set

    fun register() {
        HypixelModAPI.getInstance().createHandler(ClientboundHelloPacket::class.java, { onHypixel = true })
        //? if ornithe {
        //ClientConnectionEvents.DISCONNECT.register { disableHypixel() }
        //?} else {
        ClientPlayConnectionEvents.DISCONNECT.register { _, _ -> disableHypixel() }
        //?}
        HypixelModAPI.getInstance().createHandler(ClientboundLocationPacket::class.java, ::onLocationPacket)
        HypixelModAPI.getInstance().subscribeToEventPacket(ClientboundLocationPacket::class.java)
    }

    private fun disableHypixel() {
        onHypixel = false
        disableAll()
    }

    private fun onLocationPacket(packet: ClientboundLocationPacket) {
        Debug.log("onHypixel: $onHypixel")

        if (!packet.serverType.isPresent) {
            disableAll()
            return
        }

        currentServerName = packet.serverName

        val serverTypeName = packet.serverType.get().name
        Debug.log("serverTypeName: $serverTypeName <")

        inLobby = packet.lobbyName.isPresent

        inBedwars = serverTypeName == "Bed Wars"
        inSkywars = serverTypeName == "SkyWars"
        inDuels = serverTypeName == "Duels"
        inArcade = serverTypeName == "Arcade"

        if (!packet.mode.isPresent) {
            disableModes()
            return
        }

        val modeName = packet.mode.get()
        Debug.log("modeName: $modeName <")

        inFarmHunt = inArcade && modeName == "FARM_HUNT"
    }

    private fun disableAll() {
        currentServerName = null
        inLobby = false
        disableServerTypes()
        disableModes()
    }

    private fun disableServerTypes() {
        inBedwars = false
        inDuels = false
        inArcade = false
    }

    private fun disableModes() {
        inFarmHunt = false
    }
}