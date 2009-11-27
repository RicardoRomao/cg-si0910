package Elements;
/**
 * Classe constructora de Elemento do jogo.
 * Qualquer barco é construído com recurso e esta classe
 */

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
/**
 *
 * @author RNR
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
     * @param type ElementType
     *      O Tipo de Elemento a construir.
     * @param anchor
     *      O ponto inicial do Elemento
     * @param direction
     *      A direcção do elemento
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
     * ElementType do Element
     */
    public ElementType getType(){
        return _type;
    }
    /**
     * Retorna o ElementStatus do Element
     * @return
     * ElementStatus do Element
     */
    public ElementStatus getStatus(){
        return _status;
    }
    /**
     * Método responsável por retornar os pontos ocupados pelo Element
     * @return
     * Retorna uma collection de Point
     */
    public Collection<Point> getPoints(){
        ArrayList retArr = new ArrayList();
        retArr.add(_anchor);

        if(_type != ElementType.AIRCRAFT){
            for(int i = 1; i < _type.ordinal();i++){
                retArr.add(new Point(_anchor.x + i*_direction.x, _anchor.y + i*_direction.y));
            }
        }else{
            if(_direction.y == 1){
                for(int i = 0; i< 2; i++){
                    retArr.add(new Point(_anchor.x + (i + 1), _anchor.y));
                    retArr.add(new Point(_anchor.x + 1, _anchor.y + (i + 1)));
                }
            }else if(_direction.y == -1){
                for(int i = 0; i< 2; i++){
                    retArr.add(new Point(_anchor.x + (i*(-1) - 1), _anchor.y));
                    retArr.add(new Point(_anchor.x - 1, _anchor.y + (i*(-1) - 1)));
                }
            }else if(_direction.x == 1){
                for(int i = 0; i< 2; i++){
                    retArr.add(new Point(_anchor.x + (i + 1), _anchor.y - 1));
                    retArr.add(new Point(_anchor.x, _anchor.y + (i*(-1) - 1)));
                }
            }else{
                for(int i = 0; i< 2; i++){
                    retArr.add(new Point(_anchor.x + (i*(-1) - 1), _anchor.y + 1));
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
        System.out.print(" ");
    }
    /**
     * Desenha um ponto atingido
     */
    public void drawDamage() {
        System.out.print(_type.ordinal());
    }
    /**
     * Método responsável pela definição do estado de Element(ElementStatus)
     * com base no incremento do número de vezes que o Element foi atingido
     * e o seu tamanho.
     */
    public void hit(){
        if(++_hitCount == _type.ordinal())
            _status = ElementStatus.SUNK;
        else
            _status = ElementStatus.HITTED;
    }
}