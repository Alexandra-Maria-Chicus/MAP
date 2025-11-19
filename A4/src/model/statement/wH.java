package model.statement;

import exception.StatementExecutionException;
import model.expression.Expression;
import model.state.PrgState;
import model.type.RefType;
import model.value.RefValue;
import model.value.Value;

public record wH(String varName, Expression expression) implements IStmt{

    @Override
    public PrgState execute(PrgState state) throws StatementExecutionException {
        if(!state.getSymTable().isDefined(varName)){
            throw new StatementExecutionException("Variable " + varName + " is not defined");
        }
        if(!(state.getSymTable().getType(varName) instanceof RefType )){
            throw new StatementExecutionException("Variable " + varName + " is not of type RefType");
        }
        Value v= state.getSymTable().getValue(varName);
        if(v instanceof RefValue rv) {
            if (state.getHeap().getValue(rv.getAddress()) == null)
                throw new StatementExecutionException("The memory is not allocated!");
            Value valExpr=expression.evaluate(state.getSymTable(), state.getHeap());
            if(valExpr.getType().equals(rv.getLocationType())){
                state.getHeap().update(rv.getAddress(),valExpr);
            }
            else  throw new StatementExecutionException("The memory is not allocated!");
        }
        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new wH(varName, expression.deepCopy());
    }
    @Override
    public String toString() {
        return "writeHeap(" + varName + ", " + expression.toString() + ")";
    }
}
