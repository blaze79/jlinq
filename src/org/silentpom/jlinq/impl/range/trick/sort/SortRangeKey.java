package org.silentpom.jlinq.impl.range.trick.sort;

import org.silentpom.jlinq.function.impl.selectors.TSelector;
import org.silentpom.jlinq.range.Range;

/**
 * Created by IntelliJ IDEA.
 * Author: pom
 * Date: 15.07.2012
 * Time: 16:45:24
 * Version: ${VERSION}
 */

/**
 * Класс сортирует последовательность по сраниваемым элементам
 *
 * @param <T> тип элемента
 */
public class SortRangeKey<T extends Comparable> extends SortRangeComparable<T, T> {

    /**
     * @param tRange исходная последовательность
     * @param asc    направление сортировки
     */
    public SortRangeKey(Range<T> tRange, boolean asc) {
        super(tRange, new TSelector<T>(), asc);
    }

    /**
     * @param tRange исходная последовательность
     */
    public SortRangeKey(Range<T> tRange) {
        this(tRange, true);
    }

}