package com.example.MaidsTest.Template.Aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation to mark methods for logging.
 * <p>
 * The {@link Loggable} annotation is used to indicate that the annotated method should have its execution
 * logged by the {@link LoggingAspect}. This includes logging method arguments, execution time, and return value.
 * It is primarily used in conjunction with the {@code LoggingAspect} class to enable method-level logging.
 * </p>
 */
@Target(ElementType.METHOD)  // Specifies that this annotation can be applied to methods only.
@Retention(RetentionPolicy.RUNTIME)  // Makes this annotation available at runtime for reflection.
public @interface Loggable {
}
