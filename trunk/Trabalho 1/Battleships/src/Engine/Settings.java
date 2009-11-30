package Engine;

import Elements.IElement;
import java.awt.Point;
import java.util.Hashtable;
import java.util.Iterator;

public class Settings {


    public static Hashtable<ElementType,Integer> getElemRules()
    {
        Hashtable<ElementType,Integer> elemRules = new Hashtable<ElementType, Integer>();
        elemRules.put(ElementType.SUBMARINE, 2);
        elemRules.put(ElementType.PATROL_BOAT, 2);
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
     * Invalid, Ready, Started e Ended.
     */
    public enum GameStatus {
    Invalid, Ready, Started, Ended;
        private static final long serialVersionUID = 1L;
    }
    /**
     * Enumerado representativo dos possíveis estados de um elemento do jogo
     * Alive, Hitted e Sunk.
     */
    public enum ElementStatus {
    ALIVE, HITTED, SUNK;
        private static final long serialVersionUID = 1L;
    }
    /**
     * Enumerado representativo dos tipos de Element e seu tamanho
     * - Water (0 canos)
     * - Submarine (1 cano)
     * - Patrol_Boat (2 canos)
     * - Destroyer (3 canos)
     * - Battleship (4 canos)
     * - Aircraft (5 canos em T)
     */
    public enum ElementType {
    WATER, SUBMARINE , PATROL_BOAT, DESTROYER, BATTLESHIP, AIRCRAFT;
        private static final long serialVersionUID = 1L;
    }
}
