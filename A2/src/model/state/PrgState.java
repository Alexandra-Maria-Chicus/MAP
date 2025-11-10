package model.state;

import model.statement.IStmt;
import model.value.Value;

public class PrgState {
    private final MyIStack<IStmt> exeStack;
    private final MyIDictionary<String, Value> symTable;
    private final MyIList<String> out;

    public PrgState(MyIStack<IStmt> exeStack, MyIDictionary<String, Value> symTable, MyIList<String> out, IStmt program) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;

        exeStack.push(program);
    }

    public boolean isNotCompleted() {
        return !exeStack.isEmpty();
    }

    @Override
    public String toString() {
        return "--------------------------------------------------------\n" +
                exeStack.toString() + "\n" +
                symTable.toString() + "\n" +
                out.toString() + "\n" +
                "--------------------------------------------------------";
    }

    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }

    public MyIList<String> getOut() {
        return out;
    }

    public MyIDictionary<String, Value> getSymTable() {
        return symTable;
    }
}
