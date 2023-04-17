package TestCases;

import mvc.*;
import static mvc.Utilities.rng;
import simstation.*;
import java.awt.*;

/* Test Case "Plague" Datalog
4/9/2023 - Niko Jokhadze: Created file
4/9/2023 - Niko Jokhadze: Edited file
4/16/2023 - Niko Jokhadze: Finished implementing methods
*/

class Plague extends Agent {

    public int chanceTime;
    public int resistance;
    private boolean infected;

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


class PlagueFactory extends SimStationFactory {
    public Model makeModel() { return new PlagueSimulation(); }
    public String getTitle() { return "Plague";}
    public String about() {
        return "";
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
        for(Agent a : agentList) {
            Plague p = (Plague) a;
            if(p.isInfected()){
                infected++;
            }
        }

        return (infected/(int)agentList.size()) * 100;
    }

    public String[] getStats(){
        String[] stats = new String[3];
        stats[0] = "#agents = " + agentList.size();
        stats[1] = "clock = " + this.getClock();
        stats[2] = "% infected = " + this.getInfectedPercent();
        return stats;
    }

    public static void main(String[] args) {
        AppPanel panel = new SimStationPanel(new PlagueFactory());
        panel.display();
    }
}