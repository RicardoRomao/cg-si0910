package Elements;

import java.awt.Point;
import java.util.Collection;

public interface IElement {

    void draw();
    void drawDamage();
    boolean hit();

    Collection<Point> getPoints();

}
