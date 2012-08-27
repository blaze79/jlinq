package org.silentpom.jlinq.range;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: pom
 * Date: 11.07.2012
 * Time: 20:59:24
 */
public interface Range<T> extends Iterable<T> {
    public T first();

    public T last();

    public boolean isEmpty();

    public List<T> toList();

    public List<T> toConstList();
}
