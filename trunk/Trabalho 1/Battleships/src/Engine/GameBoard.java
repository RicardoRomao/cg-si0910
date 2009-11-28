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
public class GameBoard {

// Atributos
    /**
     * Coordenadas de fim do tabuleiro.<br>
     */
    private Point _end;
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

// Construtor
    /**
     * Construtor que recebe o número de linhas e colunas do tabuleiro,
     *  traduzindo-se no ponto de fim do mesmo.<br>
     *
     * @param lines Número de linhas do tabuleiro.
     * @param cols Número de colunas do tabuleiro.
     */
    public GameBoard(Point bounds) {
        _board = new Hashtable<Point, IElement>();
        _receivedShots = new Hashtable<Point, IElement>();
        _end = bounds;
    }

// Métodos Privados
    /**
     * Verifica se o ponto está dentro dos limites do tabuleiro.
     *
     * @param p Ponto sobre o qual se vai fazer a verificação.
     * @return True caso o ponto esteja dentro dos limites.
     */
    private boolean isInBounds(Point p) {
        return (p.x <= _end.x && p.y <= _end.y && p.x >= 0 && p.y >= 0);
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
            Point p = it.next();
            if (_board.contains(p) || !isInBounds(p)) {
                return false;
            }
        }
        return true;
    }

// Métodos Públicos
    /**
     * Associa uma jogada ao tabuleiro de jogo.<br>
     * Retorna o elemento que se encontra no ponto passado como parâmetro ou,
     *  caso não exista nenhum nessa localização, retorna água.<br>
     * Em qualquer um dos casos a jogada é guardada e, no caso de se ter acertado
     *  num elemento, esta posição é transitada para a estrutura _receivedShots.<br>
     *
     * @param p Ponto onde deverá ser feita a jogada.
     * @return IElement Elemento alvo da jogada ou null, caso o ponto não esteja nos
     *          limites do tabuleiro
     */
    public IElement shoot(Point p) {
        if (!isInBounds(p)) {
            return null;
        }
        IElement elem = _board.get(p);
        if (elem == null) {
            _receivedShots.put(p, _water);
            return _water;
        }
        elem.hit();
        _receivedShots.put(p, _board.remove(p));
        return elem;
    }

    /**
     * Verifica se existem mais jogadas disponíveis no tabuleiro.<br>
     * Esta resposta passa por verificar se existem elementos em jogo.<br>
     *
     * @return True caso existam elementos em jogo no tabuleiro.
     */
    public boolean hasMoreMoves() {
        return (!_board.isEmpty());
    }

    /**
     * Adiciona um IElement ao tabuleiro de jogo.<br>
     *
     * @param e Elemento que se pretende ao tabuleiro de jogo.
     * @return boolean True caso o elemento tenha sido adicionado.
     */
    public boolean addElement(IElement e) {
        if (isPlaceable(e)) {
            Collection<Point> elemPts = e.getPoints();
            Iterator<Point> it = elemPts.iterator();
            while (it.hasNext()) {
                _board.put(it.next(), e);
            }
            return true;
        }
        return false;
    }

    /**
     * Desenha o tabuleiro de jogo.
     */
    public void draw(boolean drawAll) {
        //for (int i = 0; i <= _end.x; i++) {
        //    System.out.print("O");
        //}
        System.out.print("\n");
        for (int y = 0; y <= _end.y; y++) {
            //System.out.print("O");
            for (int x = 0; x <= _end.x; x++) {
                Point temp = new Point(x, y);
                if (drawAll & _board.containsKey(temp)) {
                    _board.get(temp).draw();
                } else if (_receivedShots.containsKey(temp)) {
                    _receivedShots.get(temp).drawDamage();
                } else {
                    _water.drawDamage();
                }
            }
            System.out.print("\n");
        }
        //for (int i = 0; i <= _end.x; i++) {
        //    System.out.print("O");
        //}
        System.out.println("\n");
    }
}
