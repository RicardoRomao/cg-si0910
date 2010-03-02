package battleshipwarfare.PlayerPackage;

import battleshipwarfare.Elementspackage.ElementType;
import battleshipwarfare.Elementspackage.IElement;
import battleshipwarfare.Boardpackage.Point;
import battleshipwarfare.Elementspackage.ElementStatus;

/**
 * Definition of a player capabilities
 * @author RNR
 */
public interface IPlayer {
    /**
     * Gets the current player type
     * @return
     * Player Type
     */
    public PlayerType getPlayerType();
    /**
     * Gets the current player name
     * @return
     * Player Name
     */
    public String getName();
    /**
     * Returns a new IElement fo the specified type
     * @param type
     * The type of the element to be created
     * @return
     * A new element
     */
    public IElement getNewElement(ElementType type);
    /**
     * The player play
     * @return
     * A point representing the player play
     */
    public Point Play();
    /**
     * Notifies the current player of what he hitted and what was the resulting
     * status of the hitted element
     * @param type
     * The hitted element type
     * @param status
     * The hitted element resulting status
     */
    public void notifyHit(ElementType type, ElementStatus status);
    
}
