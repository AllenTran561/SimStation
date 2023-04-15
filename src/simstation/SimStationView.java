
package simstation;

import mvc.*;
import simstation.Agent;
import java.awt.*;
import java.util.List;

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
            gc.setColor(Color.RED);
            gc.fillOval(a.getX_Pos(), a.getY_Pos(), sim.getDotSize(), sim.getDotSize());
        }
    }
}