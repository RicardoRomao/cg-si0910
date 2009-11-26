package Player;


import java.awt.Point;
import java.lang.reflect.Type;

public interface IPlayer {
    
    Point[] getElement(Type elem);
    Point play ();
    void draw();

}
