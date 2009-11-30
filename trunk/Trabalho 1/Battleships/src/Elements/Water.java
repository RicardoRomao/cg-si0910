package Elements;

import java.awt.Point;
import java.util.Collection;
import Engine.Settings.ElementStatus;
import Engine.Settings.ElementType;
/**
 * Classe que representa o Elemento Água
 *
 * @author RNR
 */
public class Water implements IElement {

    private ElementType _type;

    public Water() {
        _type = ElementType.WATER;
    }

    public ElementType getType() {
        return _type;
    }

    public ElementStatus getStatus(){
        return ElementStatus.ALIVE;
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
    public Collection<Point> getPointsWithAdjacent() {
        return null;
    }

    public void hit() {
    }
}
