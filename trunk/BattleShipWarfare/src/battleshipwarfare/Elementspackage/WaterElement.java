package battleshipwarfare.Elementspackage;

import battleshipwarfare.Boardpackage.Point;

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
}
