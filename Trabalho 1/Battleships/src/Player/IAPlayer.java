package Player;

import Elements.Element;
import Elements.ElementStatus;
import Elements.ElementType;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import Engine.IConstants;

/**
 * Classe que representa o jogador "inteligente"
 * @author RNR
 */
public class IAPlayer implements IPlayer {
    ArrayList<Point> availMoves;
    ArrayList<Point> planedMoves;
    Point lastPlay; // Auxiliar para no caso de ter um hit tentar calcular direcções
    Point lastHit; // Pode ser util para calcular direcções

    /**
     * Constructor de IAPLayer
     */
    public IAPlayer() {
	initAI();
    }

    /**
     * Inicializa o movimentos disponíveis
     */
    private void initAI() {
	availMoves = new ArrayList<Point>();
	planedMoves = new ArrayList<Point>();
	for(int x=0;x<IConstants.BOUNDS.x;x++) {
	    for(int y=0;y<IConstants.BOUNDS.y;y++) {
		availMoves.add(new Point(x, y));
	    }
	}
    }

    /**
     * Retorna um ponto aleatório da lista passada como parâmetro
     * @param list ArrayList<Point> Lista <em>source</em> de pontos
     * @return
     */
    private Point getRandomPointOfList(ArrayList<Point> list) {
	Random rnd = new Random();
	int idx = rnd.nextInt(list.size());
	rnd = null;
        return list.get(idx);
    }

    /**
     * Gera os pontos possíveis da previsão da localização
     * do elemento de tipo <em>t</em>
     * @param t Tipo de Elemento
     */
    private void planMoves(ElementType t) {
	if (planedMoves.isEmpty()) {
	    // gera os pontos para o tipo t
	}
	else {
	    // tenta calcular a direcção
	    // Se sucesso
	    //   elimina pontos não pertencentes à direcção
	}
    }

    /**
     * Gera aleatoriamente uma localização e direcção para
     * um elemento
     * @param elem Tipo do elemento
     * @return Point[] Array de âncora e Direcção
     */
    public Point[] getElement(ElementType elem) {
	Point[] pos = new Point[2];
	Random rnd = new Random();
	pos[pos.length] = new Point(rnd.nextInt(IConstants.BOUNDS.x), rnd.nextInt(IConstants.BOUNDS.y));
	pos[pos.length] = DirectionDescriptor(rnd.nextInt(4)+1);
        return pos;
    }

    /**
     * Retorna um Poit correspodente a uma direcção
     * @param direction Valor int ed direcção
     * @return Point Direcção
     */
    private Point DirectionDescriptor(int direction) {
	switch (direction) {
	    case 1:
		return IConstants.NORTH;
	    case 2:
		return IConstants.SOUTH;
	    case 3:
		return IConstants.EAST;
	    case 4:
		return IConstants.WEST;
	}
	return null;
    }

    public Point play() {
	Point next = null;
	if (!planedMoves.isEmpty()) {
	    next = getRandomPointOfList(planedMoves);
	    planedMoves.remove(next);
	}
	else {
	    next = getRandomPointOfList(availMoves);
	}
	availMoves.remove(next);
	lastPlay = next;
        return next;
    }

    public void draw() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Notificação lançada quando existe hit num navio
     * @param shot Elemento acertado
     */
    public void notifyHit(Element shot) {
	if (shot.getStatus() == ElementStatus.SUNK) {
	    planedMoves = new ArrayList<Point>();
	    lastHit = null;
	    lastPlay = null;
	    return;
	}
	planMoves(shot.getType());
	throw new UnsupportedOperationException("Not supported yet.");
    }
}