package org.silentpom.jlinq.impl.range.trick.group;

import org.silentpom.jlinq.data.GroupedData;
import org.silentpom.jlinq.data.impl.GroupedDataImpl;
import org.silentpom.jlinq.function.KeySelector;
import org.silentpom.jlinq.function.group.GroupResultProcessor;
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
 * Класс группирующий элементы
 *
 * @param <T>      тип элементов
 * @param <Key>    тип ключа для группировки
 * @param <Result> тип группированных элементов
 */
public class GroupByRange<T, Key, Result> extends ProxyRangeImpl<GroupedData<Key, Result>, T> {

    ArrayList<GroupedData<Key, Result>> list;
    GroupResultProcessor<Result, Key> processor;
    KeySelector<? super T, Key> keySelector;
    KeySelector<? super T, Result> resultSelector;

    /**
     * @param ts             - исходная последовательность элементов
     * @param keySelector    - селектор ключа
     * @param resultSelector - селектор результа
     */
    public GroupByRange(Range<T> ts, KeySelector<? super T, Key> keySelector, KeySelector<? super T, Result> resultSelector) {
        this(ts, keySelector, resultSelector, null);
    }

    /**
     * @param ts              - исходная последовательность элементов
     * @param keySelector     - селектор ключа
     * @param resultSelector- селектор результа
     * @param processor       - обработчик списка группированных элементов
     */
    public GroupByRange(Range<T> ts, KeySelector<? super T, Key> keySelector, KeySelector<? super T, Result> resultSelector, GroupResultProcessor<Result, Key> processor) {
        super(ts);
        this.processor = processor;
        this.keySelector = keySelector;
        this.resultSelector = resultSelector;
    }

    protected ArrayList<GroupedData<Key, Result>> buildList() {
        ArrayList<GroupedData<Key, Result>> list = new ArrayList<GroupedData<Key, Result>>();
        Map<Key, ArrayList<Result>> mapper = new HashMap<Key, ArrayList<Result>>();

        for (T t : range) {
            Key key = keySelector.getKey(t);
            Result result = resultSelector.getKey(t);

            ArrayList<Result> tList = mapper.get(key);
            if (tList == null) {
                tList = new ArrayList<Result>();
                mapper.put(key, tList);
            }
            tList.add(result);
        }

        for (Map.Entry<Key, ArrayList<Result>> entry : mapper.entrySet()) {
            Key key = entry.getKey();
            List<Result> group = entry.getValue();

            if (processor != null) {
                group = processor.processList(key, group);
            }

            list.add(new GroupedDataImpl<Key, Result>(key, group));
        }

        return list;
    }

    private synchronized void checkList() {
        if (list == null) {
            list = buildList();
        }
    }

    @Override
    public GroupedData<Key, Result> first() {
        checkList();
        return list.get(0);
    }

    @Override
    public GroupedData<Key, Result> last() {
        checkList();
        return list.get(list.size() - 1);
    }

    @Override
    public boolean isEmpty() {
        return range.isEmpty();
    }

    @Override
    public List<GroupedData<Key, Result>> toList() {
        checkList();
        return new ArrayList<GroupedData<Key, Result>>(list);
    }

    @Override
    public List<GroupedData<Key, Result>> toConstList() {
        checkList();
        return list;
    }

    @Override
    public Iterator<GroupedData<Key, Result>> iterator() {
        checkList();
        return list.iterator();
    }
}
