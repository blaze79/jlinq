package org.silentpom.jlinq.impl.range.trick;

import org.silentpom.jlinq.range.DirectRange;
import org.silentpom.jlinq.range.Range;

/**
 * Created by IntelliJ IDEA.
 * Author: pom
 * Date: 12.07.2012
 * Time: 22:01:43
 */
public abstract class DirectRangeImpl<T, I> extends ProxyRangeImpl<T, I> implements DirectRange<T, I> {
    protected DirectRangeImpl(Range<I> iRange) {
        super(iRange);
    }
}
