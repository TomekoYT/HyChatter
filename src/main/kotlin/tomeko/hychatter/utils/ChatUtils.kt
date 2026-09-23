package tomeko.hychatter.utils

//? if 1.8.9 {
/*import net.minecraft.client.Minecraft
import net.minecraft.util.IChatComponent
//? if ornithe {
/*import org.polyfrost.oneconfig.api.event.v1.EventManager
import org.polyfrost.oneconfig.api.event.v1.events.TickEvent
*///?}
import java.util.concurrent.ConcurrentLinkedQueue

object ChatUtils {
    val mc = Minecraft.getMinecraft()
    private val queue = ConcurrentLinkedQueue<Pair<String, Int>>()
    private var tickCounter = 0L

    init {
        EventManager.register(TickEvent.Start::class) {
            onTick()
        }
    }

    private fun onTick() {
        if (queue.isEmpty()) {
            tickCounter = 0
            return
        }

        tickCounter++

        if (mc.thePlayer == null) return
        val (message, delay) = queue.peek() ?: return

        if (delay != 0 && tickCounter < delay) return

        sendMessage(message)
        queue.poll()
        tickCounter = 0
    }

    fun sendMessage(message: String) {
        mc.thePlayer?.sendChatMessage(message)
    }

    fun queueMessage(message: String, delay: Int = 0) {
        require(delay >= 0) { "Delay must be non-negative" }
        queue.add(message to delay)
    }

    fun displayMessage(message: IChatComponent) {
        mc.thePlayer?.addChatMessage(message)
    }
}
*///?} else {
import net.minecraft.network.chat.Component
import org.polyfrost.oneconfig.api.event.v1.EventManager
import org.polyfrost.oneconfig.api.event.v1.events.TickEvent
import org.polyfrost.oneconfig.utils.v1.dsl.mc
import java.util.concurrent.ConcurrentLinkedQueue

object ChatUtils {
    private val queue = ConcurrentLinkedQueue<Pair<String, Int>>()
    private var tickCounter = 0L

    init {
        EventManager.register(TickEvent.Start::class) {
            if (queue.isEmpty()) {
                tickCounter = 0
                return@register
            }

            tickCounter++

            if (mc.player == null) return@register
            val (message, delay) = queue.peek() ?: return@register
            if (delay % tickCounter != 0L) return@register

            sendMessage(message)
            queue.poll()
            tickCounter = 0
        }
    }

    fun sendMessage(message: String) {
        mc.player?.connection?.sendChat(message)
    }

    fun queueMessage(message: String, delay: Int = 0) {
        require(delay >= 0) { "Delay must be non-negative" }
        queue.add(message to delay)
    }

    fun displayMessage(message: Component) {
        //~ if <26.1 'sendSystemMessage' -> 'displayClientMessage'
        mc.player?.sendSystemMessage(message, /*? if <26.1 {*//*false *//*?}*/)
    }
}
//?}