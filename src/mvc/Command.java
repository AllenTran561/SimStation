package mvc;

abstract public class Command {
    public Model model;
    public abstract void execute() throws Exception;
    public Command(Model m){
        this.model = m;
    }
}
