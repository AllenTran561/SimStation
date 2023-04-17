package TestCases;

import mvc.*;
import simstation.*;

import javax.swing.*;
import java.awt.*;

/* Test Case "RandomWalk" Datalog
4/9/2023 - Niko Jokhadze: Created file
4/9/2023 - Allen Tran: Edited file
4/12/2023 - Allen Tran: Finished implementing methods
4/16/2023 - Niko Jokhadze: Made sure stats, color, and help functions were all functional
*/

class Drunk extends Agent {

    public Drunk() {
        super();
        heading = Heading.randomHeading();
        this.setAgentColor(Color.WHITE);
    }

    public void update() {
        heading = Heading.randomHeading();
        int steps = Utilities.rng.nextInt(10) + 1;
        move(steps);
    }

    @Override
    public int getSpeed() {
        return 0; // unneeded
    }
}

class RandomWalkFactory extends SimStationFactory {
    public Model makeModel() {
        return new RandomWalkSimulation();
    }

    public String getTitle() {
        return "Random Walks";
    }

    @Override
    public String[] getHelp() {

        String[] cmmds = new String[3];
        cmmds[0] = "# Agents: # of dots";
        cmmds[1] = "Clock: Start timer when you start the simulation and pause when you press suspend";
        cmmds[2] = "Description: Agents move randomly with random speed.";

        return cmmds;
    }
}

public class RandomWalkSimulation extends Simulation {

    @Override
    public void populate() {
        for (int i = 0; i < 15; i++) {
            this.addAgent(new Drunk());
        }
    }

    public void stats() {
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, getStats());
    }

    private String getStats() {
        String statsString = "";
        statsString = "# of agents = " + agentList.size()+
                "\nClock = " + this.getClock();
        return statsString;
    }

    public static void main(String[] args) {
        AppPanel panel = new SimStationPanel(new RandomWalkFactory());
        panel.display();
    }
}

class RandomWalksStatsCommand extends Command {
    public RandomWalksStatsCommand(Model model) {
        super(model);
    }

    @Override
    public void execute() {
        Simulation walking = (Simulation) model;
        walking.stats();
    }
}