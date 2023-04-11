
package simstation;

import mvc.Command;
import mvc.Model;

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
