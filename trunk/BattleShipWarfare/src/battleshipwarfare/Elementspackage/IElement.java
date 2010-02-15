package battleshipwarfare.Elementspackage;

import battleshipwarfare.Boardpackage.Point;

public interface IElement {

    public static Point[] cardinalPoints = {new Point(0, -1), new Point(0, 1),new Point(1, 0), new Point(-1, 0)};

    public ElementType getType();
    public ElementStatus getStatus();

    public Point[] getArea();
    public Point[] getAreaWithAdjacent();
    public void setArea(Point direction);

    public void hit(Point p);
    public void drawInConsole(Point p, boolean own);
}
