package org.silentpom.jlinq.impl.range;

import org.silentpom.jlinq.impl.IteratorUtil;
import org.silentpom.jlinq.range.Range;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: pom
 * Date: 11.07.2012
 * Time: 21:22:51
 */
public class CollectionRange<T> implements Range<T> {
    Collection<T> collection;

    public CollectionRange(Collection<T> collection) {
        this.collection = collection;
    }

    @Override
    public T first() {
        return IteratorUtil.first(collection);
    }

    @Override
    public T last() {
        return IteratorUtil.last(collection);
    }

    @Override
    public boolean isEmpty() {
        return collection.isEmpty();
    }

    @Override
    public List<T> toList() {
        return new ArrayList<T>(collection);
    }

    @Override
    public List<T> toConstList() {
        return toList();
    }

    @Override
    public Iterator<T> iterator() {
        return collection.iterator();
    }
}
