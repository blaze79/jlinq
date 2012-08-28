package org.silentpom.jlinq.function.impl.selectors;

import org.silentpom.jlinq.function.Caster;
import org.silentpom.jlinq.function.KeySelector;

/**
 * ${NAME}
 *
 * @author Vladislav Kogut
 * @version $Id: Exp $
 */

/**
 * Тривиальный селектор
 *
 * @param <T> объект селекции
 */
public class TSelector<T> implements KeySelector<T, T>, Caster<T, T> {
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

    @Override
    public T cast(T t) {
        return t;
    }
}
