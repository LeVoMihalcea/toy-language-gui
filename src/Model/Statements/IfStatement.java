package Model.Statements;

import Model.Exceptions.SuperCoolException;
import Model.Expressions.Expression;
import Model.PrgState;
import Model.Types.BoolType;
import Model.Values.BoolValue;
import Model.Values.Value;

import java.util.Map;
import java.util.Stack;

public class IfStatement implements IStatement{
    private Expression expression;
    private IStatement thenStatement;
    private IStatement elseStatement;

    public IfStatement(Expression expression, IStatement thenStatement, IStatement elseStatement) {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public IStatement getThenStatement() {
        return thenStatement;
    }

    public void setThenStatement(IStatement thenStatement) {
        this.thenStatement = thenStatement;
    }

    public IStatement getElseStatement() {
        return elseStatement;
    }

    public void setElseStatement(IStatement elseStatement) {
        this.elseStatement = elseStatement;
    }

    @Override
    public String toString() {
        return "If(" + expression + ")" +
                "Then{" + thenStatement + "}" +
                "Else{" + elseStatement + "}";
    }

    public PrgState execute(PrgState state){
        Stack<IStatement> executionStack = state.getExecutionStack();
        Map<String, Value> symbolTable = state.getSymbolTable();
        Value value = expression.evaluate(symbolTable, state.getHeap());

        if(value.getType().equals(new BoolType())){
            BoolValue condition = (BoolValue) value;
            if(condition.getValue()){
                executionStack.push(thenStatement);
                state.setExecutionStack(executionStack);
//                return state;
            }
            else{
                executionStack.push(elseStatement);
                state.setExecutionStack(executionStack);
//                return state;
            }
        }
        else{
            throw new SuperCoolException("Wrong conditional type!");
        }
        return null;
    }
}
