package org.silentpom.jlinq.function.impl.accumulate;

import org.silentpom.jlinq.function.Accumulate;

/**
 * Created by IntelliJ IDEA.
 * Author: pom
 * Date: 26.07.12
 * Time: 22:41
 */

/**
 * Аккумулятор, склетвающий строковое представление объектов
 *
 * @param <T> - элемент коллекции
 */
public abstract class BaseStringAccumulate<T> implements Accumulate<T, StringBuilder> {
    private String suffix;
    private boolean first = true;

    protected BaseStringAccumulate() {
    }

    /**
     * @param suffix - cтрока, разделющая объекты
     */
    protected BaseStringAccumulate(String suffix) {
        this.suffix = suffix;
    }

    /**
     * {inheritDoc}
     */
    @Override
    public StringBuilder value(T elem, StringBuilder last) {
        if (first) {
            first = false;
        } else {
            if (suffix != null) {
                last.append(suffix);
            }
        }

        return last.append(getString(elem));
    }

    /**
     * Возвращает строковое представление объекта
     *
     * @param object - объект
     * @return строковое представление
     */
    protected abstract String getString(T object);
}
