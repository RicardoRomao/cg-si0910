package battleshipwarfare.PlayerPackage;

import battleshipwarfare.Boardpackage.Board;
import battleshipwarfare.Elementspackage.Element;
import battleshipwarfare.Elementspackage.ElementType;
import battleshipwarfare.Elementspackage.IElement;
import battleshipwarfare.Elementspackage.LineElement;
import battleshipwarfare.Boardpackage.IBoard;
import battleshipwarfare.Boardpackage.Point;
import battleshipwarfare.Elementspackage.AircraftElement;
import battleshipwarfare.Elementspackage.ElementStatus;
import java.util.ArrayList;
import java.util.Random;

public class IAPlayer implements IPlayer {

    String _playerName;
    PlayerType _playerType;
    IBoard _board;
    ArrayList<Point> availMoves;
    ArrayList<Point> planedMoves;
    Point lastPlay; // Auxiliar para no caso de ter um hit tentar calcular direcções
    Point lastHit;

    public IAPlayer() {
        initAI();
    }
    private void initAI() {
        _playerName = "IA Player";
        _playerType = PlayerType.IA;
	availMoves = new ArrayList<Point>();
	planedMoves = new ArrayList<Point>();
        _board = new Board();
	for(int x=0;x<_board.getEndPoint().getX();x++) {
	    for(int y=0;y<_board.getEndPoint().getY();y++) {
		availMoves.add(new Point(x, y));
	    }
	}
    }
    public String getName(){
        return _playerName;
    }
    public PlayerType getPlayerType(){
        return _playerType;
    }
    public IBoard getBoard(){
        return _board;
    }

    private Point getRandomPointOfList(ArrayList<Point> list) {
	Random rnd = new Random();
	int idx = rnd.nextInt(list.size());
	rnd = null;
        return list.get(idx);
    }

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
                        gen = new Point(lastPlay.getX() + x, lastPlay.getY() + y);
                        if (_board.isInBounds(gen) && !gen.equals(lastPlay)) {
                            planedMoves.add(gen);
                        } // if
                    } // for
                } // for
            } // if
            else {
                init = -limit;
                for(int n=init;n<=limit;n++) {
                    gen = new Point(lastPlay.getX() + n, lastPlay.getY());
                    if (_board.isInBounds(gen) && !gen.equals(lastPlay)) {
                        planedMoves.add(gen);
                    } // if
                    gen = new Point(lastPlay.getX(), lastPlay.getY() + n);
                    if (_board.isInBounds(gen) && !gen.equals(lastPlay)) {
                        planedMoves.add(gen);
                    } // if
                } // for
            } // else
	} // if
	else {
            // Vamos eliminar os pontos em excesso
            Point dir = new Point(lastHit.getX() - lastPlay.getX(), lastHit.getY() - lastPlay.getY());
            if (t == ElementType.AIRCRAFT) {
                // AIRCRAFT
                limit /= 2;
                init = -(limit);
                int refX, refY;
                if (dir.getX() < 0) {
                    refX = Math.min(lastHit.getX(), lastPlay.getX());
                }
                else {
                    refX = Math.max(lastHit.getX(), lastPlay.getX());
                }
                if (dir.getY() < 0) {
                    refY = Math.min(lastHit.getY(), lastPlay.getY());
                }
                else {
                    refY = Math.max(lastHit.getY(), lastPlay.getY());
                }
                for(int n=0;n<planedMoves.size();n++) {
                    if (dir.getX() < 0 && dir.getY() < 0) {
                    }
                    else if (dir.getX() < 0 && dir.getY() > 0) {
                    }
                    else if (dir.getX() > 0 && dir.getY() < 0) {
                    }
                    else {
                    }
                } // for
            } // if
            else {
                boolean vertical = false;
                init = -limit;
                vertical = (Math.abs(dir.getX()) == 0);
                if (vertical) {
                    // Eliminar todos os pontos desnecessários
                    for(int n=init;n<=limit;n++) {
                        gen = new Point(lastPlay.getX() + n, lastPlay.getY());
                        planedMoves.remove(gen);
                        if (dir.getY() != 0) {
                            gen = new Point(lastHit.getX(), lastHit.getY() + dir.getY());
                            dir.translate(0, -(dir.getY() / Math.abs(dir.getY())));
                            planedMoves.remove(gen);
                        }
                    } // for
                } // if
                else {
                    // Eliminar todos os pontos desnecessários
                    for(int n=init;n<=limit;n++) {
                        gen = new Point(lastPlay.getX(), lastPlay.getY() + n);
                        planedMoves.remove(gen);
                        if (dir.getX() != 0) {
                            gen = new Point(lastHit.getX() + dir.getX(), lastHit.getY());
                            dir.translate(-(dir.getX() / Math.abs(dir.getX())), 0);
                            planedMoves.remove(gen);
                        }
                    } // for
                } // else
            } // else
	} // else
    } // function

    public IElement getNewElement(ElementType type) {
        Random rnd = new Random();
	Point anchor = new Point(rnd.nextInt(_board.getEndPoint().getX()), rnd.nextInt(_board.getEndPoint().getY()));
	Point direction = IElement.cardinalPoints[(rnd.nextInt(4))];
        if(type == ElementType.AIRCRAFT){
            return new AircraftElement(anchor, direction);
        }else{
            return new LineElement(type, anchor, direction);
        }
    }    

    public Point Play() {
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
    public void notifyHit(ElementType type, ElementStatus status) {
        if (type == ElementType.WATER) {
            return;
        }
	if (status == ElementStatus.SUNK) {
	    planedMoves = new ArrayList<Point>();
	    lastHit = null;
	    lastPlay = null;
	    return;
	}
	planMoves(type);
        lastHit = lastPlay;
    }
}
