package battleshipwarfare.Elementspackage;

import battleshipwarfare.Boardpackage.Point;
import java.awt.Color;
import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.Shape3D;
import javax.vecmath.Color3f;

/**
 * Class that represents a Aircraft element.
 * @author RNR
 */
public class AircraftElement extends Element {

    private boolean _own;
    /**
     * Constructs a new Aircraft Element.
     * @param anchor The element anchor point.
     * @param direction The element direction.
     */
    public AircraftElement(Point anchor, Point direction, boolean own){
        _type = ElementType.AIRCRAFT;                
        _anchor = anchor;
        _own = own;
        setArea(direction);
        setShapes();
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
    private void setShapes(){
        for(int i = 0; i < _area.length; i++)
            setShape(_area[i]);
    }
    private void setShape(Point p){
        Shape3D sh = new Shape3D();
        sh.setName(p.getX() + "," + p.getY());
        sh.setGeometry(getGeometryInfo(p).getIndexedGeometryArray());

        if(_own){
            Appearance appearance = new Appearance();
            appearance.setMaterial(new Material(new Color3f(Color.GREEN), new Color3f(Color.GREEN)
                    ,new Color3f(Color.GREEN),new Color3f(Color.GREEN), 0.5f));

            sh.setAppearance(appearance);
        }else{
            Appearance appearance = new Appearance();
            appearance.setMaterial(new Material(new Color3f(Color.BLUE), new Color3f(Color.BLUE)
                    ,new Color3f(Color.BLUE),new Color3f(Color.BLUE), 0.5f));

            sh.setAppearance(appearance);
        }
    }
    public void hit(Point p) {
        _hitted[_type.ordinal() - _liveCells] = p;
        hitShape(p);
        if(--_liveCells == 0)
            _status = ElementStatus.SUNK;
        else
            if(_status == ElementStatus.ALIVE)
                _status = ElementStatus.HITTED;
    }
    private void hitShape(Point p){
        Shape3D sh = _shapes.get(p);
        if(_own){
            Appearance appearance = new Appearance();
            appearance.setMaterial(new Material(new Color3f(Color.RED), new Color3f(Color.RED)
                    ,new Color3f(Color.RED),new Color3f(Color.RED), 0.5f));

            sh.setAppearance(appearance);
        }else{
            Appearance appearance = new Appearance();
            appearance.setMaterial(new Material(new Color3f(Color.YELLOW), new Color3f(Color.YELLOW)
                    ,new Color3f(Color.YELLOW),new Color3f(Color.YELLOW), 0.5f));

            sh.setAppearance(appearance);
        }
    }
}
