package com.hexagram2021.custom_worldgen.mixin.accessors;

import net.minecraft.util.CubicSpline;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CubicSpline.Multipoint.class)
public interface CubicSplineMultipointAccessor {
	@Accessor("locations")
	@Mutable
	void setLocations(float[] locations);
}
