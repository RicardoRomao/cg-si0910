package battleshipwarfare.Elementspackage;

import battleshipwarfare.Boardpackage.Point;
import com.sun.j3d.utils.geometry.GeometryInfo;

/**
 * Class that represents a Aircraft element.
 * @author RNR
 */
public class AircraftElement extends Element {
        
    /**
     * Constructs a new Aircraft Element.
     * @param anchor The element anchor point.
     * @param direction The element direction.
     */
    public AircraftElement(Point anchor, Point direction){
        _type = ElementType.AIRCRAFT;                
        _anchor = anchor;
        setArea(direction);
        _hitted = new Point[_type.ordinal()];
        _liveCells = _type.ordinal();
        _status = ElementStatus.ALIVE;
    }    

    public void setArea(Point direction){
        _area = new Point[_type.ordinal()];
        int pos = 0;
        _area[pos++] = _anchor;        
        if (direction.getY() == 1) {
                for (int i = 0; i < 2; i++) {
                    _area[pos++] = new Point(_anchor.getX() + (i + 1), _anchor.getY());
                    _area[pos++] = new Point(_anchor.getX() + 1, _anchor.getY() + (i + 1));
                }
            } else if (direction.getY() == -1) {
                for (int i = 0; i < 2; i++) {
                    _area[pos++] = new Point(_anchor.getX() + (i * (-1) - 1), _anchor.getY());
                    _area[pos++] = new Point(_anchor.getX() - 1, _anchor.getY() + (i * (-1) - 1));
                }
            } else if (direction.getX() == 1) {
                for (int i = 0; i < 2; i++) {
                    _area[pos++] = new Point(_anchor.getX() + (i + 1), _anchor.getY() - 1);
                    _area[pos++] = new Point(_anchor.getX(), _anchor.getY() + (i * (-1) - 1));
                }
            } else {
                for (int i = 0; i < 2; i++) {
                    _area[pos++] = new Point(_anchor.getX() + (i * (-1) - 1), _anchor.getY() + 1);
                    _area[pos++] = new Point(_anchor.getX(), _anchor.getY() + (i + 1));
                }
            }
    }
    public void hit(Point p) {
        _hitted[_type.ordinal() - _liveCells] = p;
        if(--_liveCells == 0)
            _status = ElementStatus.SUNK;
        else
            if(_status == ElementStatus.ALIVE)
                _status = ElementStatus.HITTED;
    }

    @Override
    public GeometryInfo getGeometryInfo(boolean own) {
        return null;
    }
}
