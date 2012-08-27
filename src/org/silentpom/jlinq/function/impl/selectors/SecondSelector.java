package org.silentpom.jlinq.function.impl.selectors;

import org.silentpom.jlinq.data.IPair;
import org.silentpom.jlinq.function.KeySelector;

/**
 * Created by IntelliJ IDEA.
 * Author: pom
 * Date: 27.07.12
 * Time: 22:43
 */

/**
 * Выбирает второй элемент  в паре
 *
 * @param <T1> - первый тип в паре
 * @param <T2> - второй тип в паре
 */
public class SecondSelector<T1, T2> implements KeySelector<IPair<T1, T2>, T2> {
    /**
     * Выделяет второй элемент в паре
     *
     * @param x пара
     * @return второй элемент
     */
    @Override
    public T2 getKey(IPair<T1, T2> x) {
        return x.getSecond();
    }
}
