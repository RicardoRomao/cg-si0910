package Engine;

import Elements.ElementType;
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
}
