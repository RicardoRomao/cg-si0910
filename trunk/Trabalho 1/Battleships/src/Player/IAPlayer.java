package Player;

import Elements.Element;
import Elements.ElementType;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 * Classe que representa o jogador "inteligente"
 * @author RNR
 */
public class IAPlayer implements IPlayer {
    ArrayList<Point> availMoves;
    ArrayList<Point> planedMoves;
    Point lastPlay; // Auxiliar para no caso de ter um hit tentar calcular direcções
    Point lastHit; //
    Point boardSize;

    /**
     * Constructor de IAPLayer
     * @param boardSize Tamanho da board
     */
    public IAPlayer(Point boardSize) {
	this.boardSize = boardSize;
	initAI();
    }

    /**
     * Inicializa o movimentos disponíveis
     */
    private void initAI() {
	for(int x=0;x<boardSize.x;x++) {
	    for(int y=0;y<boardSize.y;y++) {
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

    private void planMoves(ElementType t) {
		// Se planedMoves estiver vazio
		//   gera os pontos para o tipo t
		// Sen�o
		//   tenta calcular a direc��o
		//   Se sucesso
		//     elimina pontos n�o pertencentes � direc��o
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Point[] getElement(ElementType elem) {
        throw new UnsupportedOperationException("Not supported yet.");
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

    public void notifyHit(Element shot) {
	throw new UnsupportedOperationException("Not supported yet.");
    }
}