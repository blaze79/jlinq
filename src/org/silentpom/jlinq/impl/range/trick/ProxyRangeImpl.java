package org.silentpom.jlinq.impl.range.trick;

import org.silentpom.jlinq.range.ProxyRange;
import org.silentpom.jlinq.range.Range;

/**
 * Created by IntelliJ IDEA.
 * Author: pom
 * Date: 12.07.2012
 * Time: 20:52:39
 */
public abstract class ProxyRangeImpl<T, I> implements ProxyRange<T, I> {
    protected Range<I> range;

    public ProxyRangeImpl(Range<I> range) {
        this.range = range;
    }

    @Override
    public Range<I> getUsedRange() {
        return range;
    }

    @Override
    public void setUsedRange(Range<I> tRange) {
        range = tRange;
    }

}
