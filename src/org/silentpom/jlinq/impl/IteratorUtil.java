package org.silentpom.jlinq.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: pom
 * Date: 11.07.2012
 * Time: 22:03:50
 */
public class IteratorUtil {

    public static <T> List<T> fillList(List<T> list, Iterator<T> it) {
        while (it.hasNext()) {
            list.add(it.next());
        }

        return list;
    }

    public static <T> T first(Iterator<T> iter) {
        return iter.next();
    }

    public static <T> T first(Collection<T> collection) {
        for (T nextElem : collection) {
            return nextElem;
        }
        return null;
    }

    public static <T> T last(Iterator<T> iter) {
        T elem = null;
        while (iter.hasNext()) {
            elem = iter.next();
        }
        return elem; // sad but true
    }

    public static <T> T last(Collection<T> collection) {
        T elem = null;
        for (T nextElem : collection) {
            elem = nextElem;
        }
        return elem; // sad but true
    }

    public static boolean equals(Object a, Object b) {
        if (a == null) {
            return b == null;
        }

        return a.equals(b);
    }


    @SuppressWarnings("unchecked")
    public static <T> boolean equalsIters(Iterator<T> it1, Iterator<T> it2) {
        List list1 = new ArrayList();
        List list2 = new ArrayList();

        while (it1.hasNext()) {
            list1.add(it1.next());
        }

        while (it2.hasNext()) {
            list2.add(it2.next());
        }

        return equalsLists(list1, list2);
    }

    public static <T> boolean equalsLists(List list1, List list2) {
        if (list1.size() != list2.size()) {
            return false;
        }

        for (int i = 0; i < list1.size(); ++i) {
            if (!equals(list1.get(i), list2.get(i))) {
                return false;
            }
        }

        return true;
    }

}
