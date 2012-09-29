package org.silentpom.jlinq.impl;

import org.silentpom.jlinq.LinqRange;
import org.silentpom.jlinq.function.Caster;
import org.silentpom.jlinq.impl.range.trick.CastRange;
import org.silentpom.jlinq.range.Range;

/**
 * ConvertedRange
 *
 * @author Vladislav Kogut
 * @version $Id: Exp $
 */
public class ConvertedRange<Old, New> {
    Range<Old> range;

    public ConvertedRange(Range<Old> range) {
        this.range = range;
    }

    public LinqRange<New> cast(Caster<New, Old> caster) {
        return new LinqRange<New>(new CastRange<New, Old>(range, caster));
    }
}
