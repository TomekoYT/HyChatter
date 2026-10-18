package tomeko.hychatter.commands

//? if forge {
/*import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.util.BlockPos
import net.minecraftforge.client.ClientCommandHandler
*///?} elif ornithe {
/*import com.mojang.brigadier.arguments.StringArgumentType
import org.polyfrost.oneconfig.api.commands.v1.CommandManager.argument
import org.polyfrost.oneconfig.api.commands.v1.CommandManager.literal
import org.polyfrost.oneconfig.internal.legacy.command.ClientCommandRegistrationCallback
*///?} else {
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.tree.LiteralCommandNode
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.command.v2.ClientCommands.argument
import net.fabricmc.fabric.api.client.command.v2.ClientCommands.literal
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
//?}
//? if 1.8.9 {
/*import net.minecraft.network.play.client.C01PacketChatMessage
import net.minecraft.util.ChatComponentText
import net.minecraft.util.EnumChatFormatting
*///?} else {
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
//?}
import com.google.gson.JsonParser
import net.minecraft.client.Minecraft
import tomeko.hychatter.config.HyChatterConfig
import tomeko.hychatter.utils.Debug
import tomeko.hychatter.utils.HypixelPackets
import java.net.URI
import java.util.Locale
import kotlin.concurrent.thread

object PlayCommand
//? if forge
//: CommandBase()
{
    @Volatile private var games: Map<String, String> = emptyMap()
    @Volatile private var gameList: List<String> = emptyList()

    fun register() {
        thread(isDaemon = true) {
            try {
                val json =
                    //? if 1.8.9
                    //JsonParser().parse(URI.create("https://data-v2.polyfrost.org/hytils/game_aliases.json").toURL().readText())
                    //? else
                    JsonParser.parseString(URI.create("https://data-v2.polyfrost.org/hytils/game_aliases.json").toURL().readText())
                games = json.asJsonObject.entrySet().associate { it.key to it.value.asString }
                gameList = (games.keys + games.values).distinct()
            } catch (e: Exception) {
                Debug.forceLog("Failed to fetch /play game list: $e")
            }
        }

        //? if forge {
        //ClientCommandHandler.instance.registerCommand(this)
        //?} else {
        ClientCommandRegistrationCallback.EVENT.register { dispatcher, _ -> dispatcher.register(command()) }
        //?}
    }

    //? if !forge {
    private fun command() = literal("play")
        .executes {
            showError("Usage: /play <game>")
            1
        }
        .then(
            argument("game", StringArgumentType.word())
                .suggests { _, builder ->
                    complete(builder.remaining).forEach { builder.suggest(it) }
                    builder.buildFuture()
                }
                .executes { ctx ->
                    play(StringArgumentType.getString(ctx, "game"))
                    1
                }
        )
    //?}

    //? if fabric
    @JvmStatic val commandNode: LiteralCommandNode<FabricClientCommandSource> by lazy { command().build() }

    //? if forge {
    /*override fun getCommandName(): String = "play"

    override fun getCommandUsage(sender: ICommandSender): String = "/play <game>"

    override fun getRequiredPermissionLevel(): Int = 0

    override fun processCommand(sender: ICommandSender, args: Array<String>) {
        if (args.isEmpty()) showError("Usage: /play <game>") else play(args[0])
    }

    override fun addTabCompletionOptions(sender: ICommandSender, args: Array<String>, pos: BlockPos): List<String>? =
        if (args.size == 1) complete(args[0]) else null
    *///?}

    private fun complete(input: String): List<String> {
        if (!HypixelPackets.onHypixel || !HyChatterConfig.autocompletePlayCommands) return emptyList()
        return gameList.filter { it.startsWith(input) }
    }

    private fun play(game: String) {
        if (!HypixelPackets.onHypixel) {
            send("/play $game")
            return
        }

        var command = game
        if (HyChatterConfig.autocompletePlayCommands && games.isNotEmpty()) {
            val lowercase = game.lowercase(Locale.ENGLISH)
            command = games[lowercase] ?: if (games.containsValue(lowercase)) game else {
                showError("Invalid game: \"$game\"")
                return
            }
        }

        send("/play $command")
    }

    private fun send(command: String) {
        //? if 1.8.9
        //Minecraft.getMinecraft().thePlayer?.sendQueue?.addToSendQueue(C01PacketChatMessage(command))
        //? else
        Minecraft.getInstance().player?.connection?.sendCommand(command.removePrefix("/"))
    }

    private fun showError(message: String) {
        //? if 1.8.9
        //Minecraft.getMinecraft().thePlayer?.addChatMessage(ChatComponentText("${EnumChatFormatting.RED}$message"))
        //? elif >= 26.2
        Minecraft.getInstance().gui.hud.chat.addClientSystemMessage(Component.literal(message).withStyle(ChatFormatting.RED))
        //? else
        //Minecraft.getInstance().gui.chat.addClientSystemMessage(Component.literal(message).withStyle(ChatFormatting.RED))
    }
}
