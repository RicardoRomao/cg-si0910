package battleshipwarfare.Elementspackage;

import battleshipwarfare.Boardpackage.Point;
import java.awt.Color;
import java.util.Hashtable;
import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.Shape3D;
import javax.vecmath.Color3f;

/**
 * Class the represents a LineElement
 * @author RNR
 */
public class LineElement extends Element {

    /**
     * Indicates if the element is owned or opponent
     */
    private boolean _own;

    /**
     * Constructs a new LineElement that starts in the <b>anchor</b> point,
     * in the <b>direction</b> direction and of <b>type</b> element type.
     * @param type The element type
     * @param anchor The element anchor.
     * @param direction The element directoin
     * @param own is Human or IA element
     */
    public LineElement(ElementType type, Point anchor, Point direction, boolean own) {
        _type = type;
        _liveCells = type.ordinal();
        _anchor = anchor;
        _status = ElementStatus.ALIVE;
        _own = own;
        setArea(direction);
        _shapes = new Hashtable<Point, Shape3D>();
        setShapes();
        _hitted = new Point[type.ordinal()];
    }

    public void setArea(Point direction) {
        _area = new Point[_type.ordinal()];
        _area[0] = _anchor;
        if (_type != ElementType.AIRCRAFT) {
            for (int i = 1; i < _type.ordinal(); i++) {
                _area[i] = Point.Add(_area[i - 1], direction);
            }
        } else {
            for (int i = 1; i < _type.ordinal(); i++) {
                _area[i] = new Point(_anchor.getX() + i * direction.getX(), _anchor.getY() + i * direction.getY());
            }
        }
    }

    private void setShapes() {
        for (int i = 0; i < _area.length; i++) {
            setShape(_area[i]);
        }
    }

    private Material getMaterial(boolean hitState)
    {
        Material material = new Material();
        if ((_own && !hitState) ||(!_own && hitState)) {
            if (_type == ElementType.SUBMARINE) {
                material.setDiffuseColor(new Color3f(new Color(164,99,15)));
                material.setEmissiveColor(new Color3f(new Color(159,101,27)));
            } else if (_type == ElementType.PATROL_BOAT) {
                material.setDiffuseColor(new Color3f(new Color(70,40,0)));
                material.setEmissiveColor(new Color3f(new Color(72,41,6)));
            } else if (_type == ElementType.DESTROYER) {
                material.setDiffuseColor(new Color3f(new Color(255,255,255)));
                material.setEmissiveColor(new Color3f(new Color(65,144,12)));
            } else if (_type == ElementType.BATTLESHIP) {
                material.setDiffuseColor(new Color3f(new Color(14,180,0)));
                material.setEmissiveColor(new Color3f(new Color(14,150,0)));
            }
        } else if (!_own && !hitState){
            material.setDiffuseColor(new Color3f(Color.blue));
            material.setEmissiveColor(new Color3f(new Color(14,18,66)));
        } else if (_own && hitState){
            material.setDiffuseColor(new Color3f(new Color(248,34,34)));
            material.setEmissiveColor(new Color3f(new Color(250,79,79)));
        }
        material.setLightingEnable(true);
        material.setShininess(128f);
        return material;
    }

    private void setShape(Point p) {
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
        if (--_liveCells == 0) {
            _status = ElementStatus.SUNK;
        } else if (_status == ElementStatus.ALIVE) {
            _status = ElementStatus.HITTED;
        }
    }

    private void hitShape(Point p) {
        Shape3D sh = _shapes.get(p);
        Appearance appearance = new Appearance();
        appearance.setMaterial(getMaterial(true));
        sh.setAppearance(appearance);
    }
    //@Override
    //public GeometryInfo getGeometryInfo(boolean own) {
    //return null;
    //}
}
