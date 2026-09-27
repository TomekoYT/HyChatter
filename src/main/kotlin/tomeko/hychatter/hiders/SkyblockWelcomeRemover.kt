package tomeko.hychatter.hiders

//? if fabric {
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
import net.minecraft.network.chat.Component
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.LanguageData
import tomeko.hychatter.utils.HypixelPackets

object SkyblockWelcomeRemover {
    fun register() {
        ClientReceiveMessageEvents.ALLOW_GAME.register(::onGameReceive)
    }

    private fun onGameReceive(component: Component, fromActionBar: Boolean): Boolean {
        if (fromActionBar || !HyChatterConfig.removeSkyblockWelcome || !HypixelPackets.inSkyblock) return true

        val message =
            //? if 1.8.9 {
            //component.unformattedText
            //?} else {
            component.string
            //?}

        return message != LanguageData.SKYBLOCK_WELCOME
    }
}
//?}