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
import tomeko.hychatter.config.LanguageData

object BridgeOwnGoalDeathRemover {
    fun register() {
        ClientReceiveMessageEvents.ALLOW_GAME.register(::onGameReceive)
    }

    private fun onGameReceive(component: Component, fromActionBar: Boolean): Boolean {
        if (fromActionBar || !HyChatterConfig.removeBridgeOwnGoalDeathMsg) return true

        val message =
            //? if ornithe {
            //component.unformattedText
            //?} else {
            component.string
            //?}

if (message == LanguageData.BRIDGE_OWN_GOAL) {
            return false
        }

        return true
    }
}
