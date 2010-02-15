package battleshipwarfare.Boardpackage;

import battleshipwarfare.Elementspackage.IElement;

public interface IBoard {
    final int DEFAULT_ROWS = 10;
    final int DEFAULT_COLS = 10;

    public Point getEndPoint();
    public boolean addElement(IElement elem, boolean withAdjacent);
    public IElement shoot(Point p);
    public void drawInConsole(boolean own);
    public boolean isInBounds(Point p);
}
