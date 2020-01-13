package Model.Expressions;

import Model.Exceptions.SuperCoolException;
import Model.Types.BoolType;
import Model.Values.BoolValue;
import Model.Values.Value;

import java.util.Map;

public class LogicExpression implements Expression {
    private Expression expression1;
    private Expression expression2;
    private int operand;

    public LogicExpression(Expression expression1, Expression expression2, int operand) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operand = operand;
    }

    public Expression getExpression1() {
        return expression1;
    }

    public void setExpression1(Expression expression1) {
        this.expression1 = expression1;
    }

    public Expression getExpression2() {
        return expression2;
    }

    public void setExpression2(Expression expression2) {
        this.expression2 = expression2;
    }

    public int getOperand() {
        return operand;
    }

    public void setOperand(int operand) {
        this.operand = operand;
    }

    @Override
    public Value evaluate(Map<String, Value> table, Map<Integer, Value> heap) {
        Value firstValue = expression1.evaluate(table, heap);
        Value secondValue = expression2.evaluate(table, heap);

        if(firstValue.getType().equals(new BoolType())){
            if(secondValue.getType().equals(new BoolType())){

                BoolValue boolValue1 = (BoolValue)firstValue;
                BoolValue boolValue2 = (BoolValue)secondValue;

                if(operand == 0){
                    // and
                    return boolValue1.and(boolValue2);
                }
                else if (operand == 1){
                    // or
                    return boolValue1.or(boolValue2);
                }
                else if (operand == 2){
                    // ==
                    return boolValue1.equal(boolValue2);
                }
            }
            else throw new SuperCoolException("Second variable is not of boolean type!");
        }
        else throw new SuperCoolException("First variable is not of boolean type!");

        //default case so that compiler doesn't complain
        return new BoolValue(false);
    }

    @Override
    public String toString() {
        if(operand == 0)
            return expression1 + "&&" + expression2;
        else if(operand == 1)
            return expression1 + "||" + expression2;
        else if(operand == 2)
            return expression1 + "==" + expression2;
        return "Something is terribly wrong! aka operand not existing yet";
    }
}
