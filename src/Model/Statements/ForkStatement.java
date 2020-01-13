package Model.Statements;

import Model.PrgState;
import Model.Values.Value;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class ForkStatement implements IStatement {
    IStatement statement;

    public ForkStatement(IStatement statement) {
        this.statement = statement;
    }

    public IStatement getStatement() {
        return statement;
    }

    public void setStatement(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) {
        HashMap<String, Value> symbolTable = new HashMap<String, Value>(state.getSymbolTable());
        Stack<IStatement> executionStack = new Stack<>();
        List<Value> output = state.getOutput();
        Map<Integer, Value> heap = state.getHeap();
        Map<String, BufferedReader> fileTable = state.getFileTable();

        return new PrgState(executionStack, symbolTable, output, heap, fileTable, statement);
    }

    @Override
    public String toString() {
        return "fork(" + statement + ')';
    }
}
