package com.hexagram2021.custom_worldgen.conditionalmixin;

import com.google.common.collect.Maps;
import com.hexagram2021.custom_worldgen.common.utils.CWGLogger;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.service.MixinService;
import org.spongepowered.asm.util.Annotations;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ClassLoadConditionChecker {
	private final Map<String, Boolean> memory = Maps.newHashMap();
	
	public boolean checkRestriction(String mixinClassName) {
		AnnotationNode restriction = this.getRestrictionAnnotation(mixinClassName);
		if (restriction != null) {
			List<String> classNames = Annotations.getValue(restriction, "classNames", true);
			for (String className : classNames) {
				if (!this.isSatisfied(mixinClassName, className)) {
					return false;
				}
			}
		}
		return true;
	}
	
	public synchronized boolean memorizedCheckRestriction(String mixinClassName) {
		Boolean result = this.memory.get(mixinClassName);
		if (result == null) {
			result = this.checkRestriction(mixinClassName);
			this.memory.put(mixinClassName, result);
		}
		return result;
	}
	
	@Nullable
	private AnnotationNode getRestrictionAnnotation(String className) {
		try {
			ClassNode classNode = MixinService.getService().getBytecodeProvider().getClassNode(className);
			return Annotations.getVisible(classNode, Restriction.class);
		}
		catch (ClassNotFoundException | IOException e) {
			return null;
		}
	}
	
	public boolean isSatisfied(String mixinClassName, String conditionClassName) {
		try {
			Class.forName(conditionClassName);
		} catch (ClassNotFoundException e) {
			CWGLogger.LOGGER.debug("[Conditional Mixin] Target class %s not founded, skipping mixin %s.".formatted(conditionClassName, mixinClassName));
			return false;
		}
		CWGLogger.LOGGER.debug("[Conditional Mixin] Target class %s founded, loading compat mixin %s.".formatted(conditionClassName, mixinClassName));
		return true;
	}
}
