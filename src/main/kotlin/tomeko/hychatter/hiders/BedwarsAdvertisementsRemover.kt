package tomeko.hychatter.hiders

//? if 1.8.9 {
//import net.minecraft.util.IChatComponent as Component
//?} else {
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
import net.minecraft.network.chat.Component
//?}
//? if 1.8.9 {
//import tomeko.hychatter.event.ClientReceiveMessageEvents
//?}
import net.minecraft.client.Minecraft
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.LanguageData
import tomeko.hychatter.utils.HypixelPackets

object BedwarsAdvertisementsRemover {
    fun register() {
        //? if 1.8.9 {
        //ClientReceiveMessageEvents.ALLOW_CHAT.register(::onChatReceive)
        //?} else {
        ClientReceiveMessageEvents.ALLOW_CHAT.register { component, _, _, _, _ -> onChatReceive(component) }
        //?}
    }

    private fun onChatReceive(component: Component): Boolean {
        if (!HyChatterConfig.removePlayerBedwarsAds || !HypixelPackets.onHypixel || !HypixelPackets.inBedwars || !HypixelPackets.inLobby) return true

        val message =
        //? if 1.8.9 {
        //component.unformattedText
            //?} else {
            component.string
        //?}

        if ((message.startsWith("-") && message.endsWith("-"))
            || (message.startsWith("▬") && message.endsWith("▬"))
            || (message.startsWith("≡") && message.endsWith("≡"))
            || !message.contains(": ")
            || message.contains(
                //? if 1.8.9
                //Minecraft.getMinecraft().thePlayer.name,
                //? else
                Minecraft.getInstance().player?.name?.string ?: return true,
                true
            )
        ) return true

        return message.contains(LanguageData.CHAT_BEDWARS_ADVERTISEMENT)
    }
}
