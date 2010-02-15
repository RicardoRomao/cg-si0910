package battleshipwarfare.Gamepackage;

import battleshipwarfare.Boardpackage.Point;
import battleshipwarfare.Elementspackage.IElement;
import battleshipwarfare.PlayerPackage.IPlayer;
import battleshipwarfare.PlayerPackage.PlayerType;

public class Game {

    private GameStatus _gameStatus;    
    private GamePlayer[] _gamePlayers;
    private GameRules _rules;
    private int _activePlayers;//??
    private int _currentPlayer = 0;

    public Game(){
        this(new GameRules());
    }
    public Game(GameRules rules){
        _gamePlayers = new GamePlayer[rules.getNrOfPlayers()];
        _gameStatus = GameStatus.WAITING_FOR_PLAYERS;
        _rules = rules;
        _activePlayers = _currentPlayer = 0;
    }
    public boolean addPlayer(IPlayer player){
        if(_gameStatus != GameStatus.WAITING_FOR_PLAYERS)
            return false;
        if(_activePlayers > 0 &&
                _gamePlayers[_activePlayers - 1].getPlayer().getPlayerType() == player.getPlayerType()){
            return false;
        }
        _gamePlayers[_activePlayers++] = new GamePlayer(player);

        if(_activePlayers == _rules.getNrOfPlayers())
            _gameStatus = GameStatus.WAITING_FOR_BOATS;
        return true;
    }

    public void buildBoards(){
        if(_gameStatus != GameStatus.WAITING_FOR_BOATS){
            System.out.println("Game Not Waiting for boats!");
            return;
        }
        System.out.println("Starting to build the Board Games...");

        for(int i = 0; i < _rules.getNrOfPlayers(); i++)
            _gamePlayers[i].buildBoard(_rules);

        _gameStatus = GameStatus.READY;
    }
    public void play(){
        Point p;
        _gameStatus = GameStatus.RUNNING;
        int other = _currentPlayer + 1 % _rules.getNrOfPlayers();
        while(_gameStatus != GameStatus.ENDED){
            if(_gamePlayers[_currentPlayer].getPlayer().getPlayerType()== PlayerType.HUMAN){
                _gamePlayers[_currentPlayer].drawInConsole(true);
                _gamePlayers[other].drawInConsole(false);
            }

            p = _gamePlayers[_currentPlayer].Play();
            IElement elem = _gamePlayers[other].Hit(p);
            if(_gamePlayers[other].getStatus() != GamePlayerStatus.DEAD){
                _gamePlayers[_currentPlayer].notifyHit(elem.getType(), elem.getStatus());
                int tmp = _currentPlayer;
                _currentPlayer = other;
                other = tmp;
            }
            else
                _gameStatus = GameStatus.ENDED;
        }
    }
    public GamePlayer getWinner(){
        return _gamePlayers[_currentPlayer];
    }
    public void restart(){
        _gameStatus = GameStatus.WAITING_FOR_BOATS;
        buildBoards();
    }
}