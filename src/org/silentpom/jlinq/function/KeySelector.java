package org.silentpom.jlinq.function;

/**
 * Created by IntelliJ IDEA.
 * Author: pom
 * Date: 15.07.2012
 * Time: 16:02:17
 */

/**
 * Интерфейс селектора для выбора ключа
 *
 * @param <T>   тип объекта
 * @param <Key> тип ключа
 */
public interface KeySelector<T, Key> {
    /**
     * Возврашщает ключ
     *
     * @param x - объект
     * @return ключ
     */
    public Key getKey(T x);
}
