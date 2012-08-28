package org.silentpom.jlinq.function.impl.selectors;

import org.silentpom.jlinq.data.IPair;
import org.silentpom.jlinq.function.Caster;
import org.silentpom.jlinq.function.KeySelector;

/**
 * Created by IntelliJ IDEA.
 * Author: pom
 * Date: 27.07.12
 * Time: 22:43
 */

/**
 * Выбирает первый элемент пары
 *
 * @param <T1> - первый тип в паре
 * @param <T2> - второй тип в паре
 */
public class FirstSelector<T1, T2> implements KeySelector<IPair<T1, T2>, T1>, Caster<T1, IPair<T1, T2>> {
    /**
     * выделяет первый элемент в паре
     *
     * @param x пара
     * @return первый элемент
     */
    @Override
    public T1 getKey(IPair<T1, T2> x) {
        return x.getFirst();
    }

    @Override
    public T1 cast(IPair<T1, T2> t) {
        return getKey(t);
    }
}
