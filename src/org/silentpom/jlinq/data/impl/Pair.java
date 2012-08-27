package org.silentpom.jlinq.data.impl;

import org.silentpom.jlinq.data.IPair;

/**
 * Created by IntelliJ IDEA.
 * Author:
 * Date: 27.07.12
 * Time: 22:15
 */

/**
 * @param <T1> - первый класс пары
 * @param <T2> - второй класс пары
 */
public class Pair<T1, T2> implements IPair<T1, T2> {
    private T1 first;
    private T2 second;

    public Pair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public T1 getFirst() {
        return first;
    }

    @Override
    public void setFirst(T1 first) {
        this.first = first;
    }

    @Override
    public T2 getSecond() {
        return second;
    }

    @Override
    public void setSecond(T2 second) {
        this.second = second;
    }
}
