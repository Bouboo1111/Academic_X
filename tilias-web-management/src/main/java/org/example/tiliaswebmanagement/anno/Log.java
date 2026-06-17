package org.example.tiliaswebmanagement.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)// 注解作用目标
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)// 注解保留时间
public @interface Log {
}
