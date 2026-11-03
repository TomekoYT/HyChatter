package tomeko.hychatter.commands

//? if forge {
/*import net.minecraft.client.Minecraft
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.util.BlockPos
import net.minecraft.util.ChatComponentText
import net.minecraft.util.ChatStyle
import net.minecraft.util.EnumChatFormatting
import net.minecraftforge.client.ClientCommandHandler
*///?} elif ornithe {
/*import com.mojang.brigadier.arguments.StringArgumentType
import net.minecraft.client.Minecraft
import net.minecraft.util.ChatComponentText
import net.minecraft.util.ChatStyle
import net.minecraft.util.EnumChatFormatting
import org.polyfrost.oneconfig.api.commands.v1.CommandManager.argument
import org.polyfrost.oneconfig.api.commands.v1.CommandManager.literal
import org.polyfrost.oneconfig.internal.legacy.command.ClientCommandRegistrationCallback
*///?} else {
import com.mojang.brigadier.arguments.StringArgumentType
import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.Component
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.command.v2.ClientCommands.argument
import net.fabricmc.fabric.api.client.command.v2.ClientCommands.literal
//?}
import tomeko.hychatter.automatic.NameHistory

object DelNameCommand
//? if forge
//: CommandBase()
{
    private const val USAGE = "Usage: /delname <name> [name...]"

    fun register() {
        //? if forge {
        //ClientCommandHandler.instance.registerCommand(this)
        //?} else {
        ClientCommandRegistrationCallback.EVENT.register { dispatcher, _ -> dispatcher.register(command()) }
        //?}
    }

    //? if !forge {
    private fun command() = literal("delname")
        .executes {
            //? if 1.8.9
            //Minecraft.getMinecraft().thePlayer?.addChatMessage(ChatComponentText(USAGE).setChatStyle(ChatStyle().setColor(EnumChatFormatting.RED)))
            //? elif >= 26.2
            Minecraft.getInstance().gui.hud.chat.addClientSystemMessage(Component.literal(USAGE).withStyle(ChatFormatting.RED))
            //? else
            //Minecraft.getInstance().gui.chat.addClientSystemMessage(Component.literal(USAGE).withStyle(ChatFormatting.RED))
            1
        }
        .then(
            argument("names", StringArgumentType.greedyString())
                .suggests { _, builder ->
                    val remaining = builder.remaining
                    val lastSpace = remaining.lastIndexOf(' ')
                    val prefix = remaining.substring(0, lastSpace + 1)
                    complete(prefix.trim().split(Regex("\\s+")), remaining.substring(lastSpace + 1))
                        .forEach { builder.suggest(prefix + it) }
                    builder.buildFuture()
                }
                .executes { ctx ->
                    delete(StringArgumentType.getString(ctx, "names").trim().split(Regex("\\s+")))
                    1
                }
        )
    //?}

    //? if forge {
    /*override fun getCommandName(): String = "delname"

    override fun getCommandUsage(sender: ICommandSender): String = USAGE

    override fun getRequiredPermissionLevel(): Int = 0

    override fun processCommand(sender: ICommandSender, args: Array<String>) {
        if (args.isEmpty()) Minecraft.getMinecraft().thePlayer?.addChatMessage(ChatComponentText(USAGE).setChatStyle(ChatStyle().setColor(EnumChatFormatting.RED))) else delete(args.toList())
    }

    override fun addTabCompletionOptions(sender: ICommandSender, args: Array<String>, pos: BlockPos): List<String>? =
        complete(args.dropLast(1), args.lastOrNull() ?: "")
    *///?}

    private fun complete(alreadyTyped: List<String>, current: String): List<String> {
        val typed = alreadyTyped.map { it.lowercase() }.toSet()
        return NameHistory.names()
            .filter { it.startsWith(current, ignoreCase = true) && it.lowercase() !in typed }
            .sortedWith(String.CASE_INSENSITIVE_ORDER)
    }

    private fun delete(names: List<String>) {
        val given = names.filter { it.isNotEmpty() }
        val removed = given.count { NameHistory.remove(it) }
        val notFound = given.size - removed

        when {
            removed > 0 && notFound == 0 ->
                //? if 1.8.9
                //Minecraft.getMinecraft().thePlayer?.addChatMessage(ChatComponentText("Removed $removed username${if (removed == 1) "" else "s"} from suggestion history").setChatStyle(ChatStyle().setColor(EnumChatFormatting.GREEN)))
                //? elif >= 26.2
                Minecraft.getInstance().gui.hud.chat.addClientSystemMessage(Component.literal("Removed $removed username${if (removed == 1) "" else "s"} from suggestion history").withStyle(ChatFormatting.GREEN))
                //? else
                //Minecraft.getInstance().gui.chat.addClientSystemMessage(Component.literal("Removed $removed username${if (removed == 1) "" else "s"} from suggestion history").withStyle(ChatFormatting.GREEN))
            removed == 0 && notFound > 0 ->
                //? if 1.8.9
                //Minecraft.getMinecraft().thePlayer?.addChatMessage(ChatComponentText("No usernames found in suggestion history").setChatStyle(ChatStyle().setColor(EnumChatFormatting.RED)))
                //? elif >= 26.2
                Minecraft.getInstance().gui.hud.chat.addClientSystemMessage(Component.literal("No usernames found in suggestion history").withStyle(ChatFormatting.RED))
                //? else
                //Minecraft.getInstance().gui.chat.addClientSystemMessage(Component.literal("No usernames found in suggestion history").withStyle(ChatFormatting.RED))
            removed > 0 ->
                //? if 1.8.9
                //Minecraft.getMinecraft().thePlayer?.addChatMessage(ChatComponentText("Removed $removed username${if (removed == 1) "" else "s"}, $notFound not found").setChatStyle(ChatStyle().setColor(EnumChatFormatting.GREEN)))
                //? elif >= 26.2
                Minecraft.getInstance().gui.hud.chat.addClientSystemMessage(Component.literal("Removed $removed username${if (removed == 1) "" else "s"}, $notFound not found").withStyle(ChatFormatting.GREEN))
                //? else
                //Minecraft.getInstance().gui.chat.addClientSystemMessage(Component.literal("Removed $removed username${if (removed == 1) "" else "s"}, $notFound not found").withStyle(ChatFormatting.GREEN))
            else ->
                //? if 1.8.9
                //Minecraft.getMinecraft().thePlayer?.addChatMessage(ChatComponentText(USAGE).setChatStyle(ChatStyle().setColor(EnumChatFormatting.RED)))
                //? elif >= 26.2
                Minecraft.getInstance().gui.hud.chat.addClientSystemMessage(Component.literal(USAGE).withStyle(ChatFormatting.RED))
                //? else
                //Minecraft.getInstance().gui.chat.addClientSystemMessage(Component.literal(USAGE).withStyle(ChatFormatting.RED))
        }
    }
}
