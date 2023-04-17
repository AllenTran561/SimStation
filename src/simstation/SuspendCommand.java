package simstation;

import mvc.Command;
import mvc.Model;

/* Class "SuspendCommand" Datalog
4/9/2023 - Minh Bui: Created file and implemented methods
*/

public class SuspendCommand extends Command {
    public SuspendCommand(Model model) {
        super(model);
    }

    @Override
    public void execute() {
        Simulation simulation = (Simulation) model;
        simulation.suspend();
    }
}