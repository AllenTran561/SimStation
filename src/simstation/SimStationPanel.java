package simstation;

import mvc.*;
import javax.swing.*;
import java.awt.*;

/* Class "SimStationPanel" Datalog
4/7/2023 - Allen Tran: Created file
4/9/2023 - Allen Tran and Minh Bui: Implemented all methods
*/

public class SimStationPanel extends AppPanel {

    public SimStationPanel(SimStationFactory factory) {

        super(factory);
        controlPanel.setLayout(new GridLayout(0, 1));

        controlPanel.setBackground(Color.LIGHT_GRAY);
        view.setBackground(Color.LIGHT_GRAY);

        JButton start = new JButton("Start");
        start.addActionListener(this);
        controlPanel.add(start, CENTER_ALIGNMENT);

        JButton suspend = new JButton("Suspend");
        suspend.addActionListener(this);
        controlPanel.add(suspend, CENTER_ALIGNMENT);

        JButton resume = new JButton("Resume");
        resume.addActionListener(this);
        controlPanel.add(resume, CENTER_ALIGNMENT);

        JButton stop = new JButton("Stop");
        stop.addActionListener(this);
        controlPanel.add(stop, CENTER_ALIGNMENT);

        JButton stats = new JButton("Stats");
        stats.addActionListener(this);
        controlPanel.add(stats, CENTER_ALIGNMENT);
    }

    public static void main(String[] args) {
        AppFactory factory = new SimStationFactory();
        AppPanel panel = new SimStationPanel((SimStationFactory) factory);
        panel.display();
    }
}