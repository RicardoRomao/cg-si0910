package battleshipwarfare.Boardpackage;

import battleshipwarfare.Elementspackage.IElement;
import battleshipwarfare.GameRules;
import battleshipwarfare.Settings3D;
import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;
import java.util.Hashtable;
import javax.media.j3d.Shape3D;
import javax.vecmath.Point3d;

public class Board implements IBoard{
        
    private Hashtable<Point, IElement> _elements;
    private Point _endPoint;

    public Board(){
        this(IBoard.DEFAULT_ROWS, IBoard.DEFAULT_COLS);
    }
    public Board(int rows, int cols){
        _endPoint = new Point(rows - 1, cols - 1);
        _elements = new Hashtable<Point, IElement>();
    }

    public Point getEndPoint(){
        return _endPoint;
    }    
    public boolean addElement(IElement elem, boolean withAdjacent){
        if(isPlaceable(elem, withAdjacent)){
            Point[] area = elem.getArea();
            for(int i = 0; i < area.length; i++){
                _elements.put(area[i], elem);
            }
            System.out.println("Added " + elem.getType().name());
            return true;
        }
        return false;
    }
    public IElement shoot(Point p){
        IElement elem;
        if((elem = _elements.get(p)) != null){
            elem.hit(p);
            return elem;
        }
        return null;
    }
    public void drawInConsole(boolean own){
        Point p;
        System.out.println(own?"My Board":"Opponent Board");
        System.out.println();
        System.out.print(" ");
        for(int i = 0; i <= _endPoint.getY(); i++){
            System.out.print(i + 1);
        }
        System.out.println();
        for(int i = 0; i <= _endPoint.getY(); i++){
            System.out.print((char)('A' + i));
            for(int j = 0; j <= _endPoint.getX(); j++){
                p = new Point(j, i);
                _elements.get(p).drawInConsole(p, own);
            }
            System.out.println(("|"));
        }
        for(int i = 0; i <= _endPoint.getY(); i++){
            System.out.print("-");
        }
        System.out.println("-");
    }
    public boolean isInBounds(Point p){
        return p.getX() >= 0 && p.getY() >= 0 && p.getX() <= _endPoint.getX()
                && p.getY() <= _endPoint.getY();
    }
    public Shape3D getShape(){
        Shape3D sh = new Shape3D();

        int idx = 0;

        GeometryInfo gi = new GeometryInfo(GeometryInfo.POLYGON_ARRAY);
        Point3d[] vertices  = new Point3d[8];

        //É necessário contemplar na definição das dimensões do Board a margem entre cells
        //(n cols / 2) + (n cols / 2)= cols margens, mas é necessária mais uma
        //visto 10 cols terem 9 maergens + 2 exteriores
        
//        double deltaX = ((GameRules.getCurrentRules().getCols()/2))*Settings3D.getBoardCellMargin() +
//                Settings3D.getBoardCellMargin()/2;
//        double deltaY = ((GameRules.getCurrentRules().getRows()/2))*Settings3D.getBoardCellMargin() +
//                Settings3D.getBoardCellMargin()/2;

        double startX = Settings3D.getOwnBoardStartPointX();
        double startY = Settings3D.getBoardStartPointY();
        double endX = Settings3D.getBoardEndPointX();
        double endY = Settings3D.getBoardEndPointY();

        //Pontos das faces [0..7]
        vertices[idx++] = new Point3d(startX, startY, 0);     //0
        vertices[idx++] = new Point3d(startX, endY, 0);       //1     1 3
        vertices[idx++] = new Point3d(endX, startY, 0);       //2     0 2
        vertices[idx++] = new Point3d(endX, endY, 0);         //3

        vertices[idx++] = new Point3d(startX, startY, 0.1);   //4
        vertices[idx++] = new Point3d(startX, endY, 0.1);     //5     5 7
        vertices[idx++] = new Point3d(endX, startY, 0.1);     //6     4 6
        vertices[idx++] = new Point3d(endX, endY, 0.1);       //7

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

        sh.setGeometry(gi.getIndexedGeometryArray());

        return sh;
    }
    public Shape3D getElementShape(Point p, boolean own){
        return _elements.get(p).getShape(p, own);
    }

    private boolean isPlaceable(IElement elem, boolean withAdjacent){
        if(!isInBounds(elem.getArea()))
            return false;
        Point[] area = withAdjacent ? elem.getAreaWithAdjacent() : elem.getArea();
        for(int i = 0; i < area.length; i++){
            if(_elements.containsKey(area[i]))
                return false;
        }
        return true;
    }
    private boolean isInBounds(Point[] area){
        for(int i = 0; i < area.length && area[i] != null; i++){
            if(!isInBounds(area[i]))
                return false;
        }
        return true;
    }

    
}
