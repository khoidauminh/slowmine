package dev.sillibeans.slowmine.mixin;

import dev.sillibeans.slowmine.SlowMine;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class MiningSpeedMixin {
    @Inject(method = "getDestroySpeed", at = @At("RETURN"), cancellable = true)
    public void setDestroySpeed(CallbackInfoReturnable<Float> cir) {
        Level level = ((Player) (Object) this).level();

        final var diff = level.getDifficulty();

        final float scale = switch (diff) {
            case Difficulty.PEACEFUL -> SlowMine.CONFIG.speed_peaceful;
            case Difficulty.EASY -> SlowMine.CONFIG.speed_easy;
            case Difficulty.HARD -> SlowMine.CONFIG.speed_hard;
            default -> SlowMine.CONFIG.speed_normal;
        };

        cir.setReturnValue(cir.getReturnValue() * scale);
    }
}
