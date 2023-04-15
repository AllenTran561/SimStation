package TestCases;

import mvc.*;
import simstation.*;

class Drunk extends Agent {

    public Drunk() {
        super();
        heading = Heading.randomHeading();
    }

    public void update() {
        heading = Heading.randomHeading();
        int steps = Utilities.rng.nextInt(10) + 1;
        move(steps);
    }

    @Override
    public int getSpeed() {
        return 0; // unfinished
    }
}

class RandomWalkFactory extends SimStationFactory {
    public RandomWalkFactory() {
        setSim(new RandomWalkSimulation());
    }

    @Override
    public Model makeModel() {
        return getSim();
    }

    @Override
    public String getTitle() {
        return "Random Walks (Drunks) Simulation";
    }

    @Override
    public String[] getHelp() {
        // put something later
        String[] cmmds = new String[3];
        cmmds[0] = "#agents: # of dots";
        cmmds[1] = "clock: start timer when you start the simulation and pause when you press suspend";
        cmmds[2] = "Agents move randomly with random speed";

        return cmmds;
    }
}

public class RandomWalkSimulation extends Simulation {

    public void populate() {
        for (int i = 0; i < 15; i++) {
            this.addAgent(new Drunk());
        }
    }

    public static void main(String[] args) {
        AppPanel panel = new SimStationPanel(new RandomWalkFactory());
        panel.display();
    }
}
