package battleshipwarfare.Gamepackage;

import battleshipwarfare.GameRules;
import battleshipwarfare.Boardpackage.Point;
import battleshipwarfare.Elementspackage.IElement;
import battleshipwarfare.PlayerPackage.IAPlayer;
import battleshipwarfare.PlayerPackage.IPlayer;
import battleshipwarfare.PlayerPackage.PlayerType;
import java.util.Random;

public class Game {

    private static int NUM_PLAYERS = 2;

    private GameStatus _gameStatus;    
    private GamePlayer[] _gamePlayers;    
    private int _currentPlayer = 0;

    public Game(){
        _gamePlayers = new GamePlayer[NUM_PLAYERS];
        _gameStatus = GameStatus.WAITING_FOR_PLAYER;
    }   
    private void buildBoards(){
        System.out.println("Starting to build the Board Games...");

        for(int i = 0; i < _gamePlayers.length; i++)
            _gamePlayers[i].buildBoard();

        _gameStatus = GameStatus.READY;
    }
    private void play(){
        Point p;
        _gameStatus = GameStatus.RUNNING;
        setCurrentPlayer();
        int other = (_currentPlayer + 1) % _gamePlayers.length;
        while(_gameStatus != GameStatus.ENDED){

            //Get a point from current player.
            p = _gamePlayers[_currentPlayer].Play();

            //Get the element in the other player corresponding the point
            //taken from cuerrent player
            IElement elem = _gamePlayers[other].Hit(p);
            
            //Notifies the current player of what did he hit
            _gamePlayers[_currentPlayer].notifyHit(elem.getType(), elem.getStatus());

            //if hitted player has no more boats, game over
            if(_gamePlayers[other].getStatus() == GamePlayerStatus.DEAD)
                _gameStatus = GameStatus.ENDED;

            //if current Player is human, print the boards
            if(_gamePlayers[_currentPlayer].getPlayer().getPlayerType()== PlayerType.HUMAN){
                _gamePlayers[_currentPlayer].drawInConsole(true);
                _gamePlayers[other].drawInConsole(false);
            }

            if(_gameStatus != GameStatus.ENDED){
                //switch players;
                int tmp = _currentPlayer;
                _currentPlayer = other;
                other = tmp;
            }
        }
    }
    private void setCurrentPlayer(){
        Random rnd = new Random();
        _currentPlayer = rnd.nextInt(_gamePlayers.length);
        return;
    }

    public boolean addPlayer(IPlayer player){
        if(_gameStatus != GameStatus.WAITING_FOR_PLAYER)
            return false;
        //Adding IAPlayer
        IPlayer iaPlayer = new IAPlayer();
        _gamePlayers[0] = new GamePlayer(iaPlayer);
        //Adding Human Player
        _gamePlayers[1] = new GamePlayer(player);

        _gameStatus = GameStatus.WAITING_FOR_BOATS;
        return true;
    }
    public void Start(){
        if(_gameStatus != GameStatus.WAITING_FOR_BOATS){
            System.out.println("You must add a player!!");
            return;
        }
        buildBoards();
        play();
    }
    public GamePlayer getWinner(){
        return _gamePlayers[_currentPlayer];
    }
    public void restart(){
        _gameStatus = GameStatus.WAITING_FOR_BOATS;
        buildBoards();
    }
}