package controller;

import exception.StatementExecutionException;
import model.state.PrgState;

import java.util.EmptyStackException;
import java.util.List;

public interface IController {
    void allStep();
    void oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException;
    List<PrgState> removeCompletedPrg(List<PrgState> inPrgList);
}
