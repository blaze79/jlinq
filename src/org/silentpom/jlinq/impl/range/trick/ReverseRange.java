package org.silentpom.jlinq.impl.range.trick;

import org.silentpom.jlinq.range.Range;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: pom
 * Date: 12.07.2012
 * Time: 20:50:19
 */
public class ReverseRange<T> extends DirectRangeImpl<T, T> {
    public ReverseRange(Range<T> tRange) {
        super(tRange);
    }

    @Override
    public T first() {
        return range.last();
    }

    @Override
    public T last() {
        return range.first();
    }

    @Override
    public boolean isEmpty() {
        return range.isEmpty();
    }

    @Override
    public List<T> toList() {
        return toListImpl();
    }

    @Override
    public List<T> toConstList() {
        return toList();
    }

    protected List<T> toListImpl() {
        List<T> list = range.toList();
        Collections.reverse(list);
        return list;
    }

    @Override
    public Iterator<T> iterator() {
        return toListImpl().iterator();
    }
}
