package com.hexagram2021.custom_worldgen.mixin;

import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.hexagram2021.custom_worldgen.common.config.CWGCommonConfig.STRUCTURE_DENSITY_MULTIPLIER;

@Mixin(RandomSpreadStructurePlacement.class)
public class RandomSpreadStructurePlacementMixin {
	@Shadow @Final @Mutable
	private int spacing;
	@Shadow @Final @Mutable
	private int separation;

	int originalSpacing;
	int originalSeparation;

	@Inject(method = "getPotentialStructureChunk", at = @At(value = "HEAD"))
	public void beforeModifyDensity(long seed, int x, int z, CallbackInfoReturnable<ChunkPos> cir) {
		this.originalSpacing = this.spacing;
		this.originalSeparation = this.separation;
		this.spacing = (int)(this.spacing / STRUCTURE_DENSITY_MULTIPLIER.value());
		this.separation = (int)(this.separation / STRUCTURE_DENSITY_MULTIPLIER.value());
		if(this.spacing <= 0) {
			this.spacing = 1;
		}
	}

	@Inject(method = "getPotentialStructureChunk", at = @At(value = "TAIL"))
	public void afterModifyDensity(long seed, int x, int z, CallbackInfoReturnable<ChunkPos> cir) {
		this.spacing = this.originalSpacing;
		this.separation = this.originalSeparation;
	}
}
