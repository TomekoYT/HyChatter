package tomeko.hychat.config.events

//? if 1.8.9 {
/*import net.minecraft.client.audio.ISound
import org.polyfrost.oneconfig.api.event.v1.events.Event

data class SoundPlayEvent(val sound: ISound) : Event.Cancellable()
*///?} else {
import net.minecraft.client.resources.sounds.SoundInstance
import org.polyfrost.oneconfig.api.event.v1.events.Event

// oneconfig SoundPlayedEvent cannot reliably cancel sounds on these versions so we use our own
data class SoundPlayEvent(val sound: SoundInstance) : Event.Cancellable()
//?}