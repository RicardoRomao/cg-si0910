package Engine;

import Elements.IElement;
import Elements.Water;
import java.awt.Point;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * Classe que representa o tabuleiro de jogo.<br>
 * Contém informação acerca do ponto de fim, considerando-se sempre o ponto inicial (0,0).<br>
 * Guarda também informação acerca do número de elementos "alive" contidos no mesmo.<br>
 */
public abstract class GameBoard {

    /**
     * Coordenadas de fim do tabuleiro.<br>
     */
    Point _end;
    /**
     * Número de elementos "alive", i.e., elementos que ainda nao foram afundados.<br>
     */
    int _nElems;
    /**
     * Referência para um objecto do tipo Water, evitando assim instanciação
     *  de vários objectos deste tipo.<br>
     */
    private static final Water _water = new Water();
    /**
     * Estrutura que guarda estado de todos os Pontos em que já foram feitos disparos,
     *  bem como, as referencias para os objectos que ocupavam essas posições.<br>
     */
    private Hashtable<Point, IElement> _receivedShots;
    /**
     * Estrutura que guarda estado de todos os elementos em jogo no tabuleiro.<br>
     */
    private Hashtable<Point, IElement> _board;

    /**
     * Construtor que recebe o número de linhas e colunas do tabuleiro,
     *  traduzindo-se no ponto de fim do mesmo.<br>
     *
     * @param lines Número de linhas do tabuleiro.
     * @param cols Número de colunas do tabuleiro.
     */
    public GameBoard(int lines, int cols) {
        _board = new Hashtable<Point, IElement>();
        _receivedShots = new Hashtable<Point, IElement>();
        _nElems = 0;
        _end = new Point(cols - 1, lines - 1);
    }

    /**
     * Associa uma jogada ao tabuleiro de jogo.<br>
     * Retorna o elemento que se encontra no ponto passado como parâmetro.<br>
     *
     * @param p Ponto onde deverá ser feita a jogada.
     * @return IElement Elemento alvo da jogada.
     */
    private IElement shoot(Point p) {

        IElement elem = _board.get(p);
        if (elem == null) {
            return _water;
        }
        return elem;
    }

    /**
     * Verifica se um Elemento é posicionável no tabuleiro.<br>
     * Para tal, o Elemento terá que disponibilizar informação acerca de quais
     *  os pontos que vai ocupar quando posicionado.<br>
     *
     * @param e Elemento a verificar posicionamento.
     * @return boolean True caso seja possível posicionar o Elemento.
     */
    private boolean isPlaceable(IElement e) {
        Collection<Point> elemPts = e.getPoints();
        Iterator<Point> it = elemPts.iterator();
        while (it.hasNext()) {
            if (_board.contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Retorna informação acerca da existência de elementos não afundados
     *  no tabuleiro.<br>
     *
     * @return boolean True caso existam elementos não afundados.
     */
    private boolean isAlive() {
        return (_nElems == 0);
    }

    /**
     * Adiciona um IElement ao tabuleiro de jogo.<br>
     *
     * @param e Elemento que se pretende ao tabuleiro de jogo.
     * @return boolean True caso o elemento tenha sido adicionado.
     */
    public boolean addElement(IElement e) {

        //Verify how to pass callback to isPlaceable.

        if (isPlaceable(e)) {
            Collection<Point> elemPts = e.getPoints();
            Iterator<Point> it = elemPts.iterator();
            while (it.hasNext()) {
                _board.put(it.next(), e);
            }
            _nElems += 1;
            return true;
        }
        return false;
    }

    /**
     * Desenha o tabuleiro de jogo.
     */
    public void draw() {
        for (int i = 0; i < _end.x; i++) {
            System.out.println("O");
        }
        for (int x = 0; x < _end.x; x++) {
            System.out.println("O");
            for (int y = 0; y < _end.x; y++) {
                Point temp = new Point(x, y);
                if (_board.contains(temp)) {
                    _board.get(temp).draw();
                } else if (_receivedShots.contains(temp)) {
                    _receivedShots.get(temp).draw();
                } else {
                    _water.draw();
                }
            }
            System.out.println("O");
        }
        for (int i = 0; i < _end.x; i++) {
            System.out.println("O");
        }

    }
}
