package TestCases;

import mvc.*;
import simstation.*;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/* Test Case "Flocking" Datalog
4/9/2023 - Niko Jokhadze: Created file
4/9/2023 - Niko Jokhadze: Edited file
4/12/2023 - Niko Jokhadze: Finished implementing methods
4/16/2023 - Niko Jokhadze: Made sure stats, color, and help functions were all functional
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

class FlockingStatsCommand extends Command {
    public FlockingStatsCommand(Model model) {
        super(model);
    }

    @Override
    public void execute() {
        Simulation flocking = (Simulation) model;
        flocking.stats();
    }
}

class FlockingFactory extends SimStationFactory {
    public Model makeModel() {
        return new FlockingSimulation();
    }

    public String getTitle() {
        return "Flocking";
    }

    @Override
    public String[] getHelp() {

        String[] cmmds = new String[3];
        cmmds[0] = "# Agents: # of dots that represent birds";
        cmmds[1] = "Clock: start timer when you start the simulation and pause when you press suspend";
        cmmds[2] = "Description: Bird agents move at random speeds and random form flocks upon " +
                "\n close proximity until all agents fly in the same direction and speed.";

        return cmmds;
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

    public void stats() {
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, getStats());
    }

    private String getStats() {
        String statsString = "";
        int[] speedCount = new int[] {0, 0, 0, 0, 0};
        for (Agent a : agentList) {
            Bird b = (Bird) a;
            speedCount[b.speed - 1]++;
        }

        statsString = "# of birds at speed 1 = " + speedCount[0]+
                "\n# of birds at speed 2 = " + speedCount[1] +
                "\n# of birds at speed 3 = " + speedCount[2] +
                "\n# of birds at speed 4 = " + speedCount[3] +
                "\n# of birds at speed 5 = " + speedCount[4];
        return statsString;
    }

    public static void main(String[] args) {
        AppPanel panel = new SimStationPanel(new FlockingFactory());
        panel.display();
    }
}