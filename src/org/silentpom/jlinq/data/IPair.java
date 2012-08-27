package org.silentpom.jlinq.data;

/**
 * Created by IntelliJ IDEA.
 * Author: pom
 * Date: 27.07.12
 * Time: 22:15
 */

/**
 * @param <T1></T1> - первый класс пары
 * @param <T2></T2> - второй класс пары
 */
public interface IPair<T1, T2> {

    public T1 getFirst();

    public void setFirst(T1 first);

    public T2 getSecond();

    public void setSecond(T2 second);
}
