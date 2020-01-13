package Model.Expressions;

import Model.Exceptions.SuperCoolException;
import Model.Values.Value;

import java.util.Map;

public class VariableExpression implements Expression {
    private String id;

    public VariableExpression(String id) {
        this.id = id;
    }

    @Override
    public Value evaluate(Map<String, Value> table, Map<Integer, Value> heap) {
        if(table.get(id) != null){
            return table.get(id);
        }
        else throw new SuperCoolException("Variable" + id + "is not defined!");
    }

    @Override
    public String toString() {
        return id;
    }
}
