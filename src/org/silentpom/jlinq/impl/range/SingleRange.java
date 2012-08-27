package org.silentpom.jlinq.impl.range;

import org.silentpom.jlinq.range.Range;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: pom
 * Date: 11.07.2012
 * Time: 21:03:24
 */
public class SingleRange<T> implements Range<T> {
    private T elem;
    private List<T> fixedList;

    public SingleRange(T elem) {
        this.elem = elem;
        fixedList = Collections.singletonList(elem);
    }

    @Override
    public T first() {
        return elem;
    }

    @Override
    public T last() {
        return elem;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return fixedList.iterator();
    }

    @Override
    public List<T> toList() {
        ArrayList<T> copyList = new ArrayList<T>(1);
        copyList.add(elem);
        return copyList;
    }

    @Override
    public List<T> toConstList() {
        return fixedList;
    }
}
