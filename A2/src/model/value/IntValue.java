package model.value;

import model.type.IntType;
import model.type.Type;

public record IntValue(int val) implements Value {
    @Override
    public Type getType() {
        return new IntType();
    }
    @Override
    public String toString() {
        return Integer.toString(this.val);
    }
}
