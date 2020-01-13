package Model.Statements;

import Model.Exceptions.SuperCoolException;
import Model.Expressions.Expression;
import Model.PrgState;
import Model.Types.RefType;
import Model.Values.RefValue;
import Model.Values.Value;

import java.text.MessageFormat;

public class WriteHeapStatement implements IStatement {
    private String varName;
    private Expression expression;

    public WriteHeapStatement(String varName, Expression expression) {
        this.varName = varName;
        this.expression = expression;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "writeHeap(" + varName + ','  + expression + ')';
    }

    @Override
    public PrgState execute(PrgState state) {
        /*
            wH(Var_Name, expression)
                • it is a statement which takes a variable and an expression, the variable contains the heap
                address, the expression represents the new value that is going to be stored into the heap
                • first we check if var_name is a variable defined in SymTable, if its type is a Ref type and if
                the address from the RefValue associated in SymTable is a key in Heap. If not, the execution
                is stopped with an appropriate error message.
                • Second the expression is evaluated and the result must have its type equal to the
                locationType of the var_name type. If not, the execution is stopped with an appropriate
                message.
                • Third we access the Heap using the address from var_name and that Heap entry is updated
                to the result of the expression evaluation.
         */

        System.out.println("WRITING TO HEAP");
        if(state.getSymbolTable().get(varName)==null)
            throw new SuperCoolException(MessageFormat.format("{0} is not defined!", varName));

        Value value = expression.evaluate(state.getSymbolTable(), state.getHeap());
//        RefType getInnerType = (RefType)state.getSymbolTable().get(varName).getType();
//        if(!value.getType().equals(((RefType)getInnerType.getInner()).getInner()))
//            throw new SuperCoolException(MessageFormat.format("{0} - {1} type mismatch",
//                    value.getType(), getInnerType.getInner()));

        RefValue fromSymbolTable = (RefValue)state.getSymbolTable().get(varName);

        state.getHeap().put(fromSymbolTable.getAddress(), value);

        return null;
    }
}
