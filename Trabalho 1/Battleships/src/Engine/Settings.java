package Engine;

import Elements.ElementType;
import java.awt.Point;
import java.util.Hashtable;

public class Settings {


    public static Hashtable<ElementType,Integer> getElemRules()
    {
        Hashtable<ElementType,Integer> elemRules = new Hashtable<ElementType, Integer>();
        elemRules.put(ElementType.SUBMARINE, 4);
        elemRules.put(ElementType.PATROL_BOAT, 3);
        elemRules.put(ElementType.DESTROYER, 2);
        elemRules.put(ElementType.BATTLESHIP, 1);
        elemRules.put(ElementType.AIRCRAFT, 1);
        return elemRules;
    }

    public static final Point BOUNDS = new Point(10,10);
    public static final Point NORTH = new Point(0,1);
    public static final Point SOUTH = new Point(0,-1);
    public static final Point EAST = new Point(-1,0);
    public static final Point WEST = new Point(1,0);

    public static boolean isInBounds(Point p) {
        return (p.x < BOUNDS.x && p.y < BOUNDS.y && p.x >= 0 && p.y >= 0);
    }
}
