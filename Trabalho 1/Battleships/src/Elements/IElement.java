package Elements;

import java.awt.Point;
import java.util.Collection;

public interface IElement {

    void draw();
    boolean hit();
    

    Collection<Point> getPoints();

}
