package Model.Statements;

import Model.Exceptions.SuperCoolException;
import Model.Expressions.Expression;
import Model.PrgState;
import Model.Types.RefType;
import Model.Values.RefValue;
import Model.Values.Value;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Stack;

public class NewStatement implements IStatement {
    /*
        new(var_name, expression)
            • it is a statement which takes a variable name and an expression
            • check whether var_name is a variable in SymTable and its type is a RefType. If not, the
            execution is stopped with an appropriate error message.
            • Evaluate the expression to a value and then compare the type of the value to the
            locationType from the value associated to var_name in SymTable. If the types are not equal,
            the execution is stopped with an appropriate error message.
            • Create a new entry in the Heap table such that a new key (new free address) is generated and
            it is associated to the result of the expression evaluation
            • in SymTable update the RefValue associated to the var_name such that the new RefValue
            has the same locationType and the address is equal to the new key generated in the Heap at
            the previous step
     */
    private String varName;
    private Expression expression;

    public NewStatement(String varName, Expression expression) {
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
    public PrgState execute(PrgState state) {
        /*
            new(var_name, expression)
                • it is a statement which takes a variable name and an expression
                • check whether var_name is a variable in SymTable and its type is a RefType. If not, the
                execution is stopped with an appropriate error message.
                • Evaluate the expression to a value and then compare the type of the value to the
                locationType from the value associated to var_name in SymTable. If the types are not equal,
                the execution is stopped with an appropriate error message.
                • Create a new entry in the Heap table such that a new key (new free address) is generated and
                it is associated to the result of the expression evaluation
                • in SymTable update the RefValue associated to the var_name such that the new RefValue
                has the same locationType and the address is equal to the new key generated in the Heap at
                the previous step
         */

        Stack<IStatement> stack = state.getExecutionStack();
        Map<String, Value> symbolTable = state.getSymbolTable();

        if(state.getSymbolTable().get(varName)==null)
            throw new SuperCoolException(MessageFormat.format("{0} is not defined!", varName));
        else if(state.getSymbolTable().get(varName).getType().equals(new RefType(state.getSymbolTable().get(varName).getType())))
            throw new SuperCoolException(MessageFormat.format("{0} is not of type RefType!", varName));

        Value value = expression.evaluate(symbolTable, state.getHeap());

        Value location = symbolTable.get(varName);

        if(location.getType() instanceof RefType) {
            if (!((RefType) location.getType()).getInner().equals(value.getType())) {
                throw new SuperCoolException("Type mismatch!");
            }
        }
        else if(!location.getType().equals(value.getType())){
            throw new SuperCoolException("Type mismatch!");
        }

        Map<Integer, Value> heap = state.getHeap();

        Integer poll = PrgState.freeSpots.poll();
        if(poll == null) throw new SuperCoolException("No more empty places on the heap!");

        heap.put(poll, value);

        symbolTable.put(varName, new RefValue(poll, location.getType()));

        return null;
    }

    @Override
    public String toString() {
        return "new " + varName + " <-- " + expression;
    }
}
