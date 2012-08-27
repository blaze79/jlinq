package org.silentpom.jlinq.function;

/**
 * Created by IntelliJ IDEA.
 * Author: pom
 * Date: 26.07.12
 * Time: 22:16
 */

/**
 * Интерфейс аккумулятора
 *
 * @param <T>      тип аргументов
 * @param <Result> тип результата
 */
public interface Accumulate<T, Result> {
    /**
     * Вычисляет аккумулятор
     *
     * @param elem - новый элемент
     * @param last - предыдущий накопленный аккумулятор
     * @return
     */
    public Result value(T elem, Result last);
}
