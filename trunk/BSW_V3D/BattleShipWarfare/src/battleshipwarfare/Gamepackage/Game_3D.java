package battleshipwarfare.Gamepackage;

import battleshipwarfare.Boardpackage.Point;
import battleshipwarfare.Elementspackage.IElement;
import battleshipwarfare.PlayerPackage.HumanPlayer;
import battleshipwarfare.PlayerPackage.IAPlayer;

/**
 * Class that represents a BattleShipWarfare Game
 * @author RNR
 */
public class Game_3D {

    private GameStatus _gameStatus;    
    
    private GamePlayer _humanPlayer;
    private GamePlayer _iaPlayer;

    /**
     * Constructs and initializes a game object.
     */
    public Game_3D(){

        _humanPlayer = new GamePlayer(new HumanPlayer("Human Player"));
        _iaPlayer = new GamePlayer(new IAPlayer());

        _gameStatus = GameStatus.WAITING_FOR_BOATS;
    }

    /**
     * Gets the human player of the game
     * @return GamePlayer human player
     */
    public GamePlayer getHumanPlayer(){
        return _humanPlayer;
    }
    /**
     * Gets the IA player of the game
     * @return GamePlayer IA player
     */
    public GamePlayer getIAPlayer(){
        return _iaPlayer;
    }
    /**
     * Gets the game status
     * @return GameStatus Cuerrent status of the game
     */
    public GameStatus getStatus(){
        return _gameStatus;
    }

    private void buildBoards(){

        _humanPlayer.buildBoard();
        _iaPlayer.buildBoard();

        _gameStatus = GameStatus.READY;
    }
    private void playIA(){
        IElement elem = _humanPlayer.Hit(_iaPlayer.Play());
        if(_humanPlayer.getStatus() == GamePlayerStatus.DEAD){
            _gameStatus = GameStatus.ENDED;
            return;
        }
        _iaPlayer.notifyHit(elem.getType(), elem.getStatus());
    }

    /**
     * Sets the game ready to play
     */
    public void init(){
        if(_gameStatus == GameStatus.WAITING_FOR_BOATS){
            buildBoards();
            _gameStatus = GameStatus.RUNNING;
        }
    }
    /**
     * A human play by a click in the canvas
     * @param the point, in coordinates, that the human player clicked on
     */
    public void playHuman(Point p){
        _iaPlayer.Hit(p);
        if(_iaPlayer.getStatus() == GamePlayerStatus.DEAD){
            _gameStatus = GameStatus.ENDED;
            return;
        }
        playIA();
    }
 
    /**
     * Gets the winner player of the game.
     * @return GamePlayer The player who won the game.
     */
    public GamePlayer getWinner(){
        if(_gameStatus == GameStatus.ENDED)
            return _humanPlayer.getStatus() == GamePlayerStatus.ALIVE ? _humanPlayer : _iaPlayer;
        return null;
    }
    /**
     * Restarts the game.
     */
    public void restart(){
        buildBoards();
        _gameStatus = GameStatus.RUNNING;
    }
}