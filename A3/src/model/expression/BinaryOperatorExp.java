package model.expression;

import exception.DivisionByZeroException;
import exception.InvalidTypeException;
import model.state.MyIDictionary;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.Value;

public record BinaryOperatorExp(Expression left, Expression right, String operator) implements Expression {

    @Override
    public Value evaluate(MyIDictionary<String, Value> symbolTable) throws DivisionByZeroException, InvalidTypeException {
        var leftTerm=left.evaluate(symbolTable);
        var rightTerm=right.evaluate(symbolTable);

        switch(operator) {
            case "+","-","*","/": {
                if(!(leftTerm.equals(rightTerm) || leftTerm instanceof IntValue)){
                    throw new InvalidTypeException("Type Error");
                }
                var leftValue = (IntValue) leftTerm;
                var rightValue = (IntValue) rightTerm;
                if (operator.equals("/") && rightValue.val()== 0) {
                    throw new DivisionByZeroException("Cannot perform division by zero");
                } else
                    return evaluateArithmeticExpression(leftValue, rightValue);
            }
            case "&&","||": {
                if(!(leftTerm.equals(rightTerm) || leftTerm instanceof BoolValue)){
                    throw new InvalidTypeException("Type Error");
                }
                var leftValueB = (BoolValue) leftTerm;
                var rightValueB = (BoolValue) rightTerm;
                evaluateBooleanExpression(leftValueB, rightValueB);
            }
        }
        throw new IllegalArgumentException("Unknown operator" + operator);
    }

    private IntValue evaluateArithmeticExpression(IntValue leftValue, IntValue rightValue) {
        return switch (operator) {
            case "+" -> new IntValue(leftValue.val() + rightValue.val());
            case "-" -> new IntValue(leftValue.val() - rightValue.val());
            case "*" -> new IntValue(leftValue.val() * rightValue.val());
            case "/" -> new IntValue(leftValue.val() / rightValue.val());
            default -> null;
        };
    }

    private BoolValue evaluateBooleanExpression(BoolValue leftValue, BoolValue rightValue) {
        return switch (operator) {
            case "&&" -> new BoolValue(leftValue.val() && rightValue.val());
            case "||" -> new BoolValue(leftValue.val() || rightValue.val());
            default -> null;
        };
    }

    @Override
    public String toString(){
        return left.toString()+operator+right.toString();
    }
}


