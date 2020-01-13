package Model;

import Model.Exceptions.SuperCoolException;
import Model.Statements.IStatement;
import Model.Values.Value;

import java.io.BufferedReader;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

public class PrgState {
    private Stack<IStatement> executionStack;
    private String code;
    private Map<String, Value> symbolTable;
    private List<Value> output;
    private Map<String, BufferedReader> fileTable;
    private Map<Integer, Value> heap;
    public static Queue<Integer> freeSpots = new LinkedList<>();
    static {
        for(int i = 1; i<=16; i++){
            freeSpots.add(i);
        }
    }
    private int id;
    private static int idCounter;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static int getIdCounter() {
        return idCounter;
    }

    public static void setIdCounter(int idCounter) {
        PrgState.idCounter = idCounter;
    }

    public boolean isNotCompleted(){
        return !executionStack.empty();
    }

    public PrgState(Stack<IStatement> executionStack, Map<String, Value> symbolTable, List<Value> output,
                    Map<Integer, Value> heap ,Map<String, BufferedReader> fileTable, IStatement program) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.fileTable = fileTable;
        this.output = output;
        this.heap = heap;
        this.id = PrgState.idCounter;
        PrgState.idCounter++;
        executionStack.push(program);
    }

    public PrgState(Stack<IStatement> executionStack, Map<String, Value> symbolTable, List<Value> output,
                    Map<Integer, Value> heap, IStatement program) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.fileTable = new HashMap<>();
        this.output = output;
        this.heap = heap;
        this.id = PrgState.idCounter;
        PrgState.idCounter++;
        executionStack.push(program);
    }

    public Map<String, Value> getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(Map<String, Value> symbolTable) {
        this.symbolTable = symbolTable;
    }

    public Stack<IStatement> getExecutionStack() {
        return executionStack;
    }

    public void setExecutionStack(Stack<IStatement> executionStack) {
        this.executionStack = executionStack;
    }

    public List<Value> getOutput() {
        return output;
    }

    public void setOutput(List<Value> output) {
        this.output = output;
    }

    public Map<String, BufferedReader> getFileTable() {
        return fileTable;
    }

    public void setFileTable(Map<String, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public Map<Integer, Value> getHeap() {
        return heap;
    }

    public void setHeap(Map<Integer, Value> heap) {
        this.heap = heap;
    }

    @Override
    public String toString() {
        StringBuilder stackString = new StringBuilder();
        for(IStatement iStatement : executionStack){
            stackString.append(iStatement).append(';');
        }
        StringBuilder symbolTableString = new StringBuilder();
        Set<String> strings = symbolTable.keySet();
        for(String current : strings){
            Value value = symbolTable.get(current);
            symbolTableString.append(value.getType()).append(" ").append(current).append(" = ").append(value).append(";");
        }
//        return  "" + id + " - " +
//                "{" + stackString + "} - " +
//                "{" + symbolTableString + "} - " +
//                "{" + output + "} - " +
//                "{" + fileTable + "}" +
//                "{" + heap + "}";
        return "" + id + " - " + code;
    }

    public PrgState oneStep(){
        if(executionStack.isEmpty()) throw new SuperCoolException("prgstate stack is empty");
        IStatement currentStatement = executionStack.pop();
        PrgState execute = currentStatement.execute(this);
        return execute;
    }

    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList){
        return inPrgList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }
}
