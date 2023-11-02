package com.hexagram2021.custom_worldgen.mixin;

import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static com.hexagram2021.custom_worldgen.common.config.CWGCommonConfig.STRUCTURE_DENSITY_MULTIPLIER;

@SuppressWarnings("deprecation")
@Mixin(StructurePlacement.ExclusionZone.class)
public class ExclusionZoneMixin {
	@Redirect(method = "isPlacementForbidden", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/levelgen/structure/placement/StructurePlacement$ExclusionZone;chunkCount:I", opcode = Opcodes.GETFIELD))
	private int redirectChunkCount(StructurePlacement.ExclusionZone instance) {
		int ret = (int)(instance.chunkCount() / STRUCTURE_DENSITY_MULTIPLIER.value());
		if(ret <= 0) {
			ret = 1;
		}
		return ret;
	}
}
