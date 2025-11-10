package model.state;

import model.statement.IStmt;
import model.value.Value;

public class PrgState {
    private final MyIStack<IStmt> exeStack;
    private final MyIDictionary<String, Value> symTable;
    private final MyIList<String> out;
    private final FileTable fileTable;

    public PrgState(MyIStack<IStmt> exeStack, MyIDictionary<String, Value> symTable, MyIList<String> out, IStmt program, FileTable fileTable) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;

        exeStack.push(program);
    }

    public PrgState(IStmt originalProgram) {
        MyIStack<IStmt> exeStack = new MyStack<>();
        MyIDictionary<String, Value> symTable = new MyDictionary<>();
        MyIList<String> output = new MyList<>();
        FileTable fileTable = new MapFileTable();
        this(exeStack, symTable, output, originalProgram, fileTable);
    }

    public boolean isNotCompleted() {
        return !exeStack.isEmpty();
    }

    @Override
    public String toString() {
        return "--------------------------------------------------------\n" +
                exeStack.toString() + "\n" +
                symTable.toString() + "\n" +
                out.toString() + "\n" +
                fileTable.toString() + "\n" +
                "--------------------------------------------------------";
    }

    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }

    public MyIList<String> getOut() {return out;}

    public MyIDictionary<String, Value> getSymTable() {
        return symTable;
    }

    public FileTable getFileTable() {return fileTable;}
}
