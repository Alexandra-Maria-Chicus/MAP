package model.expression;

import model.state.IHeap;
import model.state.MyIDictionary;
import model.value.RefValue;
import model.value.Value;

public record rH(Expression expression) implements Expression {
    @Override
    public Value evaluate(MyIDictionary<String, Value> symbolTable, IHeap<Value> heap) {
        Value val = expression.evaluate(symbolTable, heap);
        if(!(val instanceof RefValue))
            throw new IllegalArgumentException();
        return heap.getContent().get(((RefValue)val).getAddress());
    }

    @Override
    public Expression deepCopy() {
        return new rH(expression);
    }
    @Override
    public String toString() {
        return "readHeap(" + expression.toString() + ")";
    }
}
