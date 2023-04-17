package TestCases;

import java.awt.*;
import java.io.Serializable;
import mvc.*;
import simstation.*;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/* Test Case "PrisonerDilemma" Datalog
4/9/2023 - Niko Jokhadze: Created file
4/15/2023 - Minh Bui: Implemented all methods
4/16/2023 - Minh Bui: Revised and finalized all methods
4/16/2023 - Niko Jokhadze: Made sure stats, color, and help functions were all functional
*/

abstract class AbstractStrategy implements Serializable {
    protected int strategyCode; // code used to identify and compare the different strategy objects

    abstract boolean applyStrategy(Boolean... previousActions);

    abstract boolean areStrategiesEqual(AbstractStrategy other);
}

class PrisonerStatsCommand extends Command {
    public PrisonerStatsCommand(Model model) {
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
        return "Prisoner's Dilemma";
    }

    @Override
    public String[] getHelp() {
        String[] cmmds = new String[6];
        cmmds[0] = "# Agents: # of dots";
        cmmds[1] = "Clock: Start timer when you start the simulation and pause when you press suspend";
        cmmds[2] = "Cheat vs cooperate: Fitness of cheat is incremented by 5 points";
        cmmds[3] = "Cheat vs cheat: Fitness is incremented by 1 points for both";
        cmmds[4] = "Cooperate vs cooperate: Fitness is incremented by 3 points for both";
        cmmds[5] = "Description: Prisoner agents randomly interact with one another and decide if" +
                "\n they should cooperate or cheat with each other, using one of the 4 strategies listed" +
                "\n above. Score is based on successful cooperation/cheating among the different strategies.";

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
        this.setAgentColor(Color.WHITE);
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
        JOptionPane.showMessageDialog(frame, "# Agents = " + NUM_OF_AGENTS
                + "\nClock = " + clock + "\n" + getStats());
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