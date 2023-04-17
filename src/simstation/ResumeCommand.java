package simstation;

import mvc.Command;
import mvc.Model;

/* Class "ResumeCommand" Datalog
4/9/2023 - Minh Bui: Created file and implemented methods
*/

public class ResumeCommand extends Command {
    public ResumeCommand(Model model) {
        super(model);
    }

    @Override
    public void execute() {
        Simulation simulation = (Simulation) model;
        simulation.resume();
    }
}