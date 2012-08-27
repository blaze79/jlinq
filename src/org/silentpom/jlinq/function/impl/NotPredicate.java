package org.silentpom.jlinq.function.impl;

import org.silentpom.jlinq.function.Predicate;

/**
 * Created by IntelliJ IDEA.
 * Author: pom
 * Date: 23.07.2012
 * Time: 22:15:15
 */

/**
 * Отрицающий 1-местный предикат
 *
 * @param <T> аргумент
 */
public class NotPredicate<T> implements Predicate<T> {
    Predicate<T> pred;

    /**
     * @param pred - отрицаемый предикат
     */
    public NotPredicate(Predicate<T> pred) {
        this.pred = pred;
    }

    @Override
    public boolean value(T elem) {
        return !pred.value(elem);
    }
}
