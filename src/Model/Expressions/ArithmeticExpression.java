package Model.Expressions;

import Model.Exceptions.SuperCoolException;
import Model.Types.IntType;
import Model.Values.IntValue;
import Model.Values.Value;

import java.util.Map;

//+, -, *, /
public class ArithmeticExpression implements Expression{
    private Expression expression1;
    private Expression expression2;
    private int operator;

    public ArithmeticExpression(Expression expression1, Expression expression2, int operator) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operator = operator;
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

    public int getOperator() {
        return operator;
    }

    public void setOperator(int operator) {
        this.operator = operator;
    }

    @Override
    public Value evaluate(Map<String, Value> table, Map<Integer, Value> heap) {
        Value val1, val2;
        val1 = expression1.evaluate(table, heap);

        if(val1.getType().equals(new IntType())){
            val2 = expression2.evaluate(table, heap);
            if(val2.getType().equals(new IntType())) {
                int number1, number2;
                IntValue int1 = (IntValue) val1;
                IntValue int2 = (IntValue) val2;

                number1 = int1.getValue();
                number2 = int2.getValue();

                switch (operator) {
                    case 1:
                        return new IntValue(number1 + number2);
                    case 2:
                        return new IntValue(number1 - number2);
                    case 3:
                        return new IntValue(number1 * number2);
                    case 4: {
                        if (number2 != 0)
                            return new IntValue(number1 / number2);
                        else throw new SuperCoolException("Reached division by 0!");
                    }
                }
            }else throw new SuperCoolException("Second value is not an integer one!");
        }else throw new SuperCoolException("First value is not an integer one!");
        return new IntValue(0);
    }

    @Override
    public String toString() {
        switch(operator){
            case 1 : return expression1 + "+" + expression2;
            case 2 : return expression1 + "-" + expression2;
            case 3 : return expression1 + "*" + expression2;
            case 4 : return expression1 + "/" + expression2;
        }
        return null;
    }
}

