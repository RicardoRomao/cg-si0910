package Engine;

import Elements.IElement;
import java.awt.Point;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * Classe que contém as definições do jogo.<br>
 * Guarda informação acerca das regras do jogo, limites, pontos cardeais
 * e enumerados de estado de jogo, estado dos elementos e tipos de elementos.<br>
 */
public class Settings {

    /**
     * Estrutura que guarda as regras do jogo, ou seja, que e quantos elementos
     * cada jogador deve ter no seu tabuleiro.<br>
     *
     * @return Hashtable<ElementType, Integer>
     * Regras que definem quais os Elements que devem ser colocados no tabuleiro
     * &nbsp;e quais as respectivas quantidades.
     */
    public static Hashtable<ElementType,Integer> getElemRules()
    {
        Hashtable<ElementType,Integer> elemRules = new Hashtable<ElementType, Integer>();
        elemRules.put(ElementType.SUBMARINE, 4);
        elemRules.put(ElementType.PATROL_BOAT, 3);
        elemRules.put(ElementType.DESTROYER, 2);
        elemRules.put(ElementType.BATTLESHIP, 1);
        elemRules.put(ElementType.AIRCRAFT, 1);
        return elemRules;
    }

    /**
     * Coordenadas de fim do tabuleiro.<br>
     */
    public static final Point BOUNDS = new Point(10,10);
    /**
     * Coordenada cardeal Norte.<br>
     */
    public static final Point NORTH = new Point(0,-1);
    /**
     * Coordenada cardeal Sul.<br>
     */
    public static final Point SOUTH = new Point(0,1);
    /**
     * Coordenada cardeal Este.<br>
     */
    public static final Point EAST = new Point(1,0);
    /**
     * Coordenada cardeal Oeste.<br>
     */
    public static final Point WEST = new Point(-1,0);

    /**
     * Verifica se o ponto está dentro dos limites do tabuleiro.
     *
     * @param p Ponto sobre o qual se vai fazer a verificação.
     * @return True caso o ponto esteja dentro dos limites.
     */
    public static boolean isInBounds(Point p) {
        return (p.x < BOUNDS.x && p.y < BOUNDS.y && p.x >= 0 && p.y >= 0);
    }
    /**
     * Verifica se todos os pontos de um Element estão dentro dos limites
     * do tabuleiro.
     *
     * @param elem Element sobre o qual se vai fazer a verificação.
     * @return True caso o Element esteja dentro dos limites.
     */
    public static boolean isInBounds(IElement elem) {
        Iterator<Point> it = elem.getPoints().iterator();
        while(it.hasNext()){
            Point p = it.next();
            if(!isInBounds(p))
                return false;
        }
        return true;
    }
    /**
     * Enumerado representativo dos possíveis estados do jogo
     */
    public enum GameStatus {

        /**
         * INVALID - Jogo ainda sem estruturas iniciadas.
         */
        INVALID,
        /**
         * READY - Jogo com estruturas iniciadas, pronto a jogar
         */
        READY,
        /**
         * STARTED - Jogo com batalha a decorrer.
         */
        STARTED,
        /**
         * ENDED - Jogo terminado.
         */
        ENDED;
        private static final long serialVersionUID = 1L;
    }
    /**
     * Enumerado representativo dos possíveis estados de um elemento do jogo.
     */
    public enum ElementStatus {

        /**
         * ALIVE - IElement que existe no tabuleiro de jogo.
         */
        ALIVE,
        /**
         * HITTED - IElement que já foi atingido.
         */
        HITTED,
        /**
         * SUNK - IElement que já foi afundado.
         */
        SUNK;
        private static final long serialVersionUID = 1L;
    }
    /**
     * Enumerado representativo dos tipos de Element e seu tamanho
     */
    public enum ElementType {

        /**
         * Water (0 canos)
         */
        WATER,
        /**
         * Submarine (1 cano)
         */
        SUBMARINE,
        /**
         * Patrol_Boat (2 canos)
         */
        PATROL_BOAT,
        /**
         * Destroyer (3 canos)
         */
        DESTROYER,
        /**
         * Battleship (4 canos)
         */
        BATTLESHIP,
        /**
         * Aircraft (5 canos em T)
         */
        AIRCRAFT;
        private static final long serialVersionUID = 1L;
    }
}
