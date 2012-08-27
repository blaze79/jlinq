package org.silentpom.jlinq.function.group;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: pom
 * Date: 27.07.12
 * Time: 22:06
 */

/**
 * Интерфейс функционала списка
 */
public interface GroupResultFunctor<X, Result, Key> {
    /**
     * вычисляет значение от  списка группируемых элементов
     *
     * @param k    - ключ группируемых элементов
     * @param list - группируемые элементы
     * @return - результат от группировки элементов
     */
    Result processList(Key k, List<X> list);
}
