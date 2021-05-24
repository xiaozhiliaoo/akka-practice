package org.lili.akka.common;

import java.io.Serializable;

public class SetRequest implements Serializable {
    public final String key;
    public final Object value;

    public SetRequest(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Set{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }
}
