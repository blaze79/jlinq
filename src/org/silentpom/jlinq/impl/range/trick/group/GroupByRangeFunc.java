package org.silentpom.jlinq.impl.range.trick.group;

import org.silentpom.jlinq.data.IPair;
import org.silentpom.jlinq.data.impl.Pair;
import org.silentpom.jlinq.function.KeySelector;
import org.silentpom.jlinq.function.group.GroupResultFunctor;
import org.silentpom.jlinq.impl.range.trick.ProxyRangeImpl;
import org.silentpom.jlinq.range.Range;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * Author: pom
 * Date: 25.07.12
 * Time: 22:07
 */

/**
 * Класс представляет группированную последовательность, вычисляя функционал от списка группированных элементов
 *
 * @param <T>
 * @param <Key>
 * @param <DataResult>
 * @param <FinalResult>
 */
public class GroupByRangeFunc<T, Key, DataResult, FinalResult> extends ProxyRangeImpl<IPair<Key, FinalResult>, T> {

    ArrayList<IPair<Key, FinalResult>> list;
    GroupResultFunctor<DataResult, FinalResult, Key> processor;
    KeySelector<? super T, Key> keySelector;
    KeySelector<? super T, DataResult> resultSelector;

    /**
     * @param ts             - исходная последовательность
     * @param keySelector    - селектор ключей
     * @param resultSelector - селектор результатов
     * @param processor      - функционал от списка группированных элементов
     */
    public GroupByRangeFunc(Range<T> ts, KeySelector<? super T, Key> keySelector, KeySelector<? super T, DataResult> resultSelector, GroupResultFunctor<DataResult, FinalResult, Key> processor) {
        super(ts);
        this.processor = processor;
        this.keySelector = keySelector;
        this.resultSelector = resultSelector;
    }

    protected ArrayList<IPair<Key, FinalResult>> buildList() {
        ArrayList<IPair<Key, FinalResult>> list = new ArrayList<IPair<Key, FinalResult>>();
        Map<Key, ArrayList<DataResult>> mapper = new HashMap<Key, ArrayList<DataResult>>();

        for (T t : range) {
            Key key = keySelector.getKey(t);
            DataResult result = resultSelector.getKey(t);

            ArrayList<DataResult> tList = mapper.get(key);
            if (tList == null) {
                tList = new ArrayList<DataResult>();
                mapper.put(key, tList);
            }
            tList.add(result);
        }

        for (Map.Entry<Key, ArrayList<DataResult>> entry : mapper.entrySet()) {
            Key key = entry.getKey();
            List<DataResult> group = entry.getValue();
            FinalResult result = processor.processList(key, group);

            list.add(new Pair<Key, FinalResult>(key, result));
        }

        return list;
    }

    private synchronized void checkList() {
        if (list == null) {
            list = buildList();
        }
    }

    @Override
    public IPair<Key, FinalResult> first() {
        checkList();
        return list.get(0);
    }

    @Override
    public IPair<Key, FinalResult> last() {
        checkList();
        return list.get(list.size() - 1);
    }

    @Override
    public boolean isEmpty() {
        return range.isEmpty();
    }

    @Override
    public List<IPair<Key, FinalResult>> toList() {
        checkList();
        return new ArrayList<IPair<Key, FinalResult>>(list);
    }

    @Override
    public List<IPair<Key, FinalResult>> toConstList() {
        checkList();
        return list;
    }

    @Override
    public Iterator<IPair<Key, FinalResult>> iterator() {
        checkList();
        return list.iterator();
    }
}
