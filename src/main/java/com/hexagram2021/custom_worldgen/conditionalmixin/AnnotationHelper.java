package com.hexagram2021.custom_worldgen.conditionalmixin;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.util.Annotations;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.List;

public class AnnotationHelper {
	private final Class<? extends Annotation> annotationClass;
	
	public AnnotationHelper(Class<? extends Annotation> annotationClass) {
		this.annotationClass = annotationClass;
	}
	
	@Nullable
	private AnnotationNode previousRestrictionAnnotation;
	
	public void onPreApply(ClassNode targetClass) {
		this.previousRestrictionAnnotation = Annotations.getVisible(targetClass, this.annotationClass);
	}
	
	public void onPostApply(ClassNode targetClass) {
		String descriptor = Type.getDescriptor(this.annotationClass);
		List<AnnotationNode> annotationNodes = targetClass.visibleAnnotations;
		if (annotationNodes == null) {
			return;
		}
		int index = -1;
		for (int i = 0; i < annotationNodes.size(); i++) {
			if (descriptor.equals(annotationNodes.get(i).desc)) {
				index = i;
				break;
			}
		}
		if (this.previousRestrictionAnnotation == null && index != -1) {	// @Restriction is merged, drop it
			annotationNodes.remove(index);
		}
		else if (this.previousRestrictionAnnotation != null) {				// preserve the original @Restriction
			annotationNodes.set(index, this.previousRestrictionAnnotation);
		}
	}
}
