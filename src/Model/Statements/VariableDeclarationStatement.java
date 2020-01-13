package Model.Statements;

import Model.Exceptions.SuperCoolException;
import Model.PrgState;
import Model.Types.*;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;

import java.util.Map;
import java.util.Objects;

public class VariableDeclarationStatement implements IStatement {
    private Type type;
    private String name;
    private Value value;

    public VariableDeclarationStatement(Type type, String name, Value value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public VariableDeclarationStatement(Type type, String name) {
        this.type = type;
        this.name = name;
        this.value = null;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return type + " " + name;
    }

    @Override
    public PrgState execute(PrgState state) {
        Map<String, Value> symbolTable = state.getSymbolTable();

        if(symbolTable.get(name)==null){
            if(this.value != null){
                symbolTable.put(this.name, this.value);
            }
            else symbolTable.put(this.name, this.type.defaultValue());
        }
        else {
            throw new SuperCoolException("Variable already defined!");
        }
        return null;
    }
}
