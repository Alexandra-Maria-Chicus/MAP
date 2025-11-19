package model.statement;

import exception.StatementExecutionException;
import model.expression.Expression;
import model.state.PrgState;
import model.type.RefType;
import model.type.Type;
import model.value.RefValue;
import model.value.Value;

public record NewStmt(String varName, Expression expr) implements IStmt {
    @Override
    public PrgState execute(PrgState state) {
        Value v= state.getSymTable().getValue(varName);
        if (v == null) {
            throw new StatementExecutionException("Variable " + varName + " not found");
        }
        if (!(v.getType() instanceof RefType refType)) {
            throw new StatementExecutionException("Variable " + varName + " is not a RefType. Got: " + v.getType());
        }
        Value exprValue=expr.evaluate(state.getSymTable(),state.getHeap());
        Type locationType= refType.getInner();
        if(!exprValue.getType().equals(locationType))
            throw new StatementExecutionException("Invalid type operators");
        int newAddr= state.getHeap().put(exprValue);
        state.getSymTable().setValue(varName, new RefValue(newAddr, locationType));
        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new NewStmt(varName, expr);
    }

    @Override
    public String toString() {
        return "new(" + varName + ", " + expr + ")";
    }
}
