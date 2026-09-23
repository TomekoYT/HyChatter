package tomeko.hychatter.commands

//? if fabric {
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.command.v2.ClientCommands.literal
//?} elif ornithe {
/*import net.ornithemc.osl.lifecycle.api.client.MinecraftClientEvents
import org.polyfrost.oneconfig.api.commands.v1.CommandManager.literal
*///?}
import org.polyfrost.oneconfig.utils.v1.dsl.openUI
//? if ornithe {
//import org.polyfrost.oneconfig.internal.legacy.command.ClientCommandRegistrationCallback
//?}
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.utils.Constants

object HyChatterCommand {
    private var shouldOpenConfig: Boolean = false

    fun register() {
        ClientCommandRegistrationCallback.EVENT.register { dispatcher, _ ->
            dispatcher.register(
                literal(Constants.MOD_ID)
                    .executes { _ ->
                        shouldOpenConfig = true
                        return@executes 1
                    }
            )
        }

        //? if ornithe {
        //MinecraftClientEvents.TICK_END.register {
            //?} else {
        ClientTickEvents.END_CLIENT_TICK.register {
            //?}
            if (!shouldOpenConfig) return@register

            HyChatterConfig.openUI()

            shouldOpenConfig = false
        }
    }
}