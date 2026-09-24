package tomeko.hychatter.chat

//? if 1.8.9 {
//import net.minecraft.util.IChatComponent as Component
//?} else {
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
import net.minecraft.network.chat.Component
//?}
//? if ornithe {
//import tomeko.hychatter.event.ClientReceiveMessageEvents
//?}
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.data.providers.LanguageData

object AntiGG {
    fun register() {
        //? if ornithe {
        //ClientReceiveMessageEvents.ALLOW_CHAT.register(::onChatReceive)
        //?} else {
        ClientReceiveMessageEvents.ALLOW_CHAT.register { component, _, _, _, _ -> onChatReceive(component) }
        //?}
    }

    private fun onChatReceive(component: Component): Boolean {
        if (!HyChatterConfig.antiGG) return true

        val message =
            //? if ornithe {
            //component.unformattedText
            //?} else {
            component.string
            //?}

        if (message.matches(LanguageData.GG_MESSAGES)) {
            return false
        }

        return true
    }
}
