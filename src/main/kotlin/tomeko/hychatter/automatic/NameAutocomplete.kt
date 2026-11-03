package tomeko.hychatter.automatic

//? if 1.8.9 {
/*import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiChat
//? if forge {
//import net.minecraftforge.client.ClientCommandHandler
//?}
import tomeko.hychatter.event.ClientSendMessageEvents
import tomeko.hychatter.event.ClientTickEvents
*///?} else {
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents
//?}
import net.hypixel.modapi.HypixelModAPI
import net.hypixel.modapi.packet.impl.clientbound.ClientboundHelloPacket
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.utils.HypixelPackets

object NameAutocomplete {
    private val active: Boolean
        get() = HyChatterConfig.autocompletePlayerNames && HypixelPackets.onHypixel

    fun register() {
        NameHistory.register()

        ClientSendMessageEvents.COMMAND.register(::onCommandSend)
        HypixelModAPI.getInstance().createHandler(ClientboundHelloPacket::class.java) {
            if (HyChatterConfig.autocompletePlayerNames) GuildMembers.refresh()
        }
        //? if 1.8.9
        //ClientTickEvents.END_CLIENT_TICK.register(::onClientTick)
    }

    private fun onCommandSend(command: String) {
        if (!active) return

        NameHistory.capture("/$command")
    }

    fun complete(input: String, cursor: Int): AutocompleteCore.Completion? {
        if (!active) return null

        return AutocompleteCore.complete(input, cursor, GuildMembers.members() + NameHistory.names())
    }

    //? if 1.8.9 {
    /*private var pendingScreen: GuiChat? = null
    private var pendingOptions: Array<String>? = null

    fun completeTab(screen: GuiChat, textBeforeCursor: String, word: String): Boolean {
        val options = complete(textBeforeCursor, textBeforeCursor.length)?.options
        if (options.isNullOrEmpty()) return false

        //? if forge {
        //ClientCommandHandler.instance.autoComplete(textBeforeCursor, word)
        //?}
        pendingScreen = screen
        pendingOptions = options.toTypedArray()
        return true
    }

    private fun onClientTick(mc: Minecraft) {
        val screen = pendingScreen ?: return
        val options = pendingOptions ?: return
        pendingScreen = null
        pendingOptions = null

        if (mc.currentScreen === screen) screen.onAutocompleteResponse(options)
    }
    *///?}
}
