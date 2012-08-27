package org.silentpom.jlinq.data.impl;

import org.silentpom.jlinq.data.GroupedData;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: pom
 * Date: 25.07.12
 * Time: 22:02
 */

/**
 * Класс для элемента в рейндже, который порождается операцией GroupBy
 */
public class GroupedDataImpl<Key, Data> extends Pair<Key, List<Data>> implements GroupedData<Key, Data> {
    public GroupedDataImpl(Key first, List<Data> second) {
        super(first, second);
    }
}
