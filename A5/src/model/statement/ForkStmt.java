package model.statement;

import exception.StatementExecutionException;
import model.state.MyStack;
import model.state.PrgState;

public record ForkStmt(IStmt stmt) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws StatementExecutionException {
        MyStack<IStmt> newStack = new MyStack<>();
        PrgState newPrg = new PrgState(newStack,
                state.getSymTable().deepCopy(),
                state.getOut(),
                stmt,
                state.getFileTable(),
                state.getHeap(),PrgState.getNewProgramId());
        return newPrg;
    }

    @Override
    public IStmt deepCopy() {
        return new ForkStmt(stmt);
    }
    @Override
    public String toString() {
        return "fork(" + stmt.toString() + ")";
    }
}
