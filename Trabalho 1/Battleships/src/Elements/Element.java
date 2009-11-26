package Elements;

import Engine.IConstants;
import java.awt.Point;
import java.util.Collection;

public abstract class Element implements IConstants, IElement {

    int _size;
    int _hitCount;
    Point _anchor;
    Point _direction;

    public Element(Point p, Point direction, int size) {
        _hitCount = 0;
        _anchor = p;
        this._direction = direction;
        this._size = size;
    }

    public Collection<Point> getPoints() {
        return null;
    }

    public boolean hit() {
        return (_size == _hitCount);
    }
}
