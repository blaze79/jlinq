package org.silentpom.jlinq.function.group;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: pom
 * Date: 25.07.12
 * Time: 22:23
 */

/**
 * Интерфейс трансформера списка группируемых элементов
 *
 * @param <X>
 * @param <Key>
 */
public interface GroupResultProcessor<X, Key> {
    /**
     * Возвращает измененный список группированных элементов
     *
     * @param k    - ключ элементов
     * @param list - список элементов
     * @return - измененный список элементов
     */
    List<X> processList(Key k, List<X> list);
}
