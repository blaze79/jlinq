package org.silentpom.jlinq;

import junit.framework.TestCase;
import org.silentpom.jlinq.data.GroupedData;
import org.silentpom.jlinq.data.IPair;
import org.silentpom.jlinq.function.Caster;
import org.silentpom.jlinq.function.KeySelector;
import org.silentpom.jlinq.function.Predicate;
import org.silentpom.jlinq.function.group.GroupResultFunctor;
import org.silentpom.jlinq.function.group.GroupResultProcessor;
import org.silentpom.jlinq.function.impl.accumulate.LongSumm;
import org.silentpom.jlinq.function.impl.accumulate.Maximum;
import org.silentpom.jlinq.function.impl.accumulate.Minimum;
import org.silentpom.jlinq.function.impl.selectors.FirstSelector;
import org.silentpom.jlinq.function.impl.selectors.SecondSelector;
import org.silentpom.jlinq.function.impl.selectors.TSelector;
import org.silentpom.jlinq.impl.IteratorUtil;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * Author: pom
 * Date: 11.07.2012
 * Time: 22:24:16
 */
public class LinqTest extends TestCase {
    Long[] dataArray = {1L, 2L, 3L, 4L, 5L, 6L, 7L};
    List<Long> dataList = Arrays.asList(dataArray);

    Long[] evenArray = {2L, 4L, 6L};
    List<Long> evenList = Arrays.asList(evenArray);
    List<Long> oddList = Arrays.asList(1L, 3L, 5L, 7L);
    Long evenSum;
    Long oddSum;

    List<Long> addList = new ArrayList<Long>();
    List<Long> addListBack = new ArrayList<Long>();

    List<Long> rightList = new ArrayList<Long>(evenList);

    List<String> strList = Arrays.asList("6", "4", "2");


    {
        Collections.reverse(rightList);

        addList.addAll(dataList);
        addList.addAll(evenList);

        addListBack.addAll(evenList);
        addListBack.addAll(dataList);

        evenSum = sumListTest(evenList);
        oddSum = sumListTest(oddList);
    }

    private Long sumListTest(List<Long> list) {
        long result = 0;
        for (Long l : list) {
            result += l;
        }

        return result;
    }

    Predicate<Long> odd = new Predicate<Long>() {
        @Override
        public boolean value(Long elem) {
            return (elem % 2) == 0;
        }
    };

    Predicate<Long> notOdd = new Predicate<Long>() {
        @Override
        public boolean value(Long elem) {
            return (elem % 2) != 0;
        }
    };

    public void testLinqList() throws Exception {
        List<Long> test = JLinq.from(dataList).toList();
        assertTrue("test list", IteratorUtil.equalsLists(dataList, test));
    }

    public void testLinqIter() throws Exception {
        Iterator<Long> test = JLinq.from(dataList).iterator();
        assertTrue("test iter", IteratorUtil.equalsIters(dataList.iterator(), test));
    }

    public void testLinqWhere() throws Exception {
        Iterator<Long> test = JLinq.from(dataList).where(odd).iterator();

        assertEquals("test first where", JLinq.from(dataList).where(odd).first(), (Long) 2L);
        assertEquals("test last where", JLinq.from(dataList).where(odd).last(), (Long) 6L);
        assertTrue("test iter where", IteratorUtil.equalsIters(evenList.iterator(), test));
    }

    public void testReverse() throws Exception {
        LinqRange<Long> reverse = JLinq.from(dataList).where(odd).reverse();

        assertEquals("test reverse first", reverse.first(), (Long) 6L);
        assertEquals("test reverse last", reverse.last(), (Long) 2L);

        assertTrue("test iter reverse where", IteratorUtil.equalsIters(rightList.iterator(), reverse.iterator()));
        assertTrue("test list reverse where", IteratorUtil.equalsLists(rightList, reverse.toList()));
    }

    public void testReverseDouble() throws Exception {
        LinqRange<Long> reverse = JLinq.from(dataList).reverse().where(odd).reverse();
        assertTrue("test list reverse from reverse", IteratorUtil.equalsLists(evenList, reverse.toList()));

        reverse = JLinq.from(dataList).reverse().reverse();
        assertTrue("test list reverse reverse", IteratorUtil.equalsLists(dataList, reverse.toList()));
    }

    public void testCast() throws Exception {

        Caster<String, Long> caster = new Caster<String, Long>() {
            @Override
            public String cast(Long t) {
                return t.toString();
            }
        };

        LinqRange<String> reverse = JLinq.from(dataList).reverse().where(odd).cast(caster);
        assertTrue("test list reverse where cast", IteratorUtil.equalsLists(strList, reverse.toList()));

        reverse = JLinq.from(dataList).where(odd).cast(caster).reverse();
        assertTrue("test list where cast reverse", IteratorUtil.equalsLists(strList, reverse.toList()));
    }

    public void testSort() throws Exception {

        Caster<String, Long> caster = new Caster<String, Long>() {
            @Override
            public String cast(Long t) {
                return t.toString();
            }
        };

        LinqRange<String> sort = JLinq.from(dataList).where(odd).cast(caster).sort(false);
        LinqRange<String> reverse = JLinq.from(dataList).where(odd).cast(caster).reverse();

        assertTrue("test list where cast reverse", IteratorUtil.equalsLists(sort.toList(), reverse.toList()));
    }

    public void testAdd() throws Exception {
        Long emptyArray[] = {};
        Iterator<Long> iter = JLinq.from(emptyArray).add(JLinq.from(emptyArray)).iterator();

        assertTrue("test list empty add both", IteratorUtil.equalsIters(iter, Arrays.asList(emptyArray).iterator()));

        Iterator<Long> iter1 = JLinq.from(emptyArray).add(JLinq.from(dataList)).iterator();
        Iterator<Long> iter2 = JLinq.from(dataList).add(JLinq.from(emptyArray)).iterator();

        assertTrue("test list empty add left", IteratorUtil.equalsIters(iter1, dataList.iterator()));
        assertTrue("test list empty add right", IteratorUtil.equalsIters(iter2, dataList.iterator()));

        Iterator<Long> iter3 = JLinq.from(dataList).add(JLinq.from(evenList)).iterator();
        assertTrue("test list add head", IteratorUtil.equalsIters(iter3, addList.iterator()));

        Iterator<Long> iter4 = JLinq.from(evenList).add(JLinq.from(dataList)).iterator();
        assertTrue("test list add tail", IteratorUtil.equalsIters(iter4, addListBack.iterator()));
    }

    public void testRemove() throws Exception {

        LinqRange<Long> test1 = JLinq.from(dataList).remove(JLinq.from(dataList).where(odd));
        LinqRange<Long> test2 = JLinq.from(dataList).where(notOdd);

        assertTrue("test list remove", IteratorUtil.equalsIters(test1.iterator(), test2.iterator()));
    }

    public void testRemoveWithKeys() throws Exception {

        LinqRange<Long> test1 = JLinq.from(dataList).remove(JLinq.from(evenList), new KeySelector<Long, String>() {
            @Override
            public String getKey(Long x) {
                return x.toString();
            }
        });
        LinqRange<Long> test2 = JLinq.from(dataList).where(notOdd);

        assertTrue("test list remove keys", IteratorUtil.equalsIters(test1.iterator(), test2.iterator()));
    }

    public void testRemoveKeys() throws Exception {

        LinqRange<Long> test1 = JLinq.from(dataList).removeKeys(JLinq.from(strList), new KeySelector<Long, String>() {
            @Override
            public String getKey(Long x) {
                return x.toString();
            }
        });
        LinqRange<Long> test2 = JLinq.from(dataList).where(notOdd);

        assertTrue("test list remove keys", IteratorUtil.equalsIters(test1.iterator(), test2.iterator()));
    }

    public void testGroupBy() throws Exception {
        LinqRange<GroupedData<Long, Long>> linqRange =
                JLinq.from(dataList)
                        .groupBy(new KeySelector<Long, Long>() {
                                    @Override
                                    public Long getKey(Long x) {
                                        return x % 2;
                                    }
                                }, new GroupResultProcessor<Long, Long>() {
                            @Override
                            public List<Long> processList(Long k, List<Long> longs) {
                                return JLinq.from(longs).sort(true).toList();
                            }
                        }
                        )
                        .sort(new KeySelector<GroupedData<Long, Long>, Long>() {
                            @Override
                            public Long getKey(GroupedData<Long, Long> x) {
                                return x.getFirst();
                            }
                        }, true)
                        .sort(new FirstSelector<Long, List<Long>>(), true);


        List<Long> div0 = linqRange.first().getSecond();
        List<Long> div1 = linqRange.last().getSecond();

        assertTrue("test list group by", IteratorUtil.equalsLists(div0, evenList));
        assertTrue("test list group by", IteratorUtil.equalsLists(div1, oddList));
    }

    public void testGroupByAcc() throws Exception {
        LinqRange<IPair<Long, Long>> linqRange =
                JLinq.from(dataList)
                        .groupBy(new TSelector<Long>() {
                                    @Override
                                    public Long getKey(Long x) {
                                        return x % 2;
                                    }
                                }, new GroupResultFunctor<Long, Long, Long>() {
                            @Override
                            public Long processList(Long k, List<Long> longs) {
                                return JLinq.from(longs).accumulate(new LongSumm(), 0L);
                            }
                        }
                        )
                        .sort(new FirstSelector<Long, Long>(), true);

        Long div0 = linqRange.first().getSecond();
        Long div1 = linqRange.last().getSecond();

        assertEquals("test list group by", div0, evenSum);
        assertEquals("test list group by", div1, oddSum);
    }

    @SuppressWarnings("unchecked")
    public void testAccumulate() throws Exception {

        LinqRange<Long> linq = JLinq.from(JLinq.from(dataArray).accumulates(new Maximum<Long>(), new Minimum<Long>()));
        assertEquals("test max", (Long) 7L, linq.first());
        assertEquals("testMin", (Long) 1L, linq.last());
    }

    public void testRemoveRetain() throws Exception {
        assertTrue("test remove", IteratorUtil.equalsLists(oddList,
                JLinq.from(dataArray).remove(JLinq.fromT(2L, 4L, 6L)).toList()));

        assertTrue("test retain", IteratorUtil.equalsLists(evenList,
                JLinq.from(dataArray).retain(JLinq.fromT(2L, 4L, 6L, 8L, 10L)).toList()));
    }

    public void testJoin() {
        for (IPair<Long, List<Long>> pair : JLinq.from(dataList).join(JLinq.from(oddList))) {
            int listLen = (int) (pair.getFirst() % 2);
            assertEquals(listLen, pair.getSecond().size());
        }
    }

    public void testJoin2() {
        TSelector<Long> selector = new TSelector<Long>();

        LinqRange<IPair<Long, Long>> result = JLinq.from(dataList).join(JLinq.from(oddList), selector, selector, new GroupResultFunctor<Long, Long, Long>() {
            @Override
            public Long processList(Long k, List<Long> longs) {
                return JLinq.from(longs).accumulate(new LongSumm(), 0L);
            }
        });
        for (IPair<Long, Long> pair : result) {
            Long sum = pair.getFirst() % 2 != 0 ? pair.getFirst() : 0L;
            assertEquals(sum, pair.getSecond());
        }
    }

    public void testContains() {
        assertTrue("contains 5", JLinq.fromT(1L, 2L, 3L).add(JLinq.fromT(4L, 5L, 6L)).reverse().contains(5L));
        assertFalse("not contains 7", JLinq.fromT(1L, 2L, 3L).add(JLinq.fromT(4L, 5L, 6L)).reverse().contains(7L));
    }

    public void testMap() {
        Map<Long, Long> map = new HashMap<Long, Long>();
        for (Long data : dataArray) {
            map.put(data, data + 5L);
        }

        Long result = JLinq.from(map).where(new Predicate<IPair<Long, Long>>() {
            @Override
            public boolean value(IPair<Long, Long> elem) {
                return elem.getFirst() == 5L;
            }
        }).first().getSecond();

        assertTrue("test map", result == 10L);

        result = JLinq.from(map).where(new Predicate<IPair<Long, Long>>() {
            @Override
            public boolean value(IPair<Long, Long> elem) {
                return elem.getFirst() == 6L;
            }
        }).cast(new SecondSelector<Long, Long>()).first();
        assertTrue("test map", result == 11L);
    }

    public void testMapAndJoin() {
        Map<Long, Long> map = new HashMap<Long, Long>();
        for (Long data : dataArray) {
            map.put(data, data + 5L);
        }

        List<Long> list = JLinq.from(evenList).join(JLinq.from(map), new FirstSelector<Long, Long>(), new SecondSelector<Long, Long>())
                .where(new Predicate<IPair<Long, List<Long>>>() {
                    @Override
                    public boolean value(IPair<Long, List<Long>> elem) {
                        return elem.getFirst() == 4;
                    }
                }).first().getSecond();

        assertTrue("map and join test", IteratorUtil.equalsLists(list, Arrays.asList(9L)));
    }
}
