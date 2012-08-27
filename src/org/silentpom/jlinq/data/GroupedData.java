package org.silentpom.jlinq.data;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: pom
 * Date: 25.07.12
 * Time: 22:00
 */

/**
 * Интерфейс элемента в рейндже, порождаемый операцией GroupBy
 */
public interface GroupedData<Key, Data> extends IPair<Key, List<Data>> {

}
