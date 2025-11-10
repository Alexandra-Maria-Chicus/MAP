package model.value;

import model.type.BooleanType;
import model.type.IntType;
import model.type.Type;

public record BoolValue(boolean val) implements Value {
    public String toString() {
        return Boolean.toString(val);
    }

    @Override
    public Type getType() {
        return new BooleanType();
    }
}
