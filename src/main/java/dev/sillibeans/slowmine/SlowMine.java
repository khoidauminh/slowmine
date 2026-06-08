package dev.sillibeans.slowmine;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SlowMine implements ModInitializer {
	public static final String MOD_ID = "slow-mine";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	@SuppressWarnings("all")
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		AttackBlockCallback.EVENT.register(new AttackBlockCallback() {
            @Override
            public @NonNull InteractionResult interact(Player player, Level level, InteractionHand hand, BlockPos pos, Direction direction) {
				if (level.isClientSide()) {
					return InteractionResult.PASS;
				}

				final var diff = level.getDifficulty();

				final double scale = switch (diff) {
					case Difficulty.PEACEFUL -> 1.3;
					case Difficulty.EASY -> 1.3;
					case Difficulty.HARD -> 0.6;
					default -> 1;
				};

				player.getAttribute(Attributes.BLOCK_BREAK_SPEED).setBaseValue(scale);

				return InteractionResult.PASS;
            }
        });

		LOGGER.info("Hello from Slowmine!");
	}
}