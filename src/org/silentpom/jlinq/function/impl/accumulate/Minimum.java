package org.silentpom.jlinq.function.impl.accumulate;

/**
 * Created by IntelliJ IDEA.
 * Author: pom
 * Date: 28.07.12
 * Time: 0:09
 */

/**
 * Вычисляет минимум
 *
 * @param <T> элемент коллекции
 */
public class Minimum<T extends Comparable<? super T>> extends AccumulateNonNull<T> {
    /**
     * {@inheritDoc}
     */
    @Override
    protected T valueImpl(T elem, T last) {
        if (elem.compareTo(last) < 0) {
            return elem;
        }

        return last;
    }
}
