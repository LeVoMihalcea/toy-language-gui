package Model.Statements;

import Model.PrgState;

public class NopStatement implements IStatement {
    @Override
    public PrgState execute(PrgState state) {
        return null;
    }

    @Override
    public String toString() {
        return "continue;";
    }
}
