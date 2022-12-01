package store.lunangangster.nodeflow.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import store.lunangangster.nodeflow.context.Order;

/**
 * 该注解是用于{@link Order}的扩展，如果用户自定义了类而需要与本项目中
 * 有继承Order接口的类进行优先级比较，可以使用该注解进行优先级赋值
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Priority {
	// 以int表示的优先级，优先级越高，值越小。最高优先级为0
	int value() default Integer.MAX_VALUE;
}
