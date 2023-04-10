package simstation;

import mvc.Utilities;

import java.awt.*;
import java.io.Serializable;

public abstract class Agent implements Runnable, Serializable {

    public String name;
    private Integer xc, yc;
    private AgentState state;
    transient private Thread thread; // Thread is not serializable so it has to be transient
    protected SimStation world;
    public Heading heading;
    protected int speed;

    public int random;

    public Agent() {
        state = AgentState.READY;
        // color = Color.RED;
        world = null;
        thread = null;

        xc = Utilities.rng.nextInt(249) + 1; // initially random position 1-250
        yc = Utilities.rng.nextInt(249) + 1;

        random = Utilities.rng.nextInt(Heading.values().length); // random heading
        heading = Heading.values()[random];

    }

    public void setWorld(SimStation world) {
        this.world = world;
    }

    public synchronized void start() {
        state = AgentState.RUNNING;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        if (thread == null) {

            thread = new Thread(this);
            thread.start();
        }
        state = AgentState.STOPPED;
    }

    public synchronized boolean isStopped() {
        return state == AgentState.STOPPED;
    }

    public synchronized void suspend() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
        if (!isStopped())
            state = AgentState.SUSPENDED;
    }

    public synchronized boolean isSuspended() {
        return state == AgentState.SUSPENDED;
    }

    public synchronized void resume() {

        if (thread == null) {
            if (!isStopped()) {
                state = AgentState.RUNNING;
            }
            thread = new Thread(this);
            thread.start();
        }
        if (!isStopped()) {
            state = AgentState.READY;
            notify();
        }
    }

    public synchronized AgentState getState() {
        return state;
    }

    public synchronized void join() throws InterruptedException {
        if (thread != null)
            thread.join();
    }

    public synchronized String toString() {
        return name + ".state = " + state;
    }

    public void run() {
        thread = Thread.currentThread();
        while (!isStopped()) {
            state = AgentState.RUNNING;
            update();
            try {
                Thread.sleep(100);
                synchronized (this) {
                    while (isSuspended()) {
                        wait();
                    }
                }
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }
    }

    public abstract void update();

    public void move(int steps) {

        this.speed = steps;
        switch (this.heading) {
            case NORTH:
                this.xc -= steps;
                if (this.xc < 0)
                    this.xc += 250;
                break;
            case SOUTH:
                this.xc += steps;
                if (this.xc > 250)
                    this.xc -= 250;
                break;
            case EAST:
                this.yc += steps;
                if (this.yc > 250)
                    this.yc -= 250;
                break;
            case WEST:
                this.yc -= steps;
                if (this.yc < 0)
                    this.yc += 250;
                break;
        }
        world.changed();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX_Pos() {
        return xc;
    }

    public void setX_Pos(int x_Pos) {
        this.xc = x_Pos;
    }

    public int getY_Pos() {
        return yc;
    }

    public void setY_Pos(int y_Pos) {
        this.yc = y_Pos;
    }

    public Heading getHeading() {
        return this.heading;
    }

    public void setHeading(Heading head) {
        this.heading = head;
    }

    public abstract int getSpeed();
}
