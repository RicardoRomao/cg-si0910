package Engine;


import Elements.IElement;
import Elements.Water;
import java.awt.Point;
import java.util.Hashtable;

public abstract class GameBoard {

    Point _end;
    int _nElems;

    private static final Water _water = new Water();
    
    private Hashtable<Point, IElement> _receivedShots;
    private Hashtable<Point, IElement> _board;

    public GameBoard(int lines, int cols)
    {
    }

    private IElement shoot(Point p)
    {
        //Vai fazer uma jogada sobre um ponto
        //Se o target for um element e isSunk() então _nElems--
        return null;
    }

    private boolean isInBounds(IElement e)
    {
        return false;
    }

    private boolean isAlive()
    {
        return (_nElems == 0);
    }

    public boolean addElement(IElement e)
    {

        //Pergunta ao element quais os pontos que ocupa
        //Vê se estão inbounds, se sim true e _nElems++
        return false;
    }

    public void draw()
    {
        
    }
}
