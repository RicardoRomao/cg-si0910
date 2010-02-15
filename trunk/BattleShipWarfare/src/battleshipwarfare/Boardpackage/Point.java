package battleshipwarfare.Boardpackage;

public class Point{// extends java.awt.Point{
    private static final long serialVersionUID = 1L;

    private int _x;
    private int _y;

    public Point(){
        this(0, 0);
    }
    public Point(int x, int y){
        _x = x;
        _y = y;
    }

    public int getX(){
        return _x;
    }
    public int getY(){
        return _y;
    }
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
    public boolean equals(Point p){
        return (_x == p.getX() && _y == p.getY());
    }
    public void translate(int dx, int dy){
        _x += dx;
        _y += dy;
    }
}
