package model.expression;
import model.state.IHeap;
import model.state.MyIDictionary;
import model.value.Value;

public interface Expression {
    Value evaluate(MyIDictionary<String,Value> symbolTable, IHeap<Value> heap);
    String toString();
    Expression deepCopy();
}
