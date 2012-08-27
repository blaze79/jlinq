package org.silentpom.jlinq.function;

/**
 * Created by IntelliJ IDEA.
 * Author: pom
 * Date: 11.07.2012
 * Time: 21:46:34
 */
public interface Predicate<T> {
    public boolean value(T elem);
}
