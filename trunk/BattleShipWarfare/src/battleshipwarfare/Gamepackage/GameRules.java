package battleshipwarfare.Gamepackage;

import battleshipwarfare.Elementspackage.ElementType;
import java.util.Hashtable;

public class GameRules {

    private static final int DefaultNrOfPlayers = 2;
    private static final boolean DefaultWithAdjacent = true;
    
    private int _nrOfPlayers;
    private boolean _withAdjacent;
    private Hashtable<ElementType, Integer> _nrOfBoats;
    
    public GameRules(){
        //this(DefaultNrOfPlayers);
        _nrOfPlayers = DefaultNrOfPlayers;
        _withAdjacent = DefaultWithAdjacent;
        setDefaultNrOfBoats();
    }
//    public GameRules(int nrOfPlayers){
//        _nrOfPlayers = nrOfPlayers;
//        setDefaultNrOfBoats();
//    }
//    public GameRules(Hashtable<ElementType, Integer> nrOfBoats){
//        this(DefaultNrOfPlayers, nrOfBoats);
//    }
    public GameRules(int nrOfPlayers, Hashtable<ElementType, Integer> nrOfBoats, boolean withAdjacent){
        _nrOfPlayers = nrOfPlayers;
        _nrOfBoats = nrOfBoats;
        _withAdjacent = withAdjacent;
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

    public int getNrOfPlayers(){
        return _nrOfPlayers;
    }
    public Hashtable<ElementType, Integer> getNrOfBoats(){
        return _nrOfBoats;
    }
    public boolean getWithAdjacent(){
        return _withAdjacent;
    }
}
