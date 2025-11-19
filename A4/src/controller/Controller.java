package controller;

import exception.StatementExecutionException;
import model.state.MyIStack;
import model.state.PrgState;
import model.statement.IStmt;
import model.value.RefValue;
import model.value.Value;
import repository.IRepository;

import java.util.*;
import java.util.stream.Collectors;

public class Controller implements IController {
    private IRepository repository;
    private boolean flag=true;
    public Controller(IRepository repository){
        this.repository = repository;
    }

    public PrgState oneStep(PrgState state) throws EmptyStackException, StatementExecutionException{
        MyIStack<IStmt> stk=state.getExeStack();
        if(stk.isEmpty())
            throw new EmptyStackException();
        IStmt crtStmt=stk.pop();
        PrgState returnedState = crtStmt.execute(state);
        if(flag){
            repository.logPrgStateExec(returnedState);
        }
        return returnedState;
    }

    public void allStep(){
        PrgState prg;
        try {
            prg = repository.getCurrProg();
        } catch (NoSuchElementException e) {
            System.err.println(e.getMessage());
            return;
        }
        try{
            while(!prg.getExeStack().isEmpty()){
                try {
                    prg = oneStep(prg);
                    prg.getHeap().setContent(safeGarbageCollector(
                            getAddrFromSymTable(prg.getSymTable().getContent().values()),
                            prg.getHeap().getContent()));

                } catch (EmptyStackException e) {
                    System.out.println("Program finished execution successfully.");
                    break;
                } catch (StatementExecutionException e) {
                    throw e;
                } catch (Exception e) {
                    throw new StatementExecutionException("Unexpected runtime error during execution: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("An unexpected program error occurred: " + e.getMessage());
        }
    }
    Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap){
        List<Integer> liveAddresses = new ArrayList<>(symTableAddr);
        List<Integer> nestedAddresses = getAddrFromHeap(heap.entrySet().stream()
                        .filter(e -> symTableAddr.contains(e.getKey())) // Only look at values pointed to by the roots
                        .map(Map.Entry::getValue)
                        .collect(Collectors.toList())
        );
        liveAddresses.addAll(nestedAddresses);
        Set<Integer> uniqueLiveAddresses = new HashSet<>(liveAddresses);
        return heap.entrySet().stream()
                .filter(e -> uniqueLiveAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){
        return symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();})
                .collect(Collectors.toList());
    }
    public List<Integer> getAddrFromHeap(Collection<Value> heapValues) {
        return heapValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> ((RefValue) v).getAddress())
                .filter(a -> a != 0)
                .collect(Collectors.toList());
    }
    public void displayPrgState(){
        System.out.println(repository.getCurrProg().toString());
    }
}
