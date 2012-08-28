package org.silentpom.jlinq.impl.range;

import org.silentpom.jlinq.data.IPair;
import org.silentpom.jlinq.data.impl.Pair;
import org.silentpom.jlinq.range.Range;

import java.util.*;

/**
 * MapRange
 *
 * @author Vladislav Kogut
 * @version $Id: Exp $
 */
public class MapRange<Key, Value> implements Range<IPair<Key, Value>> {
    Map<Key, Value> valueMap;
    Set<Map.Entry<Key, Value>> entrySet;
    List<IPair<Key, Value>> saveList;


    public MapRange(Map<Key, Value> valueMap) {
        this.valueMap = valueMap;
        entrySet = valueMap.entrySet();
    }

    protected IPair<Key, Value> getPair(Map.Entry<Key, Value> entry) {
        return new Pair<Key, Value>(entry.getKey(), entry.getValue());
    }

    protected void checkList() {
        if (saveList == null) {
            saveList = toList();
        }
    }

    @Override
    public IPair<Key, Value> first() {
        return getPair(entrySet.iterator().next());
    }

    @Override
    public IPair<Key, Value> last() {
        checkList();
        return saveList.get(saveList.size() - 1);
    }

    @Override
    public boolean isEmpty() {
        return valueMap.isEmpty();
    }

    @Override
    public List<IPair<Key, Value>> toList() {
        ArrayList<IPair<Key, Value>> list = new ArrayList<IPair<Key, Value>>();
        for (Map.Entry<Key, Value> entry : entrySet) {
            list.add(getPair(entry));
        }
        return list;
    }

    @Override
    public List<IPair<Key, Value>> toConstList() {
        checkList();
        return saveList;
    }

    @Override
    public Iterator<IPair<Key, Value>> iterator() {
        return new MapIterator(entrySet.iterator());
    }

    public class MapIterator implements Iterator<IPair<Key, Value>> {
        Iterator<Map.Entry<Key, Value>> iterator;

        public MapIterator(Iterator<Map.Entry<Key, Value>> iterator) {
            this.iterator = iterator;
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public IPair<Key, Value> next() {
            Map.Entry<Key, Value> entry = iterator.next();
            return getPair(entry);
        }

        @Override
        public void remove() {

        }
    }
}
