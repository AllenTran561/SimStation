package TestCases;

import mvc.*;
import static mvc.Utilities.rng;
import simstation.*;

import javax.swing.*;
import java.awt.*;

/* Test Case "Plague" Datalog
4/9/2023 - Niko Jokhadze: Created file
4/9/2023 - Niko Jokhadze: Edited file
4/16/2023 - Niko Jokhadze: Finished implementing methods
4/16/2023 - Niko Jokhadze: Made sure stats, color, and help functions were all functional
*/

class Plague extends Agent {
    public int chanceTime;
    public int resistance;
    private boolean infected;
    public int infectedAmount;

    public Plague() { // per-agent plague information
        super();
        chanceTime = rng.nextInt(100);
        if (chanceTime < PlagueSimulation.VIRULENCE){
            this.setAgentColor(Color.RED);
            infected = true;
        } else {
            this.setAgentColor(Color.GREEN);
            infected = false;
        }
        resistance = rng.nextInt(PlagueSimulation.RESISTANCE);
        heading = Heading.randomHeading();
    }

    public synchronized boolean isInfected(){
        return infected;
    }

    public void setInfected(boolean infected){
        this.infected = infected;
        this.setAgentColor(Color.RED);
    }

    public void update() {
        Plague p = (Plague)world.getNeighbor(this, 10);
        if(this.isInfected()){
            if(p != null && !p.isInfected()){
                if(resistance < rng.nextInt(100)){
                    p.setInfected(true);
                    infectedAmount++;
                }
            }
        }
        move(rng.nextInt(PlagueSimulation.AGENT_SPEED));
    }

    @Override
    public int getSpeed() {
        // not necessary for this simulation
        return 0;
    }
}

class PlagueStatsCommand extends Command {
    public PlagueStatsCommand(Model model) {
        super(model);
    }

    @Override
    public void execute() {
        Simulation plague = (Simulation) model;
        plague.stats();
    }
}

class PlagueFactory extends SimStationFactory {
    public Model makeModel() { return new PlagueSimulation(); }

    public String getTitle() { return "Plague";}

    public String[] getHelp() {

        String[] cmmds = new String[3];
        cmmds[0] = "# Agents: # of dots";
        cmmds[1] = "Clock: start timer when you start the simulation and pause when you press suspend";
        cmmds[2] = "Description: Agents randomly walk around with a small percent of them starting off infected." +
                "\n Agents traveling in close proximity to infected agents with a small chance of not becoming" +
                "\n infected, though the simulation will end with all agents being infected.";

        return cmmds;
    }
}

public class PlagueSimulation extends Simulation {

    public static int VIRULENCE = 10; // % chance of initial infection
    public static int RESISTANCE = 20; // % chance of resisting infection
    public static int POPULATION = 30; // # of agents on the field
    public static int AGENT_SPEED = 5; // speed of agents

    public void populate() {
        for(int i = 0; i < POPULATION; i++){
            Plague newPlague = new Plague();
            addAgent(newPlague);
        }
    }

    public double getInfectedPercent(){
        int infected = 0;
        for(int i = 0; i < getAgentList().size(); i++) {
            Plague p = (Plague) getAgentList().get(i);
            if(p.isInfected()){
                infected++;
            }
        }

        return (infected/(double) getAgentList().size()) * 100;
    }

    public void stats() {
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, getStats());
    }

    private String getStats() {
        String statsString = "";
        statsString = "# of agents = " + agentList.size()+
                "\nClock = " + this.getClock() +
                "\n% of agents infected = " + this.getInfectedPercent();
        return statsString;
    }

    public static void main(String[] args) {
        AppPanel panel = new SimStationPanel(new PlagueFactory());
        panel.display();
    }
}