package org.silentpom.jlinq.impl.range;

import org.silentpom.jlinq.range.Range;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class ArrayRange<T> implements Range<T> {
    private T[] array;

    public ArrayRange(T[] array) {
        this.array = array;
    }

    @Override
    public T first() {
        return array[0];
    }

    @Override
    public T last() {
        return array[array.length - 1];
    }

    @Override
    public boolean isEmpty() {
        return array.length == 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterator<T> iterator() {
        return Arrays.asList(array).iterator();
    }

    @Override
    public List<T> toList() {
        return new ArrayList<T>(toConstList());
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> toConstList() {
        return Arrays.asList(array);
    }
}