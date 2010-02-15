package battleshipwarfare.Elementspackage;

import battleshipwarfare.Boardpackage.Point;

public class LineElement extends Element{

    public LineElement(ElementType type, Point anchor, Point direction){
        _type = type;
        _liveCells = type.ordinal();
        _anchor = anchor;
        _status = ElementStatus.ALIVE;
        setArea(direction);
        _hitted = new Point[type.ordinal()];
    }
    
    public void setArea(Point direction){
        _area = new Point[_type.ordinal()];
        _area[0] = _anchor;
        if(_type != ElementType.AIRCRAFT)
            for(int i = 1; i < _type.ordinal(); i++)
                _area[i] = Point.Add(_area[i - 1], direction);
        else
            for (int i = 1; i < _type.ordinal(); i++)
                _area[i] = new Point(_anchor.getX() + i * direction.getX(), _anchor.getY() + i * direction.getY());
    }
    public void hit(Point p) {
        _hitted[_type.ordinal() - _liveCells] = p;
        if(--_liveCells == 0)
            _status = ElementStatus.SUNK;
        else
            if(_status == ElementStatus.ALIVE)
                _status = ElementStatus.HITTED;
    }
}
