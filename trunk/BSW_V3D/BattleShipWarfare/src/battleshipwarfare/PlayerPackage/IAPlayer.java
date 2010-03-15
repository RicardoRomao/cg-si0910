package battleshipwarfare.PlayerPackage;

import battleshipwarfare.Elementspackage.ElementType;
import battleshipwarfare.Elementspackage.IElement;
import battleshipwarfare.Elementspackage.LineElement;
import battleshipwarfare.Boardpackage.Point;
import battleshipwarfare.Elementspackage.AircraftElement;
import battleshipwarfare.Elementspackage.ElementStatus;
import battleshipwarfare.GameRules;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class representing the computer player
 * @author RNR
 */
public class IAPlayer implements IPlayer {

    String _playerName;
    PlayerType _playerType;
    Point _endPoint;
    ArrayList<Point> _availMoves;
    ArrayList<Point> _planedMoves;
    Point _lastPlay; // Auxiliar para no caso de ter um hit tentar calcular direcções
    Point _lastHit;

    /**
     * Constructs a new player with IA
     */
    public IAPlayer() {
        _endPoint = new Point(GameRules.getCurrentRules().getRows() - 1, GameRules.getCurrentRules().getCols() - 1);
        initAI();
    }

    private void initAI() {
        _playerName = "IA Player";
        _playerType = PlayerType.IA;
        _planedMoves = new ArrayList<Point>();
        _availMoves = new ArrayList<Point>();
        for (int x = 0; x <= _endPoint.getX(); x++) {
            for (int y = 0; y <= _endPoint.getY(); y++) {
                _availMoves.add(new Point(x, y));
            }
        }
    }

    private Point getRandomPointOfList(ArrayList<Point> list) {
        Random rnd = new Random();
        int idx = rnd.nextInt(list.size());
        rnd = null;
        return list.get(idx);
    }

    private boolean isInBounds(Point p) {
        return p.getX() >= 0 && p.getY() >= 0 && p.getX() <= _endPoint.getX() && p.getY() <= _endPoint.getY();

    }

    private void planMoves(ElementType t) {
        int limit = t.ordinal() - 1;
        int init;
        Point gen;

        if (_planedMoves.isEmpty()) {
            if (t == ElementType.AIRCRAFT) {
                limit /= 2;
                init = -(limit);
                int offset;
                for (int x = init; x <= limit; x++) {
                    offset = ((x == init) || x == limit) ? 1 : 0;
                    for (int y = (init + offset); y <= (limit - offset); y++) {
                        gen = new Point(_lastPlay.getX() + x, _lastPlay.getY() + y);
                        if (isInBounds(gen) && !gen.equals(_lastPlay)) {
                            _planedMoves.add(gen);
                        } // if
                    } // for
                } // for
            } // if
            else {
                init = -limit;
                for (int n = init; n <= limit; n++) {
                    gen = new Point(_lastPlay.getX() + n, _lastPlay.getY());
                    if (isInBounds(gen) && !gen.equals(_lastPlay)) {
                        _planedMoves.add(gen);
                    } // if
                    gen = new Point(_lastPlay.getX(), _lastPlay.getY() + n);
                    if (isInBounds(gen) && !gen.equals(_lastPlay)) {
                        _planedMoves.add(gen);
                    } // if
                } // for
            } // else
        } // if
        else {
            // Vamos eliminar os pontos em excesso
            Point dir = new Point(_lastHit.getX() - _lastPlay.getX(), _lastHit.getY() - _lastPlay.getY());
            if (t == ElementType.AIRCRAFT) {
                // AIRCRAFT
                limit /= 2;
                init = -(limit);
                int refX, refY;
                if (dir.getX() < 0) {
                    refX = Math.min(_lastHit.getX(), _lastPlay.getX());
                } else {
                    refX = Math.max(_lastHit.getX(), _lastPlay.getX());
                }
                if (dir.getY() < 0) {
                    refY = Math.min(_lastHit.getY(), _lastPlay.getY());
                } else {
                    refY = Math.max(_lastHit.getY(), _lastPlay.getY());
                }
                for (int n = 0; n < _planedMoves.size(); n++) {
                    if (dir.getX() < 0 && dir.getY() < 0) {
                    } else if (dir.getX() < 0 && dir.getY() > 0) {
                    } else if (dir.getX() > 0 && dir.getY() < 0) {
                    } else {
                    }
                } // for
            } // if
            else {
                boolean vertical = false;
                init = -limit;
                vertical = (Math.abs(dir.getX()) == 0);
                if (vertical) {
                    // Eliminar todos os pontos desnecessários
                    for (int n = init; n <= limit; n++) {
                        gen = new Point(_lastPlay.getX() + n, _lastPlay.getY());
                        _planedMoves.remove(gen);
                        if (dir.getY() != 0) {
                            gen = new Point(_lastHit.getX(), _lastHit.getY() + dir.getY());
                            dir.translate(0, -(dir.getY() / Math.abs(dir.getY())));
                            _planedMoves.remove(gen);
                        }
                    } // for
                } // if
                else {
                    // Eliminar todos os pontos desnecessários
                    for (int n = init; n <= limit; n++) {
                        gen = new Point(_lastPlay.getX(), _lastPlay.getY() + n);
                        _planedMoves.remove(gen);
                        if (dir.getX() != 0) {
                            gen = new Point(_lastHit.getX() + dir.getX(), _lastHit.getY());
                            dir.translate(-(dir.getX() / Math.abs(dir.getX())), 0);
                            _planedMoves.remove(gen);
                        }
                    } // for
                } // else
            } // else
        } // else
    } // function

    public String getName() {
        return _playerName;
    }

    public PlayerType getPlayerType() {
        return _playerType;
    }

    public IElement getNewElement(ElementType type) {
        Random rnd = new Random();
        Point anchor = new Point(rnd.nextInt(_endPoint.getX()), rnd.nextInt(_endPoint.getY()));
        Point direction = IElement.cardinalPoints[(rnd.nextInt(4))];
        if (type == ElementType.AIRCRAFT) {
            return new AircraftElement(anchor, direction, false);
        } else {
            return new LineElement(type, anchor, direction, false);
        }
    }

    public Point Play() {
        Point next = null;
        if (!_planedMoves.isEmpty()) {
            next = getRandomPointOfList(_planedMoves);
            _planedMoves.remove(next);
        } else {
            next = getRandomPointOfList(_availMoves);
        }
        _availMoves.remove(next);
        _lastPlay = next;
        return next;
    }

    public void notifyHit(ElementType type, ElementStatus status) {
        if (type == ElementType.WATER) {
            return;
        }
        if (status == ElementStatus.SUNK) {
            _planedMoves = new ArrayList<Point>();
            _lastHit = null;
            _lastPlay = null;
            return;
        }
        planMoves(type);
        _lastHit = _lastPlay;
    }
}
