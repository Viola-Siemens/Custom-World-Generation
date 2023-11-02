package com.hexagram2021.custom_worldgen.conditionalmixin;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

public class CWGMixinPlugin implements IMixinConfigPlugin {
	private final ClassLoadConditionChecker checker = new ClassLoadConditionChecker();
	private final AnnotationHelper annotationHelper = new AnnotationHelper(Restriction.class);
	
	
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
	
	@Override
	public void onLoad(String mixinPackage) {
	}
	
	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		return this.checker.memorizedCheckRestriction(mixinClassName);
	}
	
	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
		this.annotationHelper.onPreApply(targetClass);
	}
	
	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
		this.annotationHelper.onPostApply(targetClass);
	}
	
	protected void onRestrictionCheckFailed(String mixinClassName, String reason) {
	}
}
