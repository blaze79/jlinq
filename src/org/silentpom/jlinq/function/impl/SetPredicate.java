package org.silentpom.jlinq.function.impl;

import org.silentpom.jlinq.function.Predicate;
import org.silentpom.jlinq.range.Range;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * Author: pom
 * Date: 23.07.2012
 * Time: 21:58:41
 */
public class SetPredicate<T> implements Predicate<T> {
    Set<T> set = null;
    Range<T> range;

    public SetPredicate(Range<T> range) {
        this.range = range;
    }

    @Override
    public boolean value(T elem) {
        if (set == null) {
            set = new HashSet<T>();
            for (T t : range) {
                set.add(t);
            }
        }

        return set.contains(elem);
    }
}
