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
import battleshipwarfare.PlayerPackage.PlayerType;

/**
 * Class that represents a BattleShipWarfare Player
 * @author RNR
 */
public class GamePlayer {

    private IPlayer _player;
    private IBoard _board;
    private int _alivePoints;
    private GamePlayerStatus _status;

    /**
     * Constructs a GamePlayer with the specified player.
     * @param player
     * The player associated with this game player.
     */
    public GamePlayer(IPlayer player){
        _player = player;        
        _board = new Board(GameRules.getCurrentRules().getRows(), GameRules.getCurrentRules().getCols());
        _status = GamePlayerStatus.NOTREADY;
    }
    /**
     * Gets the board of this player
     * @return
     * The current player board
     *
     */
    public IBoard getBoard(){
        return _board;
    }
    /**
     * Gets the amount of living boat points. The unhitted boat cells.
     * @return int The living units
     */
    public int getAlivePoints(){
        return _alivePoints;
    }

    /**
     * Fills the empty spaces of this player board with water elements.
     */
    private void fillWater(){
        for(int i = 0; i < _board.getEndPoint().getX() + 1; i++){
            for(int j = 0; j < _board.getEndPoint().getY() + 1; j++){
                _board.addElement(new WaterElement(new Point(i, j),
                        _player.getPlayerType()==PlayerType.HUMAN?true:false), false);
            }
        }
    }

    /**
     * Gets the player associated with this Game Player.
     * @return
     * The player associated with this game player.
     */
    public IPlayer getPlayer(){
        return _player;
    }
    /**
     * Gets the current status of this player.
     * @return
     * The player status.
     */
    public GamePlayerStatus getStatus(){
        return _status;
    }

    /**
     * Constructs the current player board.
     */
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
    /**
     * The player play.
     * @return
     * A point representing the player play
     */
    public Point Play(){
        return _player.Play();
    }

    /**
     * The current player is hitted in parameter Point.
     * It returns the element contained in his board in the corresponding point.
     * @param p The point hitted.
     * @return IElement The element present in the specified point.
     */
    public IElement Hit(Point p){
        IElement hit = _board.shoot(p);

        if(hit.getClass() != WaterElement.class && --_alivePoints == 0)
            _status = GamePlayerStatus.DEAD;

        return hit;
    }
    /**
     * Notifies the current player of what he hitted and what was the resulting
     * status of the hitted element
     * @param type
     * The hitted element type
     * @param status
     * The hitted element resulting status
     */
    public void notifyHit(ElementType type, ElementStatus status){
        if(_player.getClass() == IAPlayer.class)
            _player.notifyHit(type, status);
    }
    
    /**
     * Draws a board in the console.
     * @param own
     * Determines if the board to be drawn is of the human or the IA player board.
     */
    public void drawInConsole(boolean own){
        _board.drawInConsole(own);
    }
}
