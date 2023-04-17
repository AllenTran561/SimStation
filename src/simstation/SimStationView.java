package simstation;

import mvc.*;
import java.awt.*;
import java.util.List;

/* Class "SimStationView" Datalog
4/7/2023 - Allen Tran: Created file
4/9/2023 - Allen Tran and Minh Bui: Edited file and implemented all methods
4/14/2023 - Allen Tran and Minh Bui: Fixed troublesome methods
4/16/2023 - Niko Jokhadze: Changed gc.setColor to allow for custom coloring of agents in all test cases
*/

public class SimStationView extends View {
    public SimStationView(Simulation sim) {
        super(sim);

    }

    @Override
    public void paintComponent(Graphics gc) {
        super.paintComponent(gc);
        int height = this.getHeight();
        int width = this.getWidth();
        Simulation sim = (Simulation) model;
        Graphics2D g2d = (Graphics2D) gc;
        List<Agent> agents = sim.getAgentList();
        g2d.setColor(new Color(50, 50, 50));
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(new Color(50, 200, 100));
        for (Agent a : agents) {
            gc.setColor(a.agentColor);
            gc.fillOval(a.getX_Pos(), a.getY_Pos(), sim.getDotSize(), sim.getDotSize());
        }
    }
}