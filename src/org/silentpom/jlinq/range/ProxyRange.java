package org.silentpom.jlinq.range;

/**
 * Created by IntelliJ IDEA.
 * Author: pom
 * Date: 12.07.2012
 * Time: 20:44:11
 */
public interface ProxyRange<T, I> extends Range<T> {
    public Range<I> getUsedRange();

    public void setUsedRange(Range<I> range);
}
