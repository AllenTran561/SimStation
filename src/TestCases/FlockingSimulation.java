package TestCases;

import mvc.*;
import simstation.*;
import java.awt.*;
import java.util.Random;

/* Test Case "Flocking" Datalog
4/9/2023 - Niko Jokhadze: Created file
4/9/2023 - Niko Jokhadze: Edited file
4/12/2023 - Niko Jokhadze: Finished implementing methods
*/

class Bird extends Agent {
    public int speed;

    public int getSpeed() {
        return speed;
    }

    public Bird() {
        speed = Utilities.rng.nextInt(5) + 1;
        heading = Heading.randomHeading();
        this.setAgentColor(Color.WHITE);
    }

    public void update() {
        Bird b = (Bird) world.getNeighbor(this, 10);
        if (b != null) {
            this.speed = b.speed;
            this.heading = b.heading;
            super.move(speed);
        } else {
            super.move(speed);
        }
    }
}

class FlockingFactory extends SimStationFactory {
    public Model makeModel() {
        return new FlockingSimulation();
    }

    public String getTitle() {
        return "Flocking";
    }

    public String about() {
        return "";
    }
}

public class FlockingSimulation extends Simulation {

    public void populate() {
        Random r = new Random();
        for (int i = 0; i < 50; i++) {
            int[] pos = new int[2];
            pos[0] = r.nextInt(WORLD_SIZE);
            pos[1] = r.nextInt(WORLD_SIZE);
            addAgent(new Bird());
        }
    }

    public String[] getStats() {
        int[] speedCount = new int[] {0, 0, 0, 0, 0};
        for (Agent a : agentList) {
            Bird b = (Bird) a;
            speedCount[b.speed - 1]++;
        }
        String[] stats = new String[5];
        stats[0] = "#birds @ speed 1 = " + speedCount[0];
        stats[1] = "#birds @ speed 2 = " + speedCount[1];
        stats[2] = "#birds @ speed 3 = " + speedCount[2];
        stats[3] = "#birds @ speed 4 = " + speedCount[3];
        stats[4] = "#birds @ speed 5 = " + speedCount[4];
        return stats;
    }

    public static void main(String[] args) {
        AppPanel panel = new SimStationPanel(new FlockingFactory());
        panel.display();
    }
}