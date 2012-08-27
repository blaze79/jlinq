package org.silentpom.jlinq.impl.range.trick;

import org.silentpom.jlinq.function.Caster;
import org.silentpom.jlinq.impl.IteratorUtil;
import org.silentpom.jlinq.range.Range;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: pom
 * Date: 12.07.2012
 * Time: 22:33:56
 */
public class CastRange<T, Old> extends DirectRangeImpl<T, Old> {
    Caster<T, Old> caster;

    public CastRange(Range<Old> oldRange, Caster<T, Old> caster) {
        super(oldRange);
        this.caster = caster;
    }

    @Override
    public T first() {
        return caster.cast(range.first());
    }

    @Override
    public T last() {
        return caster.cast(range.first());
    }

    @Override
    public boolean isEmpty() {
        return range.isEmpty();
    }

    @Override
    public List<T> toList() {
        return IteratorUtil.fillList(new ArrayList<T>(), iterator());
    }

    @Override
    public List<T> toConstList() {
        return toList();
    }

    @Override
    public Iterator<T> iterator() {
        return new InnerIterator(range.iterator());
    }


    class InnerIterator implements Iterator<T> {
        Iterator<Old> iterator;

        InnerIterator(Iterator<Old> iterator) {
            this.iterator = iterator;
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public T next() {
            return caster.cast(iterator.next());
        }

        @Override
        public void remove() {

        }
    }
}
