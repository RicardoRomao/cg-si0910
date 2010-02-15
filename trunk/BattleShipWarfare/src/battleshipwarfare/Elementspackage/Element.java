/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package battleshipwarfare.Elementspackage;

import battleshipwarfare.Boardpackage.Point;
import java.util.ArrayList;

public abstract class Element implements IElement{

    protected Point _anchor;
    protected ElementType _type;
    protected ElementStatus _status;
    protected Point[] _area;
    protected Point[] _hitted;
    protected int _liveCells;

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
    private boolean isHit(Point p){
        for(int i = 0; _hitted[i] != null && i < _type.ordinal(); i++){
            if(_hitted[i].equals(p))
                return true;
        }
        return false;
    }

    public static String toString(int size){
        switch(size){
            case 0:
                return "Water";
            case 1:
                return "Submarine";
            case 2:
                return "Patrol Boat";
            case 3:
                return "Destroyer";
            case 4:
                return "Battleship";
            case 5:
                return "Aircraft";
            default:
                return "";
        }
    }
}
