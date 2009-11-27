package Elements;

import java.awt.Point;
import java.util.Collection;

public class Water implements IElement {

    private ElementType _type;

    public Water() {
        _type = ElementType.WATER;
    }

    public ElementType getType() {
        return _type;
    }

    public void draw() {
        System.out.print(" ");
    }

    public void drawDamage() {
        System.out.print("X");
    }

    public Collection<Point> getPoints() {
        return null;
    }

    public void hit() {
    }
}
