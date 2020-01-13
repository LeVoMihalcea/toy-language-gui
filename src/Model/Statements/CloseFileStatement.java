package Model.Statements;

import Model.Exceptions.SuperCoolException;
import Model.Expressions.Expression;
import Model.PrgState;
import Model.Types.StringType;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Stack;

public class CloseFileStatement implements IStatement {
    private Expression expression;

    public CloseFileStatement(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) {
        Stack<IStatement> stack = state.getExecutionStack();
        Map<String, Value> symbolTable = state.getSymbolTable();
        Value value = expression.evaluate(state.getSymbolTable(), state.getHeap());


        if(value.getType().equals(new StringType())){
            StringValue stringValue = (StringValue)value;
            if(state.getFileTable().get(stringValue.getValue()) != null)
                try {
                    state.getFileTable().get(stringValue.getValue()).close();
                    state.getFileTable().remove(stringValue.getValue());
                }
                catch(IOException ioe){
                    throw new SuperCoolException(ioe.getMessage());
                }
            else throw new SuperCoolException(MessageFormat.format("File {0} is not open!", stringValue.getValue()));

        }
        else{
            throw new SuperCoolException("Value is not a string!");
        }

        return null;
    }

    @Override
    public String toString() {
        return "CloseFileStatement{" + expression + '}';
    }
}
