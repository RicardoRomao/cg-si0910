package battleshipwarfare.Elementspackage;

import battleshipwarfare.Boardpackage.Point;
import java.awt.Color;
import java.util.Hashtable;
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
        _shapes = new Hashtable<Point, Shape3D>();
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
    private Material getMaterial(boolean hitState)
    {
        Material material = new Material();
        if ((_own && !hitState) || (!_own && hitState)) {
                material.setDiffuseColor(new Color3f(new Color(5,39,2)));
                material.setEmissiveColor(new Color3f(new Color(6,64,2)));
        } else if (!_own && !hitState) {
            material.setDiffuseColor(new Color3f(Color.blue));
            material.setEmissiveColor(new Color3f(new Color(14,18,66)));
        } else if (_own && hitState) {
            material.setEmissiveColor(new Color3f(new Color(248,34,34)));
            material.setEmissiveColor(new Color3f(new Color(250,79,79)));
        }
        material.setLightingEnable(true);
        material.setShininess(128f);
        return material;
    }
    private void setShape(Point p){
        Shape3D sh = new Shape3D();
        sh.setName(p.getX() + "," + p.getY());
        sh.setGeometry(getGeometryInfo(p).getIndexedGeometryArray());

        Appearance appearance = new Appearance();
        Material material = getMaterial(false);
        appearance.setMaterial(material);
        sh.setAppearance(appearance);

        _shapes.put(p, sh);
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
        Appearance appearance = new Appearance();
        appearance.setMaterial(getMaterial(true));
        sh.setAppearance(appearance);
    }
}
