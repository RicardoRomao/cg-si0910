package Player;

import java.awt.Point;
import java.lang.reflect.Type;
import java.util.Scanner;


public class Player implements IPlayer {

    String _name;

    public Point[] getElement(Type elem)
    {
        Point p = new Point();
        Scanner in = new Scanner(System.in);
        System.out.print("Enter coordinate x: ");
        p.x = in.nextInt();
        System.out.print("Enter coordinate y: ");
        p.y = in.nextInt();
        return new Point[2];
    }

    public Point play() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void draw() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
