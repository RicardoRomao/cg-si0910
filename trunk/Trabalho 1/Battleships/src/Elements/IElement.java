package Elements;

import java.awt.Point;
import java.util.Collection;

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
     * Devolve os pontos osupados pelo Element
     */
    Collection<Point> getPoints();
    /**
     * Representa a acção de acertar no Element
     */
    void hit();
    
    ElementType getType();

    ElementStatus getStatus();
}
