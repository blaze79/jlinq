package org.silentpom.jlinq.impl.range.trick.sort;

import org.silentpom.jlinq.function.KeySelector;
import org.silentpom.jlinq.impl.range.trick.ProxyRangeImpl;
import org.silentpom.jlinq.range.Range;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * Author: pom
 * Date: 11.07.2012
 * Time: 21:45:41
 */

/**
 * Сортированная последовательность элементов с компаратором
 *
 * @param <T>   тип исходных элементов
 * @param <Key> тип ключа для сортировки
 */
public class SortRange<T, Key> extends ProxyRangeImpl<T, T> {

    KeySelector<? super T, Key> selector;
    Comparator<? super Key> comparator;
    List<T> innerList = null;
    boolean ascended = true;

    /**
     * @param tRange     - исходная последовательность
     * @param selector   - селектор ключа
     * @param comparator - компаратор
     */
    public SortRange(Range<T> tRange, KeySelector<? super T, Key> selector, Comparator<? super Key> comparator) {
        this(tRange, selector, comparator, true);
    }

    /**
     * @param tRange     - исходная последовательность
     * @param selector   - селектор ключа
     * @param comparator - компаратор
     * @param asc        - возрастание/убывание сортировки
     */
    public SortRange(Range<T> tRange, KeySelector<? super T, Key> selector, Comparator<? super Key> comparator, boolean asc) {
        super(tRange);
        this.selector = selector;
        this.comparator = comparator;
        ascended = asc;
    }

    @Override
    public T first() {
        return getArrayList().get(0);
    }

    @Override
    public T last() {
        return getArrayList().get(getArrayList().size() - 1);
    }

    @Override
    public boolean isEmpty() {
        return range.isEmpty();
    }

    @Override
    public List<T> toList() {
        return new ArrayList<T>(getArrayList());
    }

    @Override
    public List<T> toConstList() {
        return getArrayList();
    }

    @Override
    public Iterator<T> iterator() {
        return getArrayList().iterator();
    }

    private List<T> getArrayList() {
        if (innerList == null) {
            innerList = range.toList();
            Collections.sort(innerList, new InnerComparator());
        }
        return innerList;
    }

    class InnerComparator implements Comparator<T> {
        @Override
        public int compare(T t, T t1) {
            return comparator.compare(selector.getKey(t), selector.getKey(t1)) * (ascended ? 1 : -1);
        }
    }


}