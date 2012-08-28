package org.silentpom.jlinq;

import org.silentpom.jlinq.data.IPair;
import org.silentpom.jlinq.impl.range.ArrayRange;
import org.silentpom.jlinq.impl.range.CollectionRange;
import org.silentpom.jlinq.impl.range.ListRange;
import org.silentpom.jlinq.impl.range.MapRange;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Author: pom
 * Date: 11.07.2012
 * Time: 20:57:09
 */
public class JLinq {
    public static <T> LinqRange<T> from(List<T> list) {
        return new LinqRange<T>(new ListRange<T>(list));
    }

    public static <T> LinqRange<T> from(Collection<T> x) {
        return new LinqRange<T>(new CollectionRange<T>(x));
    }

    public static <T> LinqRange<T> from(T[] x) {
        return new LinqRange<T>(new ArrayRange<T>(x));
    }

    public static <Key, Value> LinqRange<IPair<Key, Value>> from(Map<Key, Value> map) {
        return new LinqRange<IPair<Key, Value>>(new MapRange<Key, Value>(map));
    }

    public static <T> LinqRange<T> fromT(T... x) {
        return from(x);
    }

}
