package battleshipwarfare;

import battleshipwarfare.Elementspackage.ElementType;
import java.util.Hashtable;

public class GameRules {

    private static final boolean DefaultWithAdjacent = true;
    private static final int DefaultRows = 10;
    private static final int DefaultCols = 10;

    private static GameRules _current = new GameRules(); //SINGLETON

    private boolean _withAdjacent;
    private Hashtable<ElementType, Integer> _nrOfBoats;
    private int _rows;
    private int _cols;
    
    private GameRules(){
        _withAdjacent = DefaultWithAdjacent;
        _rows = DefaultRows;
        _cols = DefaultCols;
        setDefaultNrOfBoats();
    }
    public static GameRules getCurrentRules(){
        if(_current == null)
            _current = new GameRules();
        return _current;
    }
    private void setDefaultNrOfBoats(){
        _nrOfBoats = new Hashtable<ElementType, Integer>();
//        _nrOfBoats.put(ElementType.SUBMARINE, 4);
//        _nrOfBoats.put(ElementType.PATROL_BOAT, 3);
//        _nrOfBoats.put(ElementType.DESTROYER, 2);
//        _nrOfBoats.put(ElementType.BATTLESHIP, 1);
//        _nrOfBoats.put(ElementType.AIRCRAFT, 1);
        _nrOfBoats.put(ElementType.SUBMARINE, 1);
        _nrOfBoats.put(ElementType.PATROL_BOAT, 1);
        _nrOfBoats.put(ElementType.DESTROYER, 1);
        _nrOfBoats.put(ElementType.BATTLESHIP, 1);
        _nrOfBoats.put(ElementType.AIRCRAFT, 1);
    }

    public Hashtable<ElementType, Integer> getNrOfBoats(){
        return _nrOfBoats;
    }
    public void setNrOfBoats(Hashtable<ElementType, Integer> nrOfBoats){
        _nrOfBoats = nrOfBoats;
    }
    public boolean getWithAdjacent(){
        return _withAdjacent;
    }
    public void setWithAdjacent(boolean withAdjacent){
        _withAdjacent = withAdjacent;
    }
    public int getRows(){
        return _rows;
    }
    public void setRows(int rows){
        _rows = rows;
    }
    public int getCols(){
        return _cols;
    }
    public void setCols(int cols){
        _cols = cols;
    }
}
