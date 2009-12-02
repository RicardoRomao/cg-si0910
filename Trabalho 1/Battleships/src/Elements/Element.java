package Elements;

import Engine.Settings;
import Engine.Settings.ElementType;
import Engine.Settings.ElementStatus;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Classe construtora de Elemento do jogo.
 * Qualquer barco é construído com recurso e esta classe.<br>
 */
public class Element implements IElement {

    ElementType _type;
    ElementStatus _status;
    Point _anchor;
    Point _direction;
    private int _hitCount;

    /**
     * Construtor de elementos do jogo a juntar ao tabuleiro
     *
     * @param type Tipo de Element a construir dado por ElementType.
     * @param anchor Ponto inicial de Element dado por Point.
     * @param direction Direcção de Element dada por Point.
     */
    public Element(ElementType type, Point anchor, Point direction) {
        _type = type;
        _anchor = anchor;
        _direction = direction;
        _status = ElementStatus.ALIVE;
    }

    /**
     * Retorna o ElementType do Element
     * @return
     * ElementType do IElement
     */
    public ElementType getType() {
        return _type;
    }

    /**
     * Retorna o ElementStatus do Element
     * @return
     * ElementStatus do IElement
     */
    public ElementStatus getStatus() {
        return _status;
    }
    /**
     * Método responsável por retornar os pontos ocupados por Element
     * juntamente com os seus adjacentes
     * @return
     * Retorna uma collection de Point
     */
    public Collection<Point> getPointsWithAdjacent(){
        Collection<Point> retPoints = new ArrayList<Point>();
        Collection<Point> elemPoints = getPoints();
        retPoints.addAll(elemPoints);
        Iterator<Point> it = elemPoints.iterator();
        while(it.hasNext()){
            retPoints.addAll(GetAdjacentCells(it.next()));
        }
        return retPoints;
    }
    /**
     * Método responsável por retornar os pontos adjacentes de Element
     * @return
     * Retorna uma collection de Point
     */
    private Collection<Point> GetAdjacentCells(Point p)
    {
        ArrayList retList = new ArrayList();
        for (int i = -1; i < 2; i++)
        {
            for (int j = -1; j < 2; j++)
            {
                if ((i != 0 || j != 0) && Settings.isInBounds(new Point(p.x + i, p.y + j)))
                {
                    retList.add(new Point(p.x + i, p.y + j));
                }
            }

        }
        return retList;
    }

    /**
     * Método responsável por retornar os pontos ocupados por Element
     * @return
     * Retorna uma collection de Point
     */
    public Collection<Point> getPoints() {
        ArrayList retArr = new ArrayList();
        retArr.add(_anchor);

        if (_type != ElementType.AIRCRAFT) {
            for (int i = 1; i < _type.ordinal(); i++) {
                retArr.add(new Point(_anchor.x + i * _direction.x, _anchor.y + i * _direction.y));
            }
        } else {
            if (_direction.y == 1) {
                for (int i = 0; i < 2; i++) {
                    retArr.add(new Point(_anchor.x + (i + 1), _anchor.y));
                    retArr.add(new Point(_anchor.x + 1, _anchor.y + (i + 1)));
                }
            } else if (_direction.y == -1) {
                for (int i = 0; i < 2; i++) {
                    retArr.add(new Point(_anchor.x + (i * (-1) - 1), _anchor.y));
                    retArr.add(new Point(_anchor.x - 1, _anchor.y + (i * (-1) - 1)));
                }
            } else if (_direction.x == 1) {
                for (int i = 0; i < 2; i++) {
                    retArr.add(new Point(_anchor.x + (i + 1), _anchor.y - 1));
                    retArr.add(new Point(_anchor.x, _anchor.y + (i * (-1) - 1)));
                }
            } else {
                for (int i = 0; i < 2; i++) {
                    retArr.add(new Point(_anchor.x + (i * (-1) - 1), _anchor.y + 1));
                    retArr.add(new Point(_anchor.x, _anchor.y + (i + 1)));
                }
            }
        }
        return retArr;
    }

    /**
     * Desenha um ponto por atingir
     */
    public void draw() {
        System.out.print(_type.ordinal());
    }

    /**
     * Desenha um ponto atingido
     */
    public void drawDamage() {
        System.out.print("o");
    }

    /**
     * Método a ser chamado em cada vez que se acerta no Element.
     * Responsável por definir o estado de Element(ElementStatus)
     * com base no incremento do número de vezes que foi atingido
     * e o seu tamanho.
     */
    public void hit() {
        if (++_hitCount == _type.ordinal()) {
            _status = ElementStatus.SUNK;
        } else {
            _status = ElementStatus.HITTED;
        }
    }
}
