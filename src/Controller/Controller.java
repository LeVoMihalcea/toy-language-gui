package Controller;

import Model.Exceptions.SuperCoolException;
import Model.PrgState;
import Model.Values.RefValue;
import Model.Values.Value;
import Repository.IRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javax.swing.text.html.ListView;
import java.io.PrintWriter;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static Model.PrgState.freeSpots;

public class Controller  {
    @Override
    public String toString() {
        return repo.getPrgList().get(0).toString();
    }

    private IRepository repo;
    private PrintWriter logFile;
    private ExecutorService executor;

    public IRepository getRepo() {
        return repo;
    }

    public void setRepo(IRepository repo) {
        this.repo = repo;
    }

    public Controller(IRepository repo) {
        this.repo = repo;
    }

    private void collectGarbage(PrgState program) {
        List<Integer> addrFromHeap = getAddrsFromHeap(program.getHeap().values());
        Map<Integer, Value> newHeap = unsafeGarbageCollector(
                getAddrFromSymTable(program.getSymbolTable().values()),
                addrFromHeap,
                program.getHeap());
        program.setHeap(newHeap);
    }

    private Map<Integer,Value> unsafeGarbageCollector(List<Integer> symTableAddr, List<Integer> heapAddr, Map<Integer, Value> heap){
        Map<Integer, Value> toReturn = heap.entrySet().stream()
                .filter(e -> (symTableAddr.contains(e.getKey()) || heapAddr.contains(e.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        for(Integer thing : heap.keySet()){
            if(toReturn.get(thing) == null)
                freeSpots.add(thing);
        }
        return toReturn;
    }
    private List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){
        return symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    private List<Integer> getAddrsFromHeap(Collection<Value> heapValue){
        return heapValue.stream()
                .filter(v -> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    private Map<Integer, Value> garbageCollector(List<Integer> symTableAddr, List<Integer> heapAddr, Map<Integer,Value> heap){
        return heap.entrySet().stream().filter(e->(symTableAddr.contains(e.getKey()) || heapAddr.contains(e.getKey()))).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    private void conservativeGarbageCollector(List<PrgState> prgList){
        List<List<Integer>> listOfTables = new ArrayList<>();
        for(PrgState p : prgList){
            listOfTables.add(getAddrFromSymTable(p.getSymbolTable().values()));
        }

        List<Integer> fullTable = listOfTables.stream().flatMap(List::stream).distinct().collect(Collectors.toList());

        HashMap<Integer, Value> heap = (HashMap<Integer, Value>) prgList.get(0).getHeap();
        Collection<Integer> heapAddr = getAddrsFromHeap(heap.values());
        Map<Integer, Value> collections = prgList.get(0).getHeap().entrySet().stream().filter(e -> (fullTable.contains(e.getKey()) || heapAddr.contains(e.getKey()))).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        prgList.get(0).getHeap().clear();
        prgList.get(0).getHeap().putAll(collections);
    }

    public void oneStepForAllPrg(List<PrgState> prgList, ExecutorService executor){
        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(p::oneStep))
                .collect(Collectors.toList());
        try {
            List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (SuperCoolException | InterruptedException | ExecutionException sce) {
                            System.out.println(sce.getMessage());
                            throw new SuperCoolException("Something went wrong during future.get..");
                        }
                    }).filter(Objects::nonNull).collect(Collectors.toList());
            prgList.addAll(newPrgList);

            if(logFile != null)
                prgList.forEach(prg -> repo.logProgramStateExecution(logFile, prg));

            repo.setPrgList(prgList);
        }
        catch(InterruptedException ie){
            throw new SuperCoolException(ie.getMessage());
        }

    }

    public void allStep(){
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList=removeCompletedPrg(repo.getPrgList());
        while(prgList.size() > 0){
            oneStepForAllPrg(prgList, executor);
            conservativeGarbageCollector(prgList);
//            prgList.forEach(System.out::println);
            prgList=removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdownNow();
        repo.setPrgList(prgList);
    }

    private List<PrgState> removeCompletedPrg(List<PrgState> prgList) {
        return prgList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }

    private boolean done(List<PrgState> prgList) {
        for(PrgState program : prgList)
            if(!program.getExecutionStack().empty())
                return false;
        return true;
    }

    private void clearEverything() {

        while(!freeSpots.isEmpty()){
            freeSpots.poll();
        }
        for(int i = 1; i<=16; i++){
            freeSpots.add(i);
        }
    }

    public void setPrgList(List<PrgState> prgList){
        this.repo.setPrgList(prgList);
    }

    public List<PrgState> getPrgList(){
        return this.repo.getPrgList();
    }

    public PrintWriter getLogFile() {
        return logFile;
    }

    public void setLogFile(PrintWriter logFile) {
        this.logFile = logFile;
    }


}
