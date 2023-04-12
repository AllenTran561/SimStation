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
    public int getSpeed(){
        return 0;   //unfinished
    }
}

class RandomWalkFactory extends SimStationFactory {
    public Model makeModel() { return new RandomWalkSimulation(); }
    public String getTitle() { return "Random Walks";}
}

public class RandomWalkSimulation extends Simulation {

    public void populate() {
        for (int i = 0; i < 15; i++) {
            addAgent(new Drunk());
        }
    }

    public static void main(String[] args) {
        AppPanel panel = new SimStationPanel(new RandomWalkFactory());
        panel.display();
    }
}
