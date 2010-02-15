package battleshipwarfare.Boardpackage;

import battleshipwarfare.Elementspackage.IElement;
import java.util.Hashtable;

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
