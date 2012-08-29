package org.silentpom.jlinq.impl.range.trick.join;

import org.silentpom.jlinq.data.IPair;
import org.silentpom.jlinq.data.impl.Pair;
import org.silentpom.jlinq.function.KeySelector;
import org.silentpom.jlinq.function.group.GroupResultFunctor;
import org.silentpom.jlinq.impl.range.trick.ProxyRangeImpl;
import org.silentpom.jlinq.range.Range;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: pom
 * Date: 25.07.12
 * Time: 22:07
 */

/**
 * Join range class
 *
 * @param <T>      left source type
 * @param <Key>    key type for join
 * @param <U>      right source type
 * @param <Result> result type
 */
public class JoinRange<T, Key, U, Middle, Result> extends ProxyRangeImpl<IPair<T, Result>, T> {

    protected ArrayList<IPair<T, Result>> list;
    protected Range<U> right;
    protected GroupResultFunctor<Middle, Result, Key> resultFunctor;

    protected KeySelector<? super T, Key> leftSelector;
    protected KeySelector<? super U, Middle> middleKeySelector;
    protected KeySelector<? super U, Key> rightSelector;

    public JoinRange(Range<T> left, Range<U> right, KeySelector<? super T, Key> leftSelector,
                     KeySelector<? super U, Key> rightSelector, KeySelector<? super U, Middle> middleKeySelector,
                     GroupResultFunctor<Middle, Result, Key> resultFunctor) {
        super(left);
        this.right = right;
        this.leftSelector = leftSelector;
        this.middleKeySelector = middleKeySelector;
        this.rightSelector = rightSelector;
        this.resultFunctor = resultFunctor;
    }

    protected ArrayList<IPair<T, Result>> buildList() {
        ArrayList<IPair<T, Result>> list = new ArrayList<IPair<T, Result>>();
        HashMap<Key, List<Middle>> mapper = new HashMap<Key, List<Middle>>();

        for (U u : right) {
            Key key = rightSelector.getKey(u);
            List<Middle> uList = mapper.get(key);
            if (uList == null) {
                uList = new ArrayList<Middle>();
                mapper.put(key, uList);
            }
            uList.add(middleKeySelector.getKey(u));
        }

        for (T t : range) {
            Key key = leftSelector.getKey(t);
            List<Middle> uList = mapper.get(key);
            if (uList == null) {
                uList = new ArrayList<Middle>();
            }

            Result result = resultFunctor.processList(key, uList);
            list.add(new Pair<T, Result>(t, result));
        }

        return list;
    }

    private synchronized void checkList() {
        if (list == null) {
            list = buildList();
        }
    }

    @Override
    public IPair<T, Result> first() {
        checkList();
        return list.get(0);
    }

    @Override
    public IPair<T, Result> last() {
        checkList();
        return list.get(list.size() - 1);
    }

    @Override
    public boolean isEmpty() {
        return range.isEmpty();
    }

    @Override
    public List<IPair<T, Result>> toList() {
        checkList();
        return new ArrayList<IPair<T, Result>>(list);
    }

    @Override
    public List<IPair<T, Result>> toConstList() {
        checkList();
        return list;
    }

    @Override
    public Iterator<IPair<T, Result>> iterator() {
        checkList();
        return list.iterator();
    }
}
