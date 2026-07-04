package dev.sillibeans.slowmine.mixin;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class BreakSpeedMixin {
	@Inject(method = "getCurrentPlayerStrVsBlock", at = @At("RETURN"), cancellable = true)
	public void changeBlockBreadSpeed(CallbackInfoReturnable<Float> cir) {
		Player player = (Player) (Object) this;
		World world = player.world;

		if (world.isClientSide) {
			return;
		}

		float multiplier = 1.0f;

		switch (world.getDifficulty()) {
			case PEACEFUL:
			case EASY:
				multiplier = 1.3f;
				break;

			case HARD:
				multiplier = 0.6f;
				break;

			default: {}
		}

		cir.setReturnValue(cir.getReturnValue() * multiplier);
	}
}
