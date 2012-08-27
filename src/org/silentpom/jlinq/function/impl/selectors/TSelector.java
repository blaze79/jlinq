package org.silentpom.jlinq.function.impl.selectors;

import org.silentpom.jlinq.function.KeySelector;

/**
 * Created by IntelliJ IDEA.
 * User: pom
 * Date: 26.07.12
 * Time: 20:30
 * To change this template use File | Settings | File Templates.
 */

/**
 * Тривиальный селектор
 *
 * @param <T> объект селекции
 */
public class TSelector<T> implements KeySelector<T, T> {
    /**
     * Возвращает сам объект
     *
     * @param x объект
     * @return x
     */
    @Override
    public T getKey(T x) {
        return x;
    }
}
