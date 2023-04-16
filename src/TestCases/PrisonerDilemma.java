package TestCases;

import java.io.Serializable;
import mvc.*;
import simstation.*;
import java.text.DecimalFormat;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

abstract class AbstractStrategy implements Serializable {
    protected int strategyCode; // code used to identify and compare the different strategy objects

    abstract boolean applyStrategy(Boolean... previousActions);

    abstract boolean areStrategiesEqual(AbstractStrategy other);
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

class RandomCooperation extends AbstractStrategy {
    public RandomCooperation() {
        strategyCode = 3;
    }

    public boolean applyStrategy(Boolean... previousActions) {
        Random rng = new Random(); // Create a random object
        return rng.nextBoolean(); // return a random boolean
    }

    public boolean areStrategiesEqual(AbstractStrategy other) {
        return this.strategyCode == other.strategyCode;
    }
}

class CooperateOnPreviousCooperation extends AbstractStrategy {
    public CooperateOnPreviousCooperation() {
        strategyCode = 4;
    }

    public boolean applyStrategy(Boolean... previousActions) {
        return previousActions[0];
    }

    public boolean areStrategiesEqual(AbstractStrategy other) {
        return this.strategyCode == other.strategyCode;
    }
}

class CooperateAlways extends AbstractStrategy {
    public CooperateAlways() {
        strategyCode = 2;
    }

    public boolean applyStrategy(Boolean... previousActions) {
        return true;
    }

    public boolean areStrategiesEqual(AbstractStrategy other) {
        return this.strategyCode == other.strategyCode;
    }
}

class CheatAlways extends AbstractStrategy {
    public CheatAlways() {
        strategyCode = 1;
    }

    public boolean applyStrategy(Boolean... previousActions) {
        return false;
    }

    public boolean areStrategiesEqual(AbstractStrategy other) {
        return this.strategyCode == other.strategyCode;
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
    public static AbstractStrategy[] strategies = new AbstractStrategy[] {
            new CheatAlways(),
            new CooperateAlways(),
            new RandomCooperation(),
            new CooperateOnPreviousCooperation()
    };
    private int fitness;
    private boolean previousCooperation;
    private AbstractStrategy strategy;

    public Prisoner() {
        fitness = 0;
        strategy = strategies[Utilities.rng.nextInt(NUMBER_OF_STRATEGIES)];
        previousCooperation = true;
    }

    public void update() {
        PrisonerDilemma prison = (PrisonerDilemma) world;
        Prisoner player2 = (Prisoner) prison.getNeighbor(this, 10);
        if (player2 != null) {
            boolean cooperation1 = this.cooperate();
            boolean cooperation2 = player2.cooperate();

            if (cooperation1 && cooperation2) {
                this.fitness += 3;
                player2.fitness += 3;
            } else if (cooperation1 && !cooperation2) {
                player2.fitness += 5;
            } else if (!cooperation1 && cooperation2) {
                this.fitness += 5;
            } else {
                this.fitness += 1;
                player2.fitness += 1;
            }
            previousCooperation = player2.cooperate();
        }
        heading = Heading.values()[Utilities.rng.nextInt(4)];
        move(Utilities.rng.nextInt(10));
    }

    private boolean cooperate() {
        return strategy.applyStrategy(previousCooperation);
    }

    public AbstractStrategy getStrategy() {
        return strategy;
    }

    @Override
    public int getSpeed() {
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

    // used to create a new stats panel for Prisoner stats
    public void stats() {
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, "#agents = " + NUM_OF_AGENTS
                + "\nclock = " + clock + "\n" + getStats());
    }

    private String getStats() {
        String statsString = "";
        double[] totalFitness = new double[Prisoner.NUMBER_OF_STRATEGIES];
        int[] strategyCounts = new int[Prisoner.NUMBER_OF_STRATEGIES];

        for (Agent a : agentList) {
            Prisoner prisoner = (Prisoner) a;
            for (int i = 0; i < Prisoner.NUMBER_OF_STRATEGIES; i++) {
                if (prisoner.getStrategy().areStrategiesEqual(Prisoner.strategies[i])) {
                    strategyCounts[i]++;
                    totalFitness[i] += prisoner.getFitness();
                    break;
                }
            }
        }

        for (int i = 0; i < Prisoner.NUMBER_OF_STRATEGIES; i++) {
            totalFitness[i] /= strategyCounts[i];
        }

        statsString = "Always Cheat Average Fitness: " + String.format("%.2f", totalFitness[0]) +
                "\nAlways Cooperate Average Fitness: " + String.format("%.2f", totalFitness[1]) +
                "\nRandomly Cooperate Average Fitness: " + String.format("%.2f", totalFitness[2]) +
                "\nPrevious Opponent Strategy Average: " + String.format("%.2f", totalFitness[3]);
        return statsString;
    }

    public static void main(String[] args) {
        AppPanel panel = new SimStationPanel(new PrisonerFactory());
        panel.display();
    }
}
