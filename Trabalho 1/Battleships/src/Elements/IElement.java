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
    boolean hit();

    Collection<Point> getPoints();

}
