package org.silentpom.jlinq.impl.range.trick;

import org.silentpom.jlinq.range.Range;

import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: pom
 * Date: 23.07.2012
 * Time: 20:50:11
 */
public class ConcatRange<T> extends ProxyRangeImpl<T, T> {
    private Range<T> endRange;

    public ConcatRange(Range<T> range1, Range<T> range2) {
        super(range1);
        endRange = range2;
    }

    @Override
    public T first() {
        if (!range.isEmpty()) {
            return range.first();
        } else {
            return endRange.first();
        }
    }

    @Override
    public T last() {
        if (!endRange.isEmpty()) {
            return endRange.last();
        } else {
            return range.last();
        }
    }

    @Override
    public boolean isEmpty() {
        return range.isEmpty() && endRange.isEmpty();
    }

    @Override
    public List<T> toList() {
        List<T> list = range.toList();
        list.addAll(endRange.toConstList());
        return list;
    }

    @Override
    public List<T> toConstList() {
        return toList();
    }

    @Override
    public Iterator<T> iterator() {
        return new InnerIterator(range.iterator(), endRange.iterator());
    }

    class InnerIterator implements Iterator<T> {
        Iterator<T> first, next;

        InnerIterator(Iterator<T> first, Iterator<T> next) {
            this.first = first;
            this.next = next;
        }

        @Override
        public boolean hasNext() {
            if (first != null) {
                if (first.hasNext()) {
                    return true;
                }
                first = null;
            }
            return next.hasNext();
        }

        @Override
        public T next() {
            if (first != null) {
                if (first.hasNext()) {
                    return first.next();
                }
                first = null;
            }
            return next.next();
        }

        @Override
        public void remove() {

        }
    }
}
