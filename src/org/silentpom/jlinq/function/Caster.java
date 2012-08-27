package org.silentpom.jlinq.function;

/**
 * Created by IntelliJ IDEA.
 * Author: pom
 * Date: 12.07.2012
 * Time: 22:35:59
 */
public interface Caster<New, Old> {
    public New cast(Old t);
}
