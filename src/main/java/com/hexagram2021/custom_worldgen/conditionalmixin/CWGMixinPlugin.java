package com.hexagram2021.custom_worldgen.conditionalmixin;

import me.fallenbreath.conditionalmixin.api.mixin.RestrictiveMixinConfigPlugin;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

public class CWGMixinPlugin extends RestrictiveMixinConfigPlugin {
	@Override @Nullable
	public String getRefMapperConfig() {
		return null;
	}
	
	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
	}
	
	@Override @Nullable
	public List<String> getMixins() {
		return null;
	}
}
