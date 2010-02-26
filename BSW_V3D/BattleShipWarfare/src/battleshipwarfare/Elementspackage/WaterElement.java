package battleshipwarfare.Elementspackage;

import battleshipwarfare.Boardpackage.Point;
import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;
import javax.vecmath.Point3d;

public class WaterElement extends Element {

    public WaterElement(){
        this(new Point());
    }
    public WaterElement(Point anchor){
        _type = ElementType.WATER;
        _anchor = anchor;
        _status = ElementStatus.ALIVE;
        setArea(null);
        _hitted = new Point[1];
        _liveCells = 1;
    }

    public void setArea(Point direction){
        _area = new Point[1];
        _area[0] = _anchor;
    }
    public void hit(Point p) {
        if(_status != ElementStatus.ALIVE)
            return;
        _hitted[_hitted.length - _liveCells--] = p;
        _status = ElementStatus.SUNK;
    }

    @Override
    public void drawInConsole(Point p, boolean owned) {
        //Ignores owned because it doesnt matter
        //Water element hava the same representation despites it
        //is owned or not owned board
        System.out.print((_status == ElementStatus.ALIVE)?" ":"X");
    }

    public GeometryInfo getGeometryInfo(boolean own){
        int idx = 0;
        GeometryInfo gi = new GeometryInfo(GeometryInfo.POLYGON_ARRAY);

        Point3d[] vertices  = new Point3d[8];

        //Pontos das faces [0..7]
        vertices[idx++] = new Point3d(-0.5, -0.5, 0);   //0
        vertices[idx++] = new Point3d(-0.5, 0.5, 0);    //1     1 3
        vertices[idx++] = new Point3d(0.5, -0.5, 0);    //2     0 2
        vertices[idx++] = new Point3d(0.5, 0.5, 0);     //3

        vertices[idx++] = new Point3d(-0.5, -0.5, 0.1); //4
        vertices[idx++] = new Point3d(-0.5, 0.5, 0.1);  //5     5 7
        vertices[idx++] = new Point3d(0.5, -0.5, 0.1);  //6     4 6
        vertices[idx++] = new Point3d(0.5, 0.5, 0.1);   //7

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
}
