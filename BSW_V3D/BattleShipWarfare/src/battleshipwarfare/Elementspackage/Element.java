/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package battleshipwarfare.Elementspackage;

import battleshipwarfare.Boardpackage.Point;
import battleshipwarfare.GameRules;
import battleshipwarfare.Settings3D;
import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;
import java.awt.Color;
import java.util.ArrayList;
import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.Shape3D;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

/**
 * Abstract class that represents a Game element.
 * @author RNR
 */
public abstract class Element implements IElement{

    /**
     * The element anchor point.
     */
    protected Point _anchor;
    /**
     * The element type.
     */
    protected ElementType _type;
    /**
     * The element status.
     */
    protected ElementStatus _status;
    /**
     * The element area.
     */
    protected Point[] _area;
    /**
     * The hitted points of the element.
     */
    protected Point[] _hitted;
    /**
     * How many unhitted points.
     */
    protected int _liveCells;

    private boolean isHit(Point p){
        for(int i = 0; _hitted[i] != null && i < _type.ordinal(); i++){
            if(_hitted[i].equals(p))
                return true;
        }
        return false;
    }
    private void GetAdjacentCells(Point p, ArrayList<Point> arr){
        Point a;
        for (int i = -1; i < 2; i++)
        {
            for (int j = -1; j < 2; j++)
            {
                if (i != 0 || j != 0)
                {
                    if(!arr.contains(a = new Point(p.getX() + i, p.getY() + j)))
                        arr.add(a);
                }
            } // for
        } // for
    }

    public Point[] getArea() {
        return _area;
    }
    public Point[] getAreaWithAdjacent(){
        ArrayList<Point> retPoints = new ArrayList<Point>();
        Point[] area = getArea();
        for(int i = 0; i < area.length && area[i] != null; i++){
            if(!retPoints.contains(area[i]))
                retPoints.add(area[i]);
            GetAdjacentCells(area[i], retPoints);
        }
        Point[] x = new Point[retPoints.size()];
        return retPoints.toArray(x);
        //return x;
    }

    public ElementType getType(){
        return _type;
    }
    public ElementStatus getStatus(){
        return _status;
    }
    
    public void drawInConsole(Point p, boolean own) {
        if(isHit(p))
            System.out.print(own ? "X" : _type.ordinal());
        else
            System.out.print(own ? _type.ordinal() : " ");
    }
    public Shape3D getShape(Point p, boolean own){
        Shape3D sh = new Shape3D();
        sh.setName(p.getX() + "," + p.getY());
        if(!own){
            Appearance appearance = new Appearance();
            if(!isHit(p)){
                appearance.setMaterial(new Material(new Color3f(Color.BLUE), new Color3f(Color.BLUE)
                    ,new Color3f(Color.BLUE),new Color3f(Color.BLUE), 0.5f));

                sh.setAppearance(appearance);
                sh.setGeometry(getUnHittedPoint(p).getIndexedGeometryArray());
            }else{
                sh.setGeometry(getGeometryInfo(own).getIndexedGeometryArray());
            }
        }
        return sh;
    }
    private GeometryInfo getUnHittedPoint(Point p){
        int idx = 0;
        GeometryInfo gi = new GeometryInfo(GeometryInfo.POLYGON_ARRAY);

        Point3d[] vertices  = new Point3d[8];

        double deltaX = (p.getX()*Settings3D.getOwnCellSize()) +
                (p.getX()+1)*Settings3D.getOwnBoardCellMargin();
        double deltaY = (GameRules.getCurrentRules().getRows() - p.getY()-1)*Settings3D.getOwnCellSize() +
                (GameRules.getCurrentRules().getRows() - p.getY())*Settings3D.getOwnBoardCellMargin();
        
        double startX = Settings3D.getOwnBoardStartPointX();
        double startY = Settings3D.getOwnBoardStartPointY();


        //Pontos das faces [0..7]
        vertices[idx++] = new Point3d(startX+deltaX, startY+deltaY, 0.1);                           //0
        vertices[idx++] = new Point3d(startX+deltaX, startY+Settings3D.getOwnCellSize()+deltaY, 0.1);  //1     1 3
        vertices[idx++] = new Point3d(startX+Settings3D.getOwnCellSize()+deltaX, startY+deltaY, 0.1);  //2     0 2
        vertices[idx++] = new Point3d(startX+Settings3D.getOwnCellSize()+deltaX,
                startY+Settings3D.getOwnCellSize()+deltaY, 0.1);                                       //3

        vertices[idx++] = new Point3d(startX+deltaX, startY+deltaY, 0.12);                           //4
        vertices[idx++] = new Point3d(startX+deltaX, startY+Settings3D.getOwnCellSize()+deltaY, 0.12);  //5     5 7
        vertices[idx++] = new Point3d(startX+Settings3D.getOwnCellSize()+deltaX, startY+deltaY, 0.12);  //6     4 6
        vertices[idx++] = new Point3d(startX+Settings3D.getOwnCellSize()+deltaX,
                startY+Settings3D.getOwnCellSize()+deltaY, 0.12);                                       //7

        gi.setCoordinates(vertices);

        idx = 0;

        int[] indices = new int[24];

        //Face de trás (4)
        indices[idx++]=0;
        indices[idx++]=1;
        indices[idx++]=3;
        indices[idx++]=2;

        //Face da frente (4)
        indices[idx++]=7;
        indices[idx++]=5;
        indices[idx++]=4;
        indices[idx++]=6;

        //Face Esquerda (4)
        indices[idx++]=5;
        indices[idx++]=1;
        indices[idx++]=0;
        indices[idx++]=4;

        //Face Direita (4)
        indices[idx++]=2;
        indices[idx++]=3;
        indices[idx++]=7;
        indices[idx++]=6;

        //Face Superior (4)
        indices[idx++]=3;
        indices[idx++]=1;
        indices[idx++]=5;
        indices[idx++]=7;

        //Face Inferior (4)
        indices[idx++]=4;
        indices[idx++]=0;
        indices[idx++]=2;
        indices[idx++]=6;

        gi.setCoordinateIndices(indices);

        int[] stripCounts = {4, 4, 4, 4, 4, 4};
        gi.setStripCounts(stripCounts);

        int[] contourCount = {1, 1, 1, 1, 1, 1};
        gi.setContourCounts(contourCount);

        NormalGenerator ng = new NormalGenerator();
        ng.generateNormals(gi);

        return gi;
    }

    /**
     * Gets the element GeometryInfo to build the element Shape3D
     * @param own boolean indicating if the element is of current player or not.
     * @return GeometryInfo The element geometryInfo.
     */
    public abstract GeometryInfo getGeometryInfo(boolean own);
}
