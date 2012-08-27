package org.silentpom.jlinq.impl.range.trick.sort;

import org.silentpom.jlinq.function.KeySelector;
import org.silentpom.jlinq.range.Range;

import java.util.Comparator;

/**
 * Created by IntelliJ IDEA.
 * Author: pom
 * Date: 15.07.2012
 * Time: 16:45:24
 */

/**
 * Сортирует последовательность по сравниваевому ключу
 *
 * @param <T>   - исходный элемент
 * @param <Key> - ключ
 */
public class SortRangeComparable<T, Key extends Comparable> extends SortRange<T, Key> {
    /**
     * @param tRange          исходная последовательность
     * @param tKeyKeySelector селектор ключа
     * @param asc             направление сортировки
     */
    public SortRangeComparable(Range<T> tRange, KeySelector<? super T, Key> tKeyKeySelector, boolean asc) {
        super(tRange, tKeyKeySelector, new StandardComparator<Key>(), asc);
    }

    /**
     * @param tRange          исходная последовательность
     * @param tKeyKeySelector селектор ключа
     */
    public SortRangeComparable(Range<T> tRange, KeySelector<T, Key> tKeyKeySelector) {
        this(tRange, tKeyKeySelector, true);
    }

    static class StandardComparator<T extends Comparable> implements Comparator<T> {
        @Override
        public int compare(T t, T t1) {
            return t.compareTo(t1);
        }
    }
}
