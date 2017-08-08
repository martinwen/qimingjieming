package com.tjyw.atom.pub.inject;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ java.lang.annotation.ElementType.FIELD })
@Inherited
public @interface FromArray {

    public abstract int[] value();

    public abstract boolean canBeNull() default false;

}
