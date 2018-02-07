/**
 * 创建日期:  2017年08月12日 15:26
 * 创建作者:  杨 强  <281455776@qq.com>
 */
package info.xiaomo.core.config.annotation;


import info.xiaomo.core.config.IConverter;

import java.lang.annotation.*;

/**
 * 列注解
 *
 * @author YangQiang
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Column {
    /**
     * 列名
     *
     * @return
     */
    String name() default "";

    /**
     * 是否允许为null
     *
     * @return
     */
    boolean notNull() default false;

    /**
     * 转换器
     *
     * @return
     */
    Class<? extends IConverter>[] value() default {};
}
