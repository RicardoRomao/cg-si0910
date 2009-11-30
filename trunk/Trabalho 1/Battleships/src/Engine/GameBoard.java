package Engine;

import Engine.Settings.ElementType;
import Engine.Settings.ElementStatus;
import Elements.IElement;
import Elements.Water;
import java.awt.Point;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * Classe que representa o tabuleiro de jogo.<br> 
 * Guarda informação acerca do número de elementos "alive" contidos no mesmo.<br>
 */
public class GameBoard {

// Atributos   
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
     * Construtor do tabuleiro de jogo.<br>
     * Inicia as estruturas de dados de suporte à gestão do tabuleiro.<br>
     */
    public GameBoard() {
        _board = new Hashtable<Point, IElement>();
        _receivedShots = new Hashtable<Point, IElement>();        
    }

// Métodos Privados
    /**
     * Verifica se um Elemento é posicionável no tabuleiro.<br>
     * Para tal, o Elemento terá que disponibilizar informação acerca de quais
     * os pontos que vai ocupar quando posicionado, bem como de quais os
     * seus pontos adjacentes.<br>
     *
     * @param e Elemento a verificar posicionamento.
     * @return boolean True caso seja possível posicionar o Elemento.
     */
    private boolean isPlaceable(IElement e) {
        Collection<Point> elemPts = e.getPointsWithAdjacent();
        Iterator<Point> it = elemPts.iterator();
        while (it.hasNext()) {
            Point p = it.next();
            if (_board.containsKey(p)) {
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
        if (!Settings.isInBounds(p)) {
            System.out.println("Coordenadas Inválidas!!");
            System.out.println("As coordenadas devem pertencer a" +
                    " (x=[1..10]; y=[A..J]).");
            return null;
        }
        IElement elem = _board.get(p);
        if (elem == null) {
            _receivedShots.put(p, _water);
            System.out.println("Acertou na água");
            return _water;
        }
        elem.hit();
        _receivedShots.put(p, _board.remove(p));
        System.out.println((elem.getStatus()==ElementStatus.SUNK?"Afundou um ":
            "Acertou num ") + elem.getType());
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
        if(!Settings.isInBounds(e)){
            System.out.println("Coordenadas Inválidas!!");
            System.out.println("Todos os pontos do elemento devem pertencer a" +
                    " (x=[1..10]; y=[A..J]).");
            return false;
        }
        if (isPlaceable(e)) {
            Collection<Point> elemPts = e.getPoints();
            Iterator<Point> it = elemPts.iterator();
            while (it.hasNext()) {
                _board.put(it.next(), e);
            }
            return true;
        }
        System.out.println("Conflicto com outro elemento já posicionado.");
        return false;
    }

    /**
     * Desenha o tabuleiro de jogo.
     */    
    public void draw(boolean drawHuman) {
        System.out.print(" ");
        for(int i = 1; i <= 10; i++){
            System.out.print(i);
        }
        System.out.print("\n");
        for (int y = 0; y < Settings.BOUNDS.y; y++) {
            //Imprime a coluna das Letras
            System.out.print((char)('A' + y));
            for (int x = 0; x < Settings.BOUNDS.x; x++) {
                Point temp = new Point(x, y);
                if(drawHuman){
                    if(_board.containsKey(temp))
                    { _board.get(temp).draw();}
                    else if(_receivedShots.containsKey(temp))
                            { _receivedShots.get(temp).drawDamage();}
                    else{ _water.draw(); }
                }else {
                    if(_receivedShots.containsKey(temp)){
                        if(_receivedShots.get(temp).getType() == ElementType.WATER){
                            _receivedShots.get(temp).drawDamage();
                        }else
                        {
                            _receivedShots.get(temp).draw();
                        }
                    }else
                    { _water.draw();}
                }
            }
            System.out.print("\n");
        }        
        System.out.println("\n");        
    }
}
