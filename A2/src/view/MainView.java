package view;

import controller.IController;
import exception.StatementExecutionException;
import model.expression.BinaryOperatorExp;
import model.expression.ValueExp;
import model.expression.VarExp;
import model.state.*;
import model.statement.*;
import model.type.BooleanType;
import model.type.IntType;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.Value;
import repository.IRepository;

import java.util.Scanner;

public class MainView {
    public IRepository repo;
    public IController controller;

    public MainView(IRepository repo, IController controller) {
        this.repo = repo;
        this.controller = controller;
    }

    public void loadExample1(IController controller){
        IStmt ex1= new CompStmt(new VarDeclStmt( new IntType(),"v"),
                new CompStmt(new AssignStmt(new ValueExp(new IntValue(2)),"v"), new PrintStmt(new
                        VarExp("v"))));

        MyIStack<IStmt> exestack=new MyStack<>();
        MyIList<String> output=new MyList<>();
        MyIDictionary<String, Value> symTable=new MyDictionary<>();

        PrgState current= new PrgState(exestack,symTable,output, ex1);

        repo.add(current);
    }

    public void loadExample2(IController controller){
        IStmt ex2 = new CompStmt( new VarDeclStmt(new IntType(),"a"),
                new CompStmt(new VarDeclStmt(new IntType(),"b"),
                        new CompStmt(new AssignStmt( new BinaryOperatorExp(new ValueExp(new IntValue(2)),new
                                BinaryOperatorExp(new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)),"*"),"+"),"a"),
                                new CompStmt(new AssignStmt(new BinaryOperatorExp(new VarExp("a"), new ValueExp(new
                                        IntValue(1)),"+"),"b"), new PrintStmt(new VarExp("b"))))));
        MyIStack<IStmt> exestack=new MyStack<>();
        MyIList<String> output=new MyList<>();
        MyIDictionary<String, Value> symTable=new MyDictionary<>();
        PrgState current= new PrgState(exestack,symTable,output, ex2);

        repo.add(current);
    }

    public void loadExample3(IController controller){
        IStmt ex3 = new CompStmt(new VarDeclStmt(new BooleanType(),"a"),
                new CompStmt(new VarDeclStmt(new IntType(),"v"),
                        new CompStmt(new AssignStmt( new ValueExp(new BoolValue(true)),"a"),
                                new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt(new ValueExp(new
                                        IntValue(2)),"v"), new AssignStmt( new ValueExp(new IntValue(3)),"v")), new PrintStmt(new
                                        VarExp("v"))))));
        MyIStack<IStmt> exestack=new MyStack<>();
        MyIList<String> output=new MyList<>();
        MyIDictionary<String, Value> symTable=new MyDictionary<>();
        PrgState current= new PrgState(exestack,symTable,output, ex3);

        repo.add(current);
    }

    public void loadExample4(IController controller){
        IStmt varA = new VarDeclStmt(new IntType(), "a");
        IStmt assignA = new AssignStmt(new ValueExp(new IntValue(2)), "a");
        IStmt printA = new PrintStmt(new VarExp("a"));
        IStmt varB = new VarDeclStmt(new IntType(), "b");

        // Sequence: (int a; a=2); print(a)
        IStmt part1 = new CompStmt(varA, assignA);
        IStmt part2 = new CompStmt(part1, printA);

        // Final statement: (part2); int b
        IStmt ex4 = new CompStmt(part2, varB);

        MyIStack<IStmt> exestack=new MyStack<>();
        MyIList<String> output=new MyList<>();
        MyIDictionary<String, Value> symTable=new MyDictionary<>();

        PrgState current= new PrgState(exestack,symTable,output, ex4);
        repo.add(current);
    }
    public void menu(){
        System.out.println("Menu");
        System.out.println("1. Example 1");
        System.out.println("2. Example 2");
        System.out.println("3. Example 3");
        System.out.println("4. Execute all");
        System.out.println("5. Execute one step");
    }

    public void executeProgram(IController controller) throws StatementExecutionException {
        controller.allStep();
    }

    public void oneStep(IController controller, PrgState state) throws StatementExecutionException {
        controller.oneStep(state);
    }
    public void run(){
        menu();
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("Enter your choice: ");
            int option=scanner.nextInt();
            switch (option){
                case 1: {
                    loadExample1(controller);
                    break;
                }
                case 2: {
                    loadExample2(controller);
                    break;
                }
                case 3:{
                    loadExample3(controller);
                    break;
                }
                case 4:{
                    executeProgram(controller);
                    break;
                }
                case 5: {
                    PrgState state=repo.getCurrProg();
                    oneStep(controller,state);
                    break;
                }
                default: {
                System.out.println("Wrong choice");
                return;}
            }
            }
    }
}
