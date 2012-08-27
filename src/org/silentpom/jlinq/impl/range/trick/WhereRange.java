package org.silentpom.jlinq.impl.range.trick;

import org.silentpom.jlinq.function.Predicate;
import org.silentpom.jlinq.impl.IteratorUtil;
import org.silentpom.jlinq.range.Range;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: pom
 * Date: 11.07.2012
 * Time: 21:45:41
 */
public class WhereRange<T> extends DirectRangeImpl<T, T> {

    Predicate<T> p;

    public WhereRange(Range<T> tRange, Predicate<T> p) {
        super(tRange);
        this.p = p;
    }

    @Override
    public T first() {
        return IteratorUtil.first(getInner());
    }

    @Override
    public T last() {
        return IteratorUtil.last(getInner());
    }

    @Override
    public boolean isEmpty() {
        return getInner().hasNext();
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
        return getInner();
    }

    private InnerIterator getInner() {
        return new InnerIterator(range.iterator());
    }

    class InnerIterator implements Iterator<T> {
        boolean headFound = false;
        Iterator<T> innerIter;
        T head = null;

        InnerIterator(Iterator<T> innerIter) {
            this.innerIter = innerIter;
        }

        @Override
        public boolean hasNext() {
            findHead();
            return headFound;
        }

        @Override
        public T next() {
            findHead();
            headFound = false;
            return head;
        }

        @Override
        public void remove() {

        }

        private void findHead() {
            if (!headFound) {
                head = null;
                while (innerIter.hasNext()) {
                    T elem = innerIter.next();
                    if (p.value(elem)) {
                        head = elem;
                        headFound = true;
                        return;
                    }
                }
            }
        }
    }
}
