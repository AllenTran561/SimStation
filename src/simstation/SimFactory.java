package simstation;

import mvc.*;

/* Interface "SimFactory" Datalog
4/9/2023 - Minh Bui: Created file and implemented method
*/

public interface SimFactory extends AppFactory {
    public View makeView(Model m);
}