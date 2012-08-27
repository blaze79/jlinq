package org.silentpom.jlinq.function.impl.accumulate;

/**
 * Created by IntelliJ IDEA.
 * Author: pom
 * Date: 27.07.12
 * Time: 22:48
 */

/**
 * Вычисляет сумму чисел
 */
public class LongSumm extends AccumulateNonNull<Long> {
    /**
     * {@inheritDoc}
     */
    @Override
    public Long valueImpl(Long elem, Long last) {
        return elem + last;
    }
}
