package org.silentpom.jlinq.range;

/**
 * Created by IntelliJ IDEA.
 * Author: pom
 * Date: 12.07.2012
 * Time: 20:46:31
 */
// была идея сделать чтобы последовательность пропускала через себя reverse, например редуцировать reverse.cast.revers
// до cast. но тогда надо еще делать копирование всего и вся. иначе это будет менять последовательность.
public interface DirectRange<T, I> extends ProxyRange<T, I> {

}
