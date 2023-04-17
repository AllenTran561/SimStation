package simstation;

import mvc.Command;
import mvc.Model;

/* Class "StatsCommand" Datalog
4/9/2023 - Minh Bui: Created file and implemented methods
*/

public class StatsCommand extends Command {

    public StatsCommand(Model model) {
        super(model);
    }

    @Override
    public void execute() {
        Simulation simulation = (Simulation) model;
        simulation.stats();
    }
}