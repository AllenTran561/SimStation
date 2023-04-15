package TestCases;

import java.io.Serializable;
import mvc.*;
import simstation.*;
import java.text.DecimalFormat;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

abstract class Strategy implements Serializable {
    protected int toCompare; // used to compare the different extended strategy Objects

    abstract boolean executeStrategy(Boolean... previous);

    abstract boolean equals(Strategy that);
}

class StatsCommand extends Command {
    public StatsCommand(Model model) {
        super(model);
    }

    @Override
    public void execute() {
        Simulation prison = (Simulation) model;
        prison.stats();
    }
}

class RandomlyCooperate extends Strategy {
    public RandomlyCooperate() {
        toCompare = 3; // How to compare the object of strategy
    }

    public boolean executeStrategy(Boolean... booleans) {
        return Utilities.rng.nextBoolean(); // return a random boolean
    }

    public boolean equals(Strategy that) {
        if (this.toCompare == that.toCompare) {
            return true;
        }
        return false;
    }
}

class CooperateIfLastCooperated extends Strategy {
    public CooperateIfLastCooperated() {
        toCompare = 4; // How to compare the object of strategy
    }

    public boolean executeStrategy(Boolean... booleans) {
        return booleans[0]; // returns previous inmates cheat or cooperation
    }

    public boolean equals(Strategy that) {
        if (this.toCompare == that.toCompare) {
            return true;
        }
        return false;
    }
}

class AlwaysCooperate extends Strategy {
    public AlwaysCooperate() {
        toCompare = 2; // How to compare the object of strategy
    }

    public boolean executeStrategy(Boolean... booleans) {
        return true;
    }

    public boolean equals(Strategy that) {
        if (this.toCompare == that.toCompare) {
            return true;
        }
        return false;
    }
}

class AlwaysCheat extends Strategy {
    public AlwaysCheat() {
        toCompare = 1; // How to compare the object of strategy
    }

    public boolean executeStrategy(Boolean... booleans) {
        return false;
    }

    public boolean equals(Strategy that) {
        if (this.toCompare == that.toCompare) // compares two different strategies
        {
            return true;
        }
        return false;
    }
}

class PrisonerFactory extends SimStationFactory {
    public Model makeModel() {
        return new PrisonerDilemma();
    }

    public String getTitle() {
        return "prison";
    }

    @Override
    public String[] getHelp() {
        // put something later
        String[] cmmds = new String[5];
        cmmds[0] = "#agents: # of dots";
        cmmds[1] = "clock: start timer when you start the simulation and pause when you press suspend";
        cmmds[2] = "cheat vs cooperate: fitness of cheat is incremented by 5pts";
        cmmds[3] = "cheat vs cheat: fitness is incremented by 1pts for both";
        cmmds[4] = "cooperate vs cooperate: fitness is incremented by 3pts for both";

        return cmmds;
    }
}

class Prisoner extends Agent {

    public static int NUMBER_OF_STRATEGIES = 4;
    public static Strategy[] strategies = new Strategy[] { new AlwaysCheat(),
            new AlwaysCooperate(),
            new RandomlyCooperate(),
            new CooperateIfLastCooperated() };
    private int fitness;
    private boolean previousCooperate; // used for a Strategy object
    private Strategy strategy; // holds current strategy

    public Prisoner() {
        fitness = 0;
        strategy = strategies[Utilities.rng.nextInt(4)]; // randomize the strategy to use
        previousCooperate = true;
    }

    public void update() {
        PrisonerDilemma prison = (PrisonerDilemma) world;
        Prisoner player2 = (Prisoner) prison.getNeighbor(this, 10);
        if (player2 != null) {
            if (this.cooperate() == true && player2.cooperate() == true) // player1 and player 2 cooperate
            {
                this.fitness += 3;
                player2.fitness += 3;
            } else if (this.cooperate() == true && player2.cooperate() == false) // player1 cooperates and playeer2
                                                                                 // cheats
            {
                player2.fitness += 5;
            } else if (this.cooperate() == false && player2.cooperate() == true) // player1 cheats and player2 cooperate
            {
                this.fitness += 5;
            } else if (this.cooperate() == false && player2.cooperate() == false) // player1 and player 2 cheat
            {
                this.fitness += 1;
                player2.fitness += 1;
            }
            previousCooperate = player2.cooperate();
        }
        heading = Heading.values()[Utilities.rng.nextInt(4)]; // randomizes the next heading
        move(Utilities.rng.nextInt(10)); // randomizes the movement
    }

    private boolean cooperate() {
        return strategy.executeStrategy(previousCooperate); // used to get the Strategys boolean (cheat or cooperate)
    }

    public Strategy getStrategy() {
        return strategy;
    }

    @Override
    public int getSpeed() { // not sure if this method is required for prisoner
        // TODO Auto-generated method stub
        return 0;
    }

    public int getFitness() {
        return fitness;
    }
}

public class PrisonerDilemma extends Simulation {

    public void populate() {
        for (int i = 0; i < NUM_OF_AGENTS; i++) {
            this.addAgent(new Prisoner());
        }
    }

    public void stats() // used to create a new stats panel for Prisoner stats
    {
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, "#agents = " + NUM_OF_AGENTS
                + "\nclock = " + clock + "\n" + getStats());
    }

    private String getStats() // to get a String of the prisoner stats
    {
        String statsString = "";
        double average1 = 0, average2 = 0, average3 = 0, average4 = 0;
        double strategy1Prisoners = 0, strategy2Prisoners = 0, strategy3Prisoners = 0, strategy4Prisoners = 0;
        DecimalFormat df2 = new DecimalFormat("#.##"); // better formatting

        for (Agent a : agentList) {
            Prisoner prisoner = (Prisoner) a;
            if (prisoner.getStrategy().equals(Prisoner.strategies[0])) // uses equals method to compare the strategy
                                                                       // objects
            {
                strategy1Prisoners += 1;
                average1 += prisoner.getFitness();
            } else if (prisoner.getStrategy().equals(Prisoner.strategies[1])) {
                strategy2Prisoners += 1;
                average2 += prisoner.getFitness();
            } else if (prisoner.getStrategy().equals(Prisoner.strategies[2])) {
                strategy3Prisoners += 1;
                average3 += prisoner.getFitness();
            } else if (prisoner.getStrategy().equals(Prisoner.strategies[3])) {
                strategy4Prisoners += 1;
                average4 += prisoner.getFitness();
            }
        }

        average1 /= strategy1Prisoners;
        average2 /= strategy2Prisoners;
        average3 /= strategy3Prisoners;
        average4 /= strategy4Prisoners;

        statsString = "Always Cheat Average Fitness: " + df2.format(average1) +
                "\nAlways Cooperate Average Fitness: " + df2.format(average2) +
                "\nRandomly Cooperate Average Fitness: " + df2.format(average3) +
                "\nPrevious Opponent Strategy Average: " + df2.format(average4);
        return statsString;
    }

    public static void main(String[] args) {
        AppPanel panel = new SimStationPanel(new PrisonerFactory());
        panel.display();
    }
}
