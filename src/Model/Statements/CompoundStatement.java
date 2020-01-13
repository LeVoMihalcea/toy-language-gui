package Model.Statements;

import Model.PrgState;

import java.util.Stack;

public class CompoundStatement implements IStatement {
    private IStatement first;
    private IStatement second;

    public CompoundStatement(IStatement first, IStatement second) {
        this.first = second;
        this.second = first;
    }

    public IStatement getFirst() {
        return first;
    }

    public void setFirst(IStatement first) {
        this.first = first;
    }

    public IStatement getSecond() {
        return second;
    }

    public void setSecond(IStatement second) {
        this.second = second;
    }

    @Override
    public PrgState execute(PrgState state) {
        Stack<IStatement> stack = state.getExecutionStack();
        stack.push(first);
        stack.push(second);
        return null;
    }

    @Override
    public String toString() {
        return "[" + first.toString() + ";" + second.toString() + "]";
    }
}
