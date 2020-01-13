package Model.Statements;

import Model.Exceptions.SuperCoolException;
import Model.Expressions.Expression;
import Model.PrgState;
import Model.Types.StringType;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import java.util.Stack;

public class OpenReadFileStatement implements IStatement{
    private Expression expression;

    public OpenReadFileStatement(Expression expression) {
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
        /*
        ExeStack is the following:
            • pop the statement
            • evaluate the exp and check whether its type is a StringType. If its type is not StringType, the
            execution is stopped with an appropriate message.
            • check whether the string value is not already a key in the FileTable. If it exists, the execution
            is stopped with an appropriate error message.
            • open in Java the file having the name equals to the computed string value, using an instance
            of the BufferedReader class. If the file does not exist or other IO error occurs the execution
            is stopped with an appropriate error message.
            • create a new entrance into the FileTable which maps the above computed string to the
            instance of the BufferedReader class created before.
         */
        Stack<IStatement> stack = state.getExecutionStack();
        Map<String, Value> symbolTable = state.getSymbolTable();
        Value value = expression.evaluate(state.getSymbolTable(), state.getHeap());


        if(value.getType().equals(new StringType())){
            StringValue stringValue = (StringValue)value;
            if(symbolTable.get(stringValue.getValue()) == null){
                try {
                    BufferedReader newReader = new BufferedReader(new FileReader(stringValue.getValue()));
                    state.getFileTable().put(stringValue.getValue(), newReader);
                }
                catch(FileNotFoundException fnfe){
                    throw new SuperCoolException(fnfe.getMessage());
                }
            }
            else throw new SuperCoolException("File already open!");
        }
        else{
            throw new SuperCoolException("Value is not a string!");
        }

        return null;
    }

    @Override
    public String toString() {
        return "OpenFile{" + expression + '}';
    }
}
