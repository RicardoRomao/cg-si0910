package Elements;

import java.awt.Point;
import java.util.Collection;
import Engine.Settings.ElementStatus;
import Engine.Settings.ElementType;

    /**
     * Interface IElement, representação do contrato de um Elemento
     * para com o jogo.<br>
     * @author RNR
     */
public interface IElement {

    /**
     * Desenha o elemento "camuflado"
     */
    void draw();

    /**
     * Desenha o elemento atingido
     */
    void drawDamage();

    /**
     * Devolve os pontos ocupados pelo Element
     *
     * @return Collecion<Point> Colecção de pontos ocupados
     */
    Collection<Point> getPoints();

    /**
     * Devolve os pontos ocupados pelo Element mais os pontos adjacentes
     * 
     * @return Collecion<Point> Colecção de pontos ocupados
     */
    Collection<Point> getPointsWithAdjacent();

    /**
     * Representa a acção de acertar no Element
     */
    void hit();

    /**
     * Devolve o tipo do Element
     *
     *@return ElementType Tipo de Element
     */
    ElementType getType();

    /**
     * Devolve o estado do elemento
     *
     * @return ElementStatus Estado de Element
     */
    ElementStatus getStatus();
}
