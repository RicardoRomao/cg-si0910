package Engine;

import Elements.Element;
import Player.IPlayer;
import Elements.ElementType;
import Elements.IElement;
import Player.IAPlayer;
import Player.Player;
import java.awt.Point;
import java.util.Enumeration;
import java.util.Hashtable;

public class Game extends Constants {

    GameBoard[] _boards;
    IPlayer[] _players;
    int _currPlayer;
    GameStatus _status = GameStatus.Invalid;

    /**
     * Construtor sem parâmetros.
     */
    public Game() {
        _boards = new GameBoard[]{new GameBoard(BOUNDS), new GameBoard(BOUNDS)};
        _players = new IPlayer[2];
    }

    /**
     * Iniciação das estruturas de suporte ao jogo, nomeadamente, contentor de
     * &nbsp;players, contentor de boards, estado do jogo <i>(READY)</i> e
     * &nbsp;obtenção das regras relativas ao posicionamento de elementos.<br>
     */
    public void init() {

        Hashtable<ElementType, Integer> elemRules = Settings.getElemRules();
        for (int i = 0; i < 2; i++) {
            _players[i] = getPlayer(i);
            setPlayer(i, _players[i], elemRules);
        }


        _currPlayer = 0;
        _status = GameStatus.Ready;
    }

    /**
     * Calcula o próximo jogador a fazer uma jogada.
     */
    public void nextPlayer() {
        _currPlayer = (_currPlayer + 1) % NUM_PLAYERS;
    }

    /**
     * Informa qual o tabuleiro alvo do jogador actual.<br>
     * O tabuleiro alvo do jogador 0 é o 1 e do jogador 1 é o 0.<br>
     *
     * @return Id do tabuleiro alvo do jogador actual.
     */
    public int currPlayerTarget() {
        return (_currPlayer + 1) % NUM_PLAYERS;
    }

    /**
     * Responsável por solicitar ao jogador o posicionamento de elementos no tabuleiro
     * &nbsp;de jogo.<br>
     * A interacção com o jogador é feita através do método getElement da classe Player
     * &nbsp; que, recebendo o tipo de elemento, devolve informação acerca do ponto inicial
     * &nbsp; onde o pretende colocar, bem como a sua direcção.<br>
     *
     * @param id Id do jogador e do seu tabuleiro de jogo.
     * @param player Jogador a configurar
     * @param elemRules Regras relativas a quantidades e tipos de elementos a posicionar.
     */
    private void setPlayer(int id, IPlayer player, Hashtable<ElementType, Integer> elemRules) {
        Enumeration<ElementType> elems = elemRules.keys();
        while (elems.hasMoreElements()) {
            ElementType e = elems.nextElement();
            for (int i = 0; i < elemRules.get(e).intValue(); i++) {
                IElement newElem;
                do {
                    Point[] plResp = player.getElement(e);
                    newElem = new Element(e, plResp[0], plResp[1]);
                } while (!_boards[id].addElement(newElem));
                drawBoard();
            }
        }
    }

    /**
     * Retorna um jogador mediante o id recebido.<br>
     * Caso id = 0 retorna um Player, caso id = 1 retorna um IAPlayer.
     *
     * @param  id Id do jogador.
     */
    public IPlayer getPlayer(int id) {
        if (id == 0) {
            return new Player();
        }
        return new IAPlayer();
    }

    /**
     * No caso do jogador actual ser o jogador humano manda desenhar o seu
     * &nbsp;tabuleiro com todos os elementos visíveis, bem como, o tabuleiro do
     * &nbsp;jogador do computador, apenas com os "tiros" efectuados sobre o mesmo.
     */
    private void drawBoard() {
        if (_currPlayer == 0) {
            System.out.println("Human Player Board");
            _boards[0].draw(true);
            System.out.println("Computer's Board");
            _boards[1].draw(false);
        }
    }

    /**
     * Mostra informação acerca de quem ganhou o jogo e coloca o estado do jogo a ENDED.
     */
    private void gameOver() {
        if (!_boards[1].hasMoreMoves()) {
            System.out.println("Human player won!");
        } else {
            System.out.println("Computer player won!");
        }
        _status = GameStatus.Ended;
    }

    /**
     * Recolhe jogadas dos jogadores, informando-os do seu alvo.
     */
    public void play() {
        if (_status != GameStatus.Ready) {
            System.out.println("Game not ready! Make sure init occurred.");
            return;
        }
        _currPlayer = 0;
        while (_boards[0].hasMoreMoves() && _boards[1].hasMoreMoves()) {
            Point shot = _players[_currPlayer].play();
            IElement target = _boards[currPlayerTarget()].shoot(shot);
            _players[_currPlayer].notifyHit(target);
            drawBoard();
            nextPlayer();
        }
        gameOver();
    }
}
