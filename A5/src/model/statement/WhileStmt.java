package model.statement;

import exception.StatementExecutionException;
import model.expression.Expression;
import model.state.PrgState;
import model.value.BoolValue;

public record WhileStmt(Expression condition, IStmt stmt) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws StatementExecutionException {
        if(condition.evaluate(state.getSymTable(),state.getHeap()) instanceof BoolValue boolValue){
            if(boolValue.val()){
                state.getExeStack().push(this);
                state.getExeStack().push(stmt);
            }
        } else
            throw new StatementExecutionException("Condition evaluation not boolean");
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(condition, stmt);
    }

    @Override
    public String toString() {
        return "While(" + condition.toString() + "){" + stmt.toString() + "};";
    }
}
