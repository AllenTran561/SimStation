
package simstation;

import mvc.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class SimStationPanel extends AppPanel {
    private JButton start, stop, suspend, resume, stats;

    public SimStationPanel(SimStationFactory factory) {

        super(factory);

        // Preparing the simulationView, the canvas the agent walks around on
        SimStationView view = (SimStationView) factory.makeView(factory.makeModel());
        view.setPreferredSize(new Dimension(250, 250));

        // Preparing the panel to hold all the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(new LineBorder(Color.BLACK));
        buttonPanel.setPreferredSize(new Dimension(100, 250));

        // Making the buttons for all the commands
        start = new JButton("Start");
        start.addActionListener(this);
        JPanel startPanel = new JPanel();
        start.setPreferredSize(new Dimension(100, 30));
        startPanel.add(start);

        suspend = new JButton("Suspend");
        suspend.addActionListener(this);
        JPanel suspendPanel = new JPanel();
        suspend.setPreferredSize(new Dimension(100, 30));
        suspendPanel.add(suspend);

        resume = new JButton("Resume");
        resume.addActionListener(this);
        JPanel resumePanel = new JPanel();
        resume.setPreferredSize(new Dimension(100, 30));
        resumePanel.add(resume);

        stop = new JButton("Stop");
        stop.addActionListener(this);
        JPanel stopPanel = new JPanel();
        stop.setPreferredSize(new Dimension(100, 30));
        stopPanel.add(stop);

        stats = new JButton("Stats");
        stats.addActionListener(this);
        JPanel statsPanel = new JPanel();
        stats.setPreferredSize(new Dimension(100, 30));
        statsPanel.add(stats);

        buttonPanel.add(startPanel);
        buttonPanel.add(suspendPanel);
        buttonPanel.add(resumePanel);
        buttonPanel.add(stopPanel);
        buttonPanel.add(statsPanel);

        // Adding the two panels to the main window
        this.add(buttonPanel, "West");
        this.add(view, "East");
        add(buttonPanel);
        add(view);
    }

    public static void main(String[] args) {
        AppFactory factory = new SimStationFactory();
        AppPanel panel = new SimStationPanel((SimStationFactory) factory);
        panel.display();
    }
}