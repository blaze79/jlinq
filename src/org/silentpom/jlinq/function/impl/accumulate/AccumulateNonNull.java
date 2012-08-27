package org.silentpom.jlinq.function.impl.accumulate;

import org.silentpom.jlinq.function.Accumulate;

/**
 * Created by IntelliJ IDEA.
 * Author: pom
 * Date: 28.07.12
 * Time: 0:09
 */

/**
 * Вычисляет аккумулятор, который возращает ненулевой аргумент, если один аргумент нулевой
 */
public abstract class AccumulateNonNull<T> implements Accumulate<T, T> {
    /**
     * {@inheritDoc}
     */
    @Override
    public T value(T elem, T last) {
        if (last == null) {
            return elem;
        }

        if (elem == null) {
            return last;
        }

        return valueImpl(elem, last);
    }

    /**
     * вычисляет аккумулятор на ненулевых значениях
     */
    protected abstract T valueImpl(T elem, T last);

}
