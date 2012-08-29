package org.silentpom.jlinq;

import org.silentpom.jlinq.data.GroupedData;
import org.silentpom.jlinq.data.IPair;
import org.silentpom.jlinq.function.Accumulate;
import org.silentpom.jlinq.function.Caster;
import org.silentpom.jlinq.function.KeySelector;
import org.silentpom.jlinq.function.Predicate;
import org.silentpom.jlinq.function.group.GroupResultFunctor;
import org.silentpom.jlinq.function.group.GroupResultProcessor;
import org.silentpom.jlinq.function.impl.NotPredicate;
import org.silentpom.jlinq.function.impl.SetPredicate;
import org.silentpom.jlinq.function.impl.selectors.TSelector;
import org.silentpom.jlinq.impl.IteratorUtil;
import org.silentpom.jlinq.impl.range.trick.CastRange;
import org.silentpom.jlinq.impl.range.trick.ConcatRange;
import org.silentpom.jlinq.impl.range.trick.ReverseRange;
import org.silentpom.jlinq.impl.range.trick.WhereRange;
import org.silentpom.jlinq.impl.range.trick.group.GroupByRange;
import org.silentpom.jlinq.impl.range.trick.group.GroupByRangeFunc;
import org.silentpom.jlinq.impl.range.trick.join.JoinRange;
import org.silentpom.jlinq.impl.range.trick.sort.SortRange;
import org.silentpom.jlinq.impl.range.trick.sort.SortRangeComparable;
import org.silentpom.jlinq.impl.range.trick.sort.SortRangeKey;
import org.silentpom.jlinq.range.Range;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: pom
 * Date: 11.07.2012
 * Time: 20:55:54
 */
public class LinqRange<T> implements Range<T> {
    private Range<T> range;

    public LinqRange(Range<T> range) {
        this.range = range;
    }

    public LinqRange<T> where(Predicate<T> p) {
        return new LinqRange<T>(new WhereRange<T>(range, p));
    }

    public LinqRange<T> reverse() {

        if (range instanceof ReverseRange) {
            return new LinqRange<T>(((ReverseRange<T>) range).getUsedRange());
        } else {
            return new LinqRange<T>(new ReverseRange<T>(range));
        }
    }

    public <New> LinqRange<New> cast(Caster<New, T> caster) {
        return new LinqRange<New>(new CastRange<New, T>(range, caster));
    }

    @SuppressWarnings("unchecked")
    public LinqRange<T> sort(boolean asc) { // нет специализации шаблонов, придется обходить проблему
        //noinspection unchecked
        return (LinqRange<T>) (new LinqRange<Comparable>(new SortRangeKey<Comparable>((Range<Comparable>) (range), asc)));
    }

    public <Key extends Comparable> LinqRange<T> sort(KeySelector<? super T, Key> selector, boolean asc) {
        return new LinqRange<T>(new SortRangeComparable<T, Key>(range, selector, asc));
    }

    public <Key> LinqRange<T> sort(KeySelector<? super T, Key> selector, Comparator<? super Key> comparator, boolean asc) {
        return new LinqRange<T>(new SortRange<T, Key>(range, selector, comparator, asc));
    }

    public LinqRange<T> add(LinqRange<T> tail) {
        return new LinqRange<T>(new ConcatRange<T>(this.range, tail.range));
    }

    public LinqRange<T> addHead(LinqRange<T> head) {
        return new LinqRange<T>(new ConcatRange<T>(head.range, this.range));
    }

    public LinqRange<T> remove(LinqRange<T> set) {
        return new LinqRange<T>(new WhereRange<T>(range, new NotPredicate<T>(new SetPredicate<T>(set))));
    }

    private <Key> Predicate<T> getFilterPredicate(final Predicate<Key> keys, final KeySelector<? super T, Key> selector) {
        return new Predicate<T>() {
            @Override
            public boolean value(T elem) {
                Key key = selector.getKey(elem);
                return keys.value(key);
            }
        };
    }

    private <Key> Predicate<Key> getKeyPredicate(LinqRange<T> set, final KeySelector<? super T, Key> selector) {
        return new SetPredicate<Key>(set.cast(new Caster<Key, T>() {
            @Override
            public Key cast(T t) {
                return selector.getKey(t);
            }
        }));
    }

    public <Key> LinqRange<T> remove(LinqRange<T> set, final KeySelector<? super T, Key> selector) {
        Predicate<T> filter = getFilterPredicate(getKeyPredicate(set, selector), selector);
        return new LinqRange<T>(new WhereRange<T>(range, new NotPredicate<T>(filter)));
    }

    public <Key> LinqRange<T> removeKeys(LinqRange<Key> set, final KeySelector<? super T, Key> selector) {
        Predicate<T> filter = getFilterPredicate(new SetPredicate<Key>(set), selector);
        return new LinqRange<T>(new WhereRange<T>(range, new NotPredicate<T>(filter)));
    }

    public LinqRange<T> retain(LinqRange<T> set) {
        return new LinqRange<T>(new WhereRange<T>(range, new SetPredicate<T>(set)));
    }

    public <Key> LinqRange<T> retain(LinqRange<T> set, final KeySelector<? super T, Key> selector) {
        Predicate<T> filter = getFilterPredicate(getKeyPredicate(set, selector), selector);
        return new LinqRange<T>(new WhereRange<T>(range, filter));
    }

    public <Key> LinqRange<T> retainKeys(LinqRange<Key> set, final KeySelector<? super T, Key> selector) {
        Predicate<T> filter = getFilterPredicate(new SetPredicate<Key>(set), selector);
        return new LinqRange<T>(new WhereRange<T>(range, filter));
    }

    public <Key, TempResult, Result> LinqRange<IPair<Key, Result>> groupBy(KeySelector<? super T, Key> keySelector,
                                                                           KeySelector<? super T, TempResult> resultSelector,
                                                                           GroupResultFunctor<TempResult, Result, Key> resultProcessor) {
        return new LinqRange<IPair<Key, Result>>(new GroupByRangeFunc<T, Key, TempResult, Result>(range, keySelector, resultSelector, resultProcessor));
    }

    public <Key, Result> LinqRange<IPair<Key, Result>> groupBy(KeySelector<? super T, Key> keySelector,
                                                               GroupResultFunctor<T, Result, Key> resultProcessor) {
        return new LinqRange<IPair<Key, Result>>(new GroupByRangeFunc<T, Key, T, Result>(range, keySelector, new TSelector<T>(), resultProcessor));
    }


    public <Key, Result> LinqRange<GroupedData<Key, Result>> groupBy(KeySelector<? super T, Key> keySelector,
                                                                     KeySelector<? super T, Result> resultSelector,
                                                                     GroupResultProcessor<Result, Key> resultProcessor) {
        return new LinqRange<GroupedData<Key, Result>>(new GroupByRange<T, Key, Result>(range, keySelector, resultSelector, resultProcessor));
    }

    public <Key, Result> LinqRange<GroupedData<Key, Result>> groupBy(KeySelector<? super T, Key> keySelector,
                                                                     KeySelector<? super T, Result> resultSelector) {
        return new LinqRange<GroupedData<Key, Result>>(new GroupByRange<T, Key, Result>(range, keySelector, resultSelector));
    }

    public <Key> LinqRange<GroupedData<Key, T>> groupBy(KeySelector<? super T, Key> keySelector, GroupResultProcessor<T, Key> resultProcessor) {
        return new LinqRange<GroupedData<Key, T>>(new GroupByRange<T, Key, T>(range, keySelector, new TSelector<T>(), resultProcessor));
    }

    public <Key> LinqRange<GroupedData<Key, T>> groupBy(KeySelector<? super T, Key> keySelector) {
        return new LinqRange<GroupedData<Key, T>>(new GroupByRange<T, Key, T>(range, keySelector, new TSelector<T>()));
    }


    public LinqRange<IPair<T, List<T>>> join(Range<T> right) {
        TSelector<T> selector = new TSelector<T>();
        return new LinqRange<IPair<T, List<T>>>(new JoinRange<T, T, T, T, List<T>>(range, right, selector, selector, selector, new GroupResultFunctor<T, List<T>, T>() {
            @Override
            public List<T> processList(T k, List<T> ts) {
                return ts;
            }
        }));
    }

    public <U, Middle> LinqRange<IPair<T, List<Middle>>> join(Range<U> right, KeySelector<? super U, T> rightSelector, KeySelector<? super U, Middle> middleKeySelector) {
        TSelector<T> selector = new TSelector<T>();
        return new LinqRange<IPair<T, List<Middle>>>(new JoinRange<T, T, U, Middle, List<Middle>>(range, right, selector, rightSelector, middleKeySelector,
                new GroupResultFunctor<Middle, List<Middle>, T>() {
                    @Override
                    public List<Middle> processList(T k, List<Middle> middles) {
                        return middles;
                    }
                }));
    }

    public <U, Key, Middle> LinqRange<IPair<T, List<Middle>>> join(Range<U> right, KeySelector<? super T, Key> leftSelector, KeySelector<? super U, Key> rightSelector, KeySelector<? super U, Middle> middleKeySelector) {

        return new LinqRange<IPair<T, List<Middle>>>(new JoinRange<T, Key, U, Middle, List<Middle>>(range, right, leftSelector, rightSelector, middleKeySelector, new GroupResultFunctor<Middle, List<Middle>, Key>() {
            @Override
            public List<Middle> processList(Key k, List<Middle> us) {
                return us;
            }
        }));
    }

    public <U, Key, Result> LinqRange<IPair<T, Result>> join(Range<U> right, KeySelector<? super T, Key> leftSelector,
                                                             KeySelector<? super U, Key> rightSelector,
                                                             GroupResultFunctor<U, Result, Key> functor) {

        return new LinqRange<IPair<T, Result>>(new JoinRange<T, Key, U, U, Result>(range, right, leftSelector, rightSelector, new TSelector<U>(), functor));
    }

    public <U, Key, Middle, Result> LinqRange<IPair<T, Result>> join(Range<U> right, KeySelector<? super T, Key> leftSelector,
                                                                     KeySelector<? super U, Key> rightSelector,
                                                                     KeySelector<? super U, Middle> middleKeySelector,
                                                                     GroupResultFunctor<Middle, Result, Key> functor) {

        return new LinqRange<IPair<T, Result>>(new JoinRange<T, Key, U, Middle, Result>(range, right, leftSelector, rightSelector, middleKeySelector, functor));
    }

    public <Result> Result accumulate(Accumulate<T, Result> accumulate, Result start) {
        for (T t : range) {
            start = accumulate.value(t, start);
        }
        return start;
    }

    public <Result> List<Result> accumulates(Accumulate<T, Result>... accumulates) {
        return accumulates(null, accumulates);
    }

    public <Result> List<Result> accumulates(Result start, Accumulate<T, Result>... accumulates) {
        ArrayList<Result> results = new ArrayList<Result>();
        for (Accumulate<T, Result> acc : accumulates) {
            results.add(accumulate(acc, start));
        }

        return results;
    }

    public boolean contains(T elem) {
        for (T test : range) {
            if (IteratorUtil.equals(elem, test)) {
                return true;
            }
        }

        return false;
    }


    @Override
    public Iterator<T> iterator() {
        return range.iterator();
    }

    @Override
    public T first() {
        return range.first();
    }

    @Override
    public T last() {
        return range.last();
    }

    @Override
    public boolean isEmpty() {
        return range.isEmpty();
    }

    @Override
    public List<T> toList() {
        return range.toList();
    }

    @Override
    public List<T> toConstList() {
        return range.toConstList();
    }
}
