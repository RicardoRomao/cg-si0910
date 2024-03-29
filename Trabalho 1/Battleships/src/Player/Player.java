package Player;

import Engine.Settings.ElementType;
import Engine.Settings;
import Elements.IElement;
import java.awt.Point;
import java.util.Scanner;

/**
 * Classe que representa um jogador humano.
 */
public class Player implements IPlayer {

    /**
     * Nome do jogador.
     */
    String _name;

    public Point[] getElement(ElementType elem)
    {
	Point[] pos = new Point[2];
	System.out.println("Posicione o seu " + TypeDescriptor(elem));        
	pos[0] = chooseAnchor();
	pos[1] = DirectionDescriptor(chooseDirection());
        return pos;
    }

    /**
     * Retorna um Poit correspodente a uma direcção.
     *
     * @param direction Valor int ed direcção.
     * @return Point Direcção.
     */
    private Point DirectionDescriptor(int direction) {
	switch (direction) {
	    case 1:
		return Settings.NORTH;
	    case 2:
		return Settings.SOUTH;
	    case 3:
		return Settings.EAST;
	    case 4:
		return Settings.WEST;
	}
	return null;
    }

    /**
     * Promove a escolha de uma direcção.
     *
     * @return int Direcção escolhida.
     */
    private int chooseDirection() {
	int res = 0;
	Scanner in = new Scanner(System.in);
	do {
	    System.out.println("  1 - Norte");
	    System.out.println("  2 - Sul");
	    System.out.println("  3 - Este");
	    System.out.println("  4 - Oeste");
	    System.out.print("Escolha uma direcção:"); res = in.nextInt();
	} while ((res < 1) || (res > 4));
	return res;
    }

    /**
     * Promove a escolha de um Ponto por parte do
     * jogador.
     *
     * @return Point Ponto escolhido.
     */
    private Point chooseAnchor() {        
	Point p = new Point();
	Scanner in = new Scanner(System.in);        
        System.out.print("\nCoordenada x: "); p.x = in.nextInt() - 1;
        System.out.print("\nCoordenada y: "); p.y = in.next().toUpperCase().charAt(0) - 'A';
	return p;
    }

    /**
     * Retorna uma representação literal de um tipo de
     * navio.
     *
     * @param type Tipo de navio.
     * @return String Tipo literal de navio.
     */
    private String TypeDescriptor(ElementType type) {
	String elem = null;

	switch (type) {
	    case AIRCRAFT:
		elem = "porta-aviões";
		break;
	    case BATTLESHIP:
		elem = "navio de 4 canos";
		break;
	    case DESTROYER:
		elem = "navio de 3 canos";
		break;
	    case PATROL_BOAT:
		elem = "navio de 2 canos";
		break;
	    case SUBMARINE:
		elem = "submarino";
		break;
	}

	return elem;
    }

    public Point play() {
	System.out.println("Faça a sua jogada.");
        Point p = new Point();
        Scanner in = new Scanner(System.in);
        System.out.print("\nCoordenada x: ");
        p.x = in.nextInt() - 1;
        System.out.print("\nCoordenada y: ");
        p.y = in.next().charAt(0) - 'A';
	return p;
    }

    public void draw() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * O jogador humano não necessita da informação, como tal, não faz nada.

     * @param shot IElement onde se acertou
     */
    public void notifyHit(IElement shot) {
	return;
    }
}