import controller.Controller;
import model.state.MyList;
import model.state.PrgState;
import repository.MyRepository;
import view.MainView;

void main() {
    List<PrgState> states=new ArrayList<>();
    MyRepository repo=new MyRepository(states,"");
    Controller controller=new Controller(repo);
    MainView mainView=new MainView(repo,controller);
    mainView.run();
}