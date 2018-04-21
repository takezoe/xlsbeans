package com.github.takezoe.xlsbeans.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation for the property which is mapped to the horizontal table records.
 *
 * @author Naoki Takezoe
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HorizontalRecords {

  boolean optional() default false;

  String tableLabel() default "";

  String terminateLabel() default "";

  int headerRow() default -1;

  int headerColumn() default -1;

  Class<?> recordClass() default Object.class;

  RecordTerminal terminal() default RecordTerminal.Empty;

  int range() default 1;

  int bottom() default 1;

  int headerLimit() default 0;
}
