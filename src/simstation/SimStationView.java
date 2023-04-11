
package simstation;

import mvc.Model;
import mvc.View;

import java.awt.*;
import java.util.Iterator;

public class SimStationView extends View {

    public SimStationView(Model model) {
        super(model);
    }

    public void paintComponent(Graphics gc) {
        Simulation sim = (Simulation) model;

        for (Agent a : sim.getAgentList()) {
            gc.setColor(Color.RED);
            gc.fillOval(a.getX_Pos(), a.getY_Pos(), sim.DOT_SIZE, sim.DOT_SIZE);
        }
    }
}
