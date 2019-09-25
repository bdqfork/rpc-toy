package cn.bdqfork.common.extension;

import java.lang.annotation.*;

/**
 * @author bdq
 * @since 2019/9/21
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Activate {
    String[] value() default {};

    String[] group() default {};

    int order() default 0;
}