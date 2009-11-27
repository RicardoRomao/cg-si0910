package Player;


import Elements.ElementType;
import java.awt.Point;

public interface IPlayer {
    
    Point[] getElement(ElementType elemType);
    Point play ();
    void notify();
    void draw();

}
