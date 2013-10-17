package com.roy.tiny.base.web.annotation;

/**
 * It cann't be visited by an anonymous user if a method of controller is marked by this annotation.
 * @author Roy Wang
 *
 */
@java.lang.annotation.Target(value={java.lang.annotation.ElementType.METHOD,java.lang.annotation.ElementType.TYPE})
@java.lang.annotation.Retention(value=java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Documented
public abstract @interface Auth {

}
