package org.silentpom.jlinq.function.impl.accumulate;

/**
 * Created by IntelliJ IDEA.
 * Author: pom
 * Date: 26.07.12
 * Time: 22:49
 */
public class StringAccumulate extends BaseStringAccumulate<String> {
    public StringAccumulate() {
    }

    public StringAccumulate(String suffix) {
        super(suffix);
    }

    @Override
    protected String getString(String object) {
        if (object == null) {
            return "";
        }
        return object;
    }
}
