package tomeko.hychatter.chat

//? if 1.8.9 {
/*import tomeko.hychatter.utils.LegacyComponents
import tomeko.hychatter.utils.string
import tomeko.hychatter.utils.siblings
import tomeko.hychatter.utils.style
import tomeko.hychatter.utils.plainCopy
import tomeko.hychatter.utils.withStyle
import tomeko.hychatter.utils.append
import tomeko.hychatter.utils.copy
import net.minecraft.util.EnumChatFormatting
import net.minecraft.util.IChatComponent as Component
*///?} else {
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
//?}
//? if ornithe {
//import tomeko.hychatter.event.ClientReceiveMessageEvents
//?} else {
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
//?}
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.config.data.providers.LanguageData

object ColoredPlayerConnectionStatus {
    fun register() {
        ClientReceiveMessageEvents.MODIFY_GAME.register(::onGameMessage)
    }

    private fun onGameMessage(component: Component, fromActionBar: Boolean): Component {
        if (fromActionBar || !HyChatterConfig.coloredStatuses) return component

        val match = LanguageData.PLAYER_CONNECTION_STATUS.find(component.string) ?: return component
        val status = match.groups["status"]?.value ?: return component
        val isJoin = status == "joined"

        return restyle(component, status, isJoin)
    }

    private fun restyle(component: Component, status: String, isJoin: Boolean): Component =
        //? if 1.8.9 {
        /*component.plainCopy().withStyle(component.style)
            .append(component.siblings.first())
            .append(LegacyComponents.literal(status).withStyle(if (isJoin) EnumChatFormatting.GREEN else EnumChatFormatting.RED))
            .append(LegacyComponents.literal(".").withStyle(EnumChatFormatting.YELLOW))
        *///?} else {
        component.plainCopy().withStyle(component.style)
            .append(component.siblings.first())
            .append(Component.literal(status).withStyle(if (isJoin) ChatFormatting.GREEN else ChatFormatting.RED))
            .append(Component.literal(".").withStyle(ChatFormatting.YELLOW))
        //?}
}
