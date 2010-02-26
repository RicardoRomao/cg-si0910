package battleshipwarfare.Gamepackage;

import battleshipwarfare.GameRules;
import battleshipwarfare.Boardpackage.Board;
import battleshipwarfare.Boardpackage.Point;
import battleshipwarfare.Boardpackage.IBoard;
import battleshipwarfare.Elementspackage.ElementStatus;
import battleshipwarfare.PlayerPackage.IPlayer;
import java.util.Enumeration;
import battleshipwarfare.Elementspackage.ElementType;
import battleshipwarfare.Elementspackage.IElement;
import battleshipwarfare.Elementspackage.WaterElement;
import battleshipwarfare.PlayerPackage.IAPlayer;

public class GamePlayer {

    private IPlayer _player;
    private IBoard _board;
    private int _alivePoints;
    private GamePlayerStatus _status;

    public GamePlayer(IPlayer player){
        _player = player;        
        _board = new Board(GameRules.getCurrentRules().getRows(), GameRules.getCurrentRules().getCols());
        _status = GamePlayerStatus.NOTREADY;
    }
    public IBoard getBoard(){
        return _board;
    }

    public void fillWater(){
        for(int i = 0; i < _board.getEndPoint().getX() + 1; i++){
            for(int j = 0; j < _board.getEndPoint().getY() + 1; j++){
                _board.addElement(new WaterElement(new Point(i, j)), false);
            }
        }
    }

    public IPlayer getPlayer(){
        return _player;
    }
    public GamePlayerStatus getStatus(){
        return _status;
    }

    @SuppressWarnings("empty-statement")
    public void buildBoard(){
        Enumeration<ElementType> elementsList = GameRules.getCurrentRules().getNrOfBoats().keys();
        while(elementsList.hasMoreElements()){
            ElementType type = elementsList.nextElement();
            for(int i = GameRules.getCurrentRules().getNrOfBoats().get(type); i > 0; i--){
                while(!_board.addElement(_player.getNewElement(type), GameRules.getCurrentRules().getWithAdjacent())){};
                _alivePoints += type.ordinal();
            }
        }
        fillWater();
        _status = GamePlayerStatus.ALIVE;
    }
    public Point Play(){
        return _player.Play();
    }

    public IElement Hit(Point p){
        IElement hit = _board.shoot(p);

        if(hit.getClass() != WaterElement.class && --_alivePoints == 0)
            _status = GamePlayerStatus.DEAD;

        return hit;
    }
    public void notifyHit(ElementType type, ElementStatus status){
        if(_player.getClass() == IAPlayer.class)
            _player.notifyHit(type, status);
    }
    
    public void drawInConsole(boolean own){
        _board.drawInConsole(own);
    }
}
