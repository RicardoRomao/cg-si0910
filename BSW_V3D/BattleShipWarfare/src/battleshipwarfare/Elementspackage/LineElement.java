package battleshipwarfare.Elementspackage;

import battleshipwarfare.Boardpackage.Point;
import com.sun.j3d.utils.geometry.GeometryInfo;
import java.awt.Color;
import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.Shape3D;
import javax.vecmath.Color3f;

/**
 * Class the represents a LineElement
 * @author RNR
 */
public class LineElement extends Element{

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
    public LineElement(ElementType type, Point anchor, Point direction, boolean own){
        _type = type;
        _liveCells = type.ordinal();
        _anchor = anchor;
        _status = ElementStatus.ALIVE;
        _own = own;
        setArea(direction);
        setShapes();
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
    private void setShapes() {
        for(int i = 0; i < _area.length; i++)
            setShape(_area[i]);
    }
    private void setShape(Point p){
        Shape3D sh = new Shape3D();
        sh.setName(p.getX() + "," + p.getY());
        sh.setGeometry(getGeometryInfo(p).getIndexedGeometryArray());

        if(_own){
            Appearance appearance = new Appearance();
            appearance.setMaterial(new Material(new Color3f(Color.YELLOW), new Color3f(Color.YELLOW)
                    ,new Color3f(Color.YELLOW),new Color3f(Color.YELLOW), 0.5f));

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

    //@Override
    //public GeometryInfo getGeometryInfo(boolean own) {
        //return null;
    //}
}
