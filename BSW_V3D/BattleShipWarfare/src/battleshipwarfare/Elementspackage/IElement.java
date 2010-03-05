package battleshipwarfare.Elementspackage;

import battleshipwarfare.Boardpackage.Point;
import javax.media.j3d.Shape3D;

/**
 * Definition of a game element.
 * @author RNR
 */
public interface IElement {

    /**
     * The cardinal points
     */
    public static Point[] cardinalPoints = {new Point(0, -1), new Point(0, 1),new Point(1, 0), new Point(-1, 0)};

    /**
     * Gets the element type.
     * @return ElementType The element type.
     */
    public ElementType getType();
    /**
     * Gets the element status
     * @return ElementStatus The element status.
     */
    public ElementStatus getStatus();

    /**
     * Gets all the points ocuppied by the element.
     * @return Point[] All points ocuppied by the element.
     */
    public Point[] getArea();
    /**
     * Gets all the point ocuppied by the element plus all the adjacent points.
     * @return Point[] All points ocuppied by the element plus adjacent points.
     */
    public Point[] getAreaWithAdjacent();

    /**
     * Sets a hitted point in the element.
     * @param p Point The point hitted.
     */
    public void hit(Point p);
    /**
     * Draws a point of the element in the console.
     * @param p Point to be draw.
     * @param own boolean indicating if the element is of current player or not.
     */
    public void drawInConsole(Point p, boolean own);

    /**
     * Gets a Shape3D representing a point of the current element.
     * @param p Point The point to be returned.
     * @param own boolean indicating if the element is of current player or not.
     * @return Shape3D The Shape3D representing a point of the element.
     */
    public Shape3D getShape(Point p);
}
