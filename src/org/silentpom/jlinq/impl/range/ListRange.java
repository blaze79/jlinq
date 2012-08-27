package org.silentpom.jlinq.impl.range;

import org.silentpom.jlinq.range.Range;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: pom
 * Date: 11.07.2012
 * Time: 21:03:24
 */
public class ListRange<T> implements Range<T> {
    private List<T> list;

    public ListRange(List<T> list) {
        this.list = list;
    }

    @Override
    public T first() {
        return list.get(0);
    }

    @Override
    public T last() {
        return list.get(list.size() - 1);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    @Override
    public List<T> toList() {
        return new ArrayList<T>(list);
    }

    @Override
    public List<T> toConstList() {
        return list;
    }
}
