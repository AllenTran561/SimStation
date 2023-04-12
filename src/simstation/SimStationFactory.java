
package simstation;

import mvc.Command;
import mvc.Model;
import mvc.View;
import java.io.Serializable;

public class SimStationFactory implements SimFactory, Serializable {
    private SimStation sim = new SimStation();

    @Override
    public Model makeModel() {
        return sim;
    }

    @Override
    public View makeView(Model m) {
        return null;
    }

    @Override
    public String[] getEditCommands() {
        return new String[] { "Start", "Suspend", "Resume", "Stop", "Stats" };
    }

    @Override
    public Command makeEditCommand(Model model, String type, Object object) {
        if (type == "Start")
            return new StartCommand(model);
        else if (type == "Stop")
            return new StopCommand(model);
        else if (type == "Suspend")
            return new SuspendCommand(model);
        else if (type == "Resume")
            return new ResumeCommand(model);
        else if (type == "Stats")
            return new StatsCommand(model);
        return null;
    }

    public View getView(Model m) {
        return new SimStationView(sim);
    }

    public SimStation getSim() {
        return this.sim;
    }

    public void setSim(SimStation sim) {
        this.sim = sim;
    }

    @Override
    public String getTitle() {
        return "simulation";
    }

    @Override
    public String[] getHelp() {
        // put something later
        String[] cmmds = new String[5];
        cmmds[0] = "Start: start the simulation";
        cmmds[1] = "Suspend: pause the simulation";
        cmmds[2] = "Resume: resume the simulation";
        cmmds[3] = "Stop: stop the simulation";
        cmmds[4] = "Stats: check #of agents and clock";
        return cmmds;
    }

    @Override
    public String about() {
        return "Sim station version 1.0. copyright 2023 by ";
    }
}
