package simstation;

import mvc.Command;
import mvc.Model;

/* Class "StartCommand" Datalog
4/9/2023 - Minh Bui: Created file and implemented methods
*/

public class StartCommand extends Command {
    public StartCommand(Model model) {
        super(model);
    }

    @Override
    public void execute() {
        Simulation sim = (Simulation) model;
        sim.start();
    }
}