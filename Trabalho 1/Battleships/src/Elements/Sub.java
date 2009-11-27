package Elements;

import java.awt.Point;
import java.util.Collection;

public class Sub extends Element {
    
    public Sub(Point p, Point direction)
    {
        super(p,direction,subSize);
    }

    public static Collection<Point> getPoints(Point p, Point dir) {
        return null;
    }

    public void draw() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void drawDamage() {
        throw new UnsupportedOperationException("Not supported yet.");
    }



}
