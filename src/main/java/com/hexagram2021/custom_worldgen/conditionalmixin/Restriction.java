package com.hexagram2021.custom_worldgen.conditionalmixin;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Restriction {
	String[] classNames() default {};
}
