package Model.Statements;

import Model.PrgState;

public interface IStatement {
    PrgState execute(PrgState state);
    String toString();
}
