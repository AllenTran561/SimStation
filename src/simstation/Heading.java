
package simstation;

import java.util.*;
public enum Heading {
    EAST, WEST, NORTH, SOUTH;

    public static Heading randomHeading(){
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}
