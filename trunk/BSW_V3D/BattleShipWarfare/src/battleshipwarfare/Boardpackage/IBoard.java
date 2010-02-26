package battleshipwarfare.Boardpackage;

import battleshipwarfare.Elementspackage.IElement;
import javax.media.j3d.Shape3D;

public interface IBoard {
    final int DEFAULT_ROWS = 10;
    final int DEFAULT_COLS = 10;

    public Point getEndPoint();
    public boolean addElement(IElement elem, boolean withAdjacent);
    public IElement shoot(Point p);
    public void drawInConsole(boolean own);
    public boolean isInBounds(Point p);

    public Shape3D getShape();
    public Shape3D getElementShape(Point p, boolean own);
}
