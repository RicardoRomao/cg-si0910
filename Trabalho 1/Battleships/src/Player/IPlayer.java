package Player;

import Elements.ElementType;
import Elements.IElement;
import java.awt.Point;

/**
 * Interface IPlayer, representação do contrato de um "jogador"
 * para com o jogo
 * @author RNR
 */
public interface IPlayer {
    Point[] getElement(ElementType elem);
    Point play();
    void draw();
    void notifyHit(IElement shot);
}