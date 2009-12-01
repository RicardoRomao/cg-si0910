package Player;

import Elements.IElement;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import Engine.Settings;
import Engine.Settings.ElementStatus;
import Engine.Settings.ElementType;
import java.util.Iterator;

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
	for(int x=0;x<Settings.BOUNDS.x;x++) {
	    for(int y=0;y<Settings.BOUNDS.y;y++) {
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
     * Verfica de um determinado Ponto está dentro 
     * dos limites da arena.<br />
     * Auxiliar na previsão da localização de um navio
     * @param p Ponto a verficar
     * @return <code>True</code> se o Ponto está na arena, 
     * <code>False</code> se o contrário
     */
    private boolean isInBounds(Point p) {
        if (((p.x >= 0) && (p.x < Settings.BOUNDS.x)) &&
            ((p.y >= 0) && (p.y < Settings.BOUNDS.y))) {
            return true;
        }
        return false;
    }

    /**
     * Gera os pontos possíveis da previsão da localização
     * do elemento de tipo <em>t</em>
     * @param t Tipo de Elemento
     */
    private void planMoves(ElementType t) {
        int limit = t.ordinal() - 1;
        int init;
        Point gen;

	if (planedMoves.isEmpty()) {
            if (t == ElementType.AIRCRAFT) {
                limit /= 2;
                init = -(limit);
                int offset;
                for(int x=init;x<=limit;x++) {
                    offset = ((x==init) || x==limit)?1:0;
                    for(int y=(init+offset);y<=(limit-offset);y++) {
                        gen = new Point(lastPlay.x + x, lastPlay.y + y);
                        if (isInBounds(gen) && !gen.equals(lastPlay)) {
                            planedMoves.add(gen);
                        } // if
                    } // for
                } // for
            } // if
            else {
                init = -limit;
                for(int n=init;n<=limit;n++) {
                    gen = new Point(lastPlay.x + n, lastPlay.y);
                    if (isInBounds(gen) && !gen.equals(lastPlay)) {
                        planedMoves.add(gen);
                    } // if
                    gen = new Point(lastPlay.x, lastPlay.y + n);
                    if (isInBounds(gen) && !gen.equals(lastPlay)) {
                        planedMoves.add(gen);
                    } // if
                } // for
            } // else
	} // if
	else {
            // Vamos eliminar os pontos em excesso
            Point dir = new Point(lastHit.x - lastPlay.x, lastHit.y - lastPlay.y);
	    Point rem;
            if (t == ElementType.AIRCRAFT) {
                // AIRCRAFT
                limit /= 2;
                init = -(limit);
                int refX, refY;
                if (dir.x < 0) {
                    refX = Math.min(lastHit.x, lastPlay.x - limit);
                }
                else {
                    refX = Math.max(lastHit.x, lastPlay.x + limit);
                }
                if (dir.y < 0) {
                    refY = Math.min(lastHit.y, lastPlay.y - limit);
                }
                else {
                    refY = Math.max(lastHit.y, lastPlay.y + limit);
                }
		Iterator<Point> iter = planedMoves.iterator();
		while (iter.hasNext()) {
		    rem = iter.next();
                    if (dir.x < 0 && dir.y < 0) {
			if (rem.x < refX && rem.y < refY) {
			    iter.remove();
			}
                    }
                    else if (dir.x < 0 && dir.y > 0) {
			if (rem.x < refX && rem.y > refY) {
			    iter.remove();
			}
                    }
                    else if (dir.x > 0 && dir.y < 0) {
			if (rem.x > refX && rem.y < refY) {
			    iter.remove();
			}
                    }
                    else {
			if (rem.x > refX && rem.y > refY) {
			    iter.remove();
			}
                    }
		} // while
            } // if
            else {
                boolean vertical = false;
                init = -limit;
                vertical = (Math.abs(dir.x) == 0);
		int offset = 0;
                if (vertical) {
                    // Eliminar todos os pontos desnecessários
		    offset = -(dir.y / Math.abs(dir.y));
                    for(int n=init;n<=limit;n++) {
                        gen = new Point(lastHit.x + n, lastHit.y);
                        planedMoves.remove(gen);
                        if (dir.y != 0) {
                            gen = new Point(lastHit.x, lastHit.y + dir.y - offset);
                            dir.translate(0, offset);
                            planedMoves.remove(gen);
                        }
                    } // for
                } // if
                else {
                    // Eliminar todos os pontos desnecessários
		    offset = -(dir.x / Math.abs(dir.x));
                    for(int n=init;n<=limit;n++) {
                        gen = new Point(lastHit.x, lastHit.y + n);
                        planedMoves.remove(gen);
                        if (dir.x != 0) {
                            gen = new Point(lastHit.x + dir.x - offset, lastHit.y);
                            dir.translate(offset, 0);
                            planedMoves.remove(gen);
                        }
                    } // for
                } // else
            } // else
	} // else
    } // function

    /**
     * Gera aleatoriamente uma localização e direcção para
     * um elemento
     * @param elem Tipo do elemento
     * @return Point[] Array de âncora e Direcção
     */
    public Point[] getElement(ElementType elem) {
	Point[] pos = new Point[2];
	Random rnd = new Random();
	pos[0] = new Point(rnd.nextInt(Settings.BOUNDS.x), rnd.nextInt(Settings.BOUNDS.y));
	pos[1] = DirectionDescriptor(rnd.nextInt(4)+1);
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
     * Simula uma jogada pelo IAPLayer
     * @return Point Localização da jogada no tabuleiro
     */
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
    public void notifyHit(IElement shot) {
        if (shot.getType() == ElementType.WATER) {
            return;
        }
	if (shot.getStatus() == ElementStatus.SUNK) {
	    planedMoves = new ArrayList<Point>();
	    lastHit = null;
	    lastPlay = null;
	    return;
	}
	planMoves(shot.getType());
        lastHit = lastPlay;
    }
}