package Elements;

import java.awt.Point;
import java.util.Collection;
import Engine.Settings.ElementStatus;
import Engine.Settings.ElementType;

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
     */
    Collection<Point> getPoints();
    /**
     * Devolve os pontos ocupados pelo Element mais os pontos adjacentes
     */
    Collection<Point> getPointsWithAdjacent();
    /**
     * Representa a acção de acertar no Element
     */
    void hit();
    
    ElementType getType();

    ElementStatus getStatus();
}
