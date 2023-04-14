
package simstation;

import mvc.*;

public interface SimFactory extends AppFactory {
    public View makeView(Model m);
}
