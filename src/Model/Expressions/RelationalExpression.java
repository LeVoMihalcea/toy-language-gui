package Model.Expressions;

import Model.Exceptions.SuperCoolException;
import Model.Operations.ComparisonOperation;
import Model.Types.IntType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.Value;

import java.text.MessageFormat;
import java.util.Map;

public class RelationalExpression implements Expression{
    private Expression first;
    private ComparisonOperation comparisonOperation;
    private Expression second;

    public RelationalExpression(Expression first, ComparisonOperation comparisonOperation, Expression second) {
        this.first = first;
        this.comparisonOperation = comparisonOperation;
        this.second = second;
    }

    public Expression getFirst() {
        return first;
    }

    public void setFirst(Expression first) {
        this.first = first;
    }

    public ComparisonOperation getComparisonOperation() {
        return comparisonOperation;
    }

    public void setComparisonOperation(ComparisonOperation comparisonOperation) {
        this.comparisonOperation = comparisonOperation;
    }

    public Expression getSecond() {
        return second;
    }

    public void setSecond(Expression second) {
        this.second = second;
    }

    @Override
    public Value evaluate(Map<String, Value> table, Map<Integer, Value> heap) {
        Value firstValue = first.evaluate(table, heap);
        Value secondValue = second.evaluate(table, heap);

        if(!firstValue.getType().equals(new IntType())){
            throw new SuperCoolException(MessageFormat.format("Value {0} - Expression {1} is not of Integer type!", firstValue, first));
        }

        if(!secondValue.getType().equals(new IntType())){
            throw new SuperCoolException(MessageFormat.format("Value {0} - Expression {1} is not of Integer type!", secondValue, second));
        }

        IntValue firstIntValue = (IntValue)firstValue;
        IntValue secondIntValue = (IntValue)secondValue;

        switch(comparisonOperation){
            case Smaller:
                return new BoolValue(firstIntValue.getValue() < secondIntValue.getValue());
            case SmallerOrEqual:
                return new BoolValue(firstIntValue.getValue() <= secondIntValue.getValue());
            case Equal:
                return new BoolValue(firstIntValue.getValue() == secondIntValue.getValue());
            case NotEqual:
                return new BoolValue(firstIntValue.getValue() != secondIntValue.getValue());
            case Greater:
                return new BoolValue(firstIntValue.getValue() > secondIntValue.getValue());
            case GreaterOrEqual:
                return new BoolValue(firstIntValue.getValue() >= secondIntValue.getValue());
        }

        throw new SuperCoolException("Something is terribly wrong! (operand out of bounds?)");
    }

    @Override
    public String toString() {
        switch(comparisonOperation){
            case Smaller:
                return first + " < " + second;
            case SmallerOrEqual:
                return first + " <= " + second;
            case Equal:
                return first + " == " + second;
            case NotEqual:
                return first + " != " + second;
            case Greater:
                return first + " > " + second;
            case GreaterOrEqual:
                return first + " >= " + second;
        }
        return "something went terribly wrong! Unknown operand";
    }
}
