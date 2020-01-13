package Repository;

import Model.Exceptions.SuperCoolException;
import Model.PrgState;

import java.io.PrintWriter;
import java.util.List;

public interface IRepository
{
    //PrgState getCurrentProgram();
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> toAdd);

    void logProgramStateExecution(PrintWriter logFile, PrgState program) throws SuperCoolException;}
