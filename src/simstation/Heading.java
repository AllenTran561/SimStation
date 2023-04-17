package simstation;

import java.util.*;

/* Enumeration "Heading" Datalog
4/7/2023 - Allen Tran: Created file and implemented methods
*/

public enum Heading {
    EAST, WEST, NORTH, SOUTH;

    public static Heading randomHeading(){
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}