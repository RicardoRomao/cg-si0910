package Player;

import Engine.Settings.ElementType;
import Elements.IElement;
import java.awt.Point;

/**
 * Interface IPlayer, representação do contrato de um "jogador"
 * para com o jogo
 */
public interface IPlayer {

    /**
     * Promove o retorno pelo jogador de uma localização e
     * direcção para um elemento.
     *
     * @param elem Tipo do elemento.
     * @return Point[] Array de âncora e Direcção.
     */
    Point[] getElement(ElementType elem);

    /**
     * Permite ao jogador realizar uma jogada.
     *
     * @return Ponto escolhido pelo jogador.
     */
    Point play();

    /**
     * Permite o desenho do jogador no ecran.<br>
     * Ainda não suportado.
     */
    void draw();

    /**
     * Notifica o jogador acerca do IElement em que acertou na última jogada.
     * 
     * @param shot
     */
    void notifyHit(IElement shot);
}