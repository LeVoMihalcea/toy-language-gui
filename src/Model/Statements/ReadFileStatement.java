package Model.Statements;

import Model.Exceptions.SuperCoolException;
import Model.Expressions.Expression;
import Model.PrgState;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Stack;

import static java.lang.Integer.parseInt;

public class ReadFileStatement implements IStatement {
    private Expression expression;
    private String varName;

    public ReadFileStatement(Expression expression, String varName) {
        this.expression = expression;
        this.varName = varName;
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
        Its execution on the ExeStack is the following:
            • pop the statement
            • check whether var_name is defined in SymTable and its type is int. If not, the execution is
            stoped with an appropriate error message.
            • evaluate exp to a value that must be a string value. If any error occurs then terminate the
            execution with an appropriate error message.
            • using the previous step value we get the BufferedReader object associated in the FileTable.
            If there is not any entry associated to this value in the FileTable we stop the execution with
            an appropriate error message.
            • Reads a line from the file using readLine method of the BufferedReader. If line is null
            creates a zero int value. Otherwise translate the returned String into an int value (using
            Integer.parseInt(String)).
            • Update SymTable such that the var_name is mapped to the int value computed at the
            previous step.
         */
        Stack<IStatement> stack = state.getExecutionStack();
        Map<String, Value> symbolTable = state.getSymbolTable();
        Map<String, BufferedReader> fileTable = state.getFileTable();
        Value value = expression.evaluate(state.getSymbolTable(), state.getHeap());

        StringValue filename = (StringValue)value;

        if(!filename.getType().equals(new StringType()))
            throw new SuperCoolException("Not a string!");

        if(state.getFileTable().get(((StringValue)filename).getValue()) == null)
            throw new SuperCoolException(MessageFormat.format("File {0} is not open!", ((StringValue)filename).getValue()));

        BufferedReader bufferedReader = state.getFileTable().get(((StringValue)filename).getValue());

        if(state.getSymbolTable().get(varName) == null)
            throw new SuperCoolException(MessageFormat.format("Var {0} is not defined", varName));

        Value variableValue = state.getSymbolTable().get(varName);
        if(!variableValue.getType().equals(new IntType()))
            throw new SuperCoolException("Not an int!");

        IntValue intValue = (IntValue)variableValue;

        try{
            String line = bufferedReader.readLine();
            int number = 0;
            if(line != null)
                number = parseInt(line);
            intValue.setValue(number);
        }catch(IOException ioe){
            throw new SuperCoolException(ioe.getMessage());
        }

        return null;
    }

    @Override
    public String toString() {
        return "ReadFile{" + expression + "," + varName + '}';
    }
}
