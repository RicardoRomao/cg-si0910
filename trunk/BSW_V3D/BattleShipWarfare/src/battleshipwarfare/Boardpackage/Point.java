package battleshipwarfare.Boardpackage;

/**
 * Class that represents a coordinate in the game.<br>
 * Since it was only needed the coordinates (x and y), this group choose to
 * implement is own class rather than use an existing one, such as AWT.Point.
 * @author RNR
 */
public class Point{
    private static final long serialVersionUID = 1L;

    private int _x;
    private int _y;

    /**
     * Parameterless constructor.<br>
     * Constructs a origin point.
     */
    public Point(){
        this(0, 0);
    }
    /**
     * Constructs a point with the given coordinates.
     * @param x The X coordinate.
     * @param y The Y coordinate.
     */
    public Point(int x, int y){
        _x = x;
        _y = y;
    }

    /**
     * Gets the X coordinate.
     * @return
     */
    public int getX(){
        return _x;
    }
    /**
     * Gets the Y coordinate.
     * @return
     */
    public int getY(){
        return _y;
    }
    /**
     * Adds two points.
     * @param a One of the points to be added.
     * @param b One of the points to be added.
     * @return
     * Returns a new point with the sum of the two point coordinates.
     */
    public static Point Add(Point a, Point b){
        return new Point(a.getX() + b.getX(), a.getY() + b.getY());
    }

    @Override
    public boolean equals(Object o){
        if(o.getClass() == Point.class)
            return equals((Point)o);
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + _x;
        hash = 61 * hash + _y;
        return hash;
    }
    /**
     * Checks if one point is equal to the other, that means:<br>
     * If one point X coordinate is equal to the other point X coordinate
     * and one point Y coordinate is equal to the other point Y coordinate.
     * @param p The point to be compared whith the current one.
     * @return boolean indicating if the other point is equal or not
     */
    public boolean equals(Point p){
        return (_x == p.getX() && _y == p.getY());
    }
    /**
     * Translates one point <b>dx</b> on the X-Axis and <b>dy</b> in the Y-Axis.
     * @param dx The translaction in the X-Axis.
     * @param dy The translaction in the Y-Axis.
     */
    public void translate(int dx, int dy){
        _x += dx;
        _y += dy;
    }
}
