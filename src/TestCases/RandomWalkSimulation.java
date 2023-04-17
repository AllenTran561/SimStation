package TestCases;

import mvc.*;
import simstation.*;
import java.awt.*;

/* Test Case "RandomWalk" Datalog
4/9/2023 - Niko Jokhadze: Created file
4/9/2023 - Allen Tran: Edited file
4/12/2023 - Allen Tran: Finished implementing methods
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
        return 0; // unfinished
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