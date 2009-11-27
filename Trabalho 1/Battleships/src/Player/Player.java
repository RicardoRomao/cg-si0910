package Player;

import Elements.Element;
import Elements.ElementType;
import java.awt.Point;
import java.util.Scanner;

public class Player implements IPlayer {
    String _name;

    public Point[] getElement(ElementType elem)
    {
	Point[] pos = new Point[2];
	System.out.println("Posicione o seu " + TypeDescriptor(elem));
        Point p = new Point();
	int res = 0;
        Scanner in = new Scanner(System.in);
        System.out.print("\nCoordenada x: "); p.x = in.nextInt();
        System.out.print("\nCoordenada y: "); p.y = in.nextInt();
	pos[pos.length] = p;
	do {
	    System.out.println("  1 - Norte");
	    System.out.println("  2 - Sul");
	    System.out.println("  3 - Este");
	    System.out.println("  4 - Oeste");
	    System.out.print("Escolha uma direcção:"); res = in.nextInt();
	} while ((res < 1) || (res > 4));
        return pos;
    }

    /**
     * Retorn uma representação literal de um tipo de
     * navio
     * @param type Tipo de navio
     * @return String tipo literal de navio
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
        p.x = in.nextInt();
        System.out.print("\nCoordenada y: ");
        p.y = in.nextInt();
	return p;
    }

    public void draw() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void notifyHit(Element shot) {
	throw new UnsupportedOperationException("Not supported yet.");
    }
}