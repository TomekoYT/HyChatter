package tomeko.hychatter.mixins;

//? if fabric {
import com.mojang.blaze3d.platform.FramerateLimitTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FramerateLimitTracker.class)
public interface FramerateLimitTrackerAccessor {
    @Accessor
    long getLatestInputTime();
}
//?}