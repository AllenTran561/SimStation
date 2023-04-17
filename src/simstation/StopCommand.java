package simstation;

import mvc.Command;
import mvc.Model;

/* Class "StopCommand" Datalog
4/9/2023 - Minh Bui: Created file and implemented methods
*/

public class StopCommand extends Command {
    public StopCommand(Model model) {
        super(model);
    }

    @Override
    public void execute() {
        Simulation simulation = (Simulation) model;
        simulation.stop();
    }
}