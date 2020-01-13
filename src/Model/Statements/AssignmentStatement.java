package Model.Statements;

import Model.Exceptions.SuperCoolException;
import Model.Expressions.Expression;
import Model.PrgState;
import Model.Types.Type;
import Model.Values.Value;

import java.util.Map;
import java.util.Stack;

public class AssignmentStatement implements IStatement{
    private String id;
    private Expression expression;

    public AssignmentStatement(String id, Expression expression) {
        this.id = id;
        this.expression = expression;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        Value value = expression.evaluate(symbolTable, state.getHeap());

        if(symbolTable.get(id) != null){
            Type typeId = (symbolTable.get(id)).getType();
            if(value.getType().equals(typeId)){
                symbolTable.put(id, value);
            }
            else{
                throw new SuperCoolException("Type mismatch for variable" + id);
            }
        }
        else throw new SuperCoolException("Referencing before declaration!");

        return null;
    }

    @Override
    public String toString() {
        return id + " = " + expression.toString();
    }
}
