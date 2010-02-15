package battleshipwarfare.PlayerPackage;

import battleshipwarfare.Boardpackage.IBoard;
import battleshipwarfare.Elementspackage.ElementType;
import battleshipwarfare.Elementspackage.IElement;
import battleshipwarfare.Boardpackage.Point;
import battleshipwarfare.Elementspackage.ElementStatus;

public interface IPlayer {
    public PlayerType getPlayerType();
    public String getName();
    public IElement getNewElement(ElementType type);
    public Point Play();
    public void notifyHit(ElementType type, ElementStatus status);
    public IBoard getBoard();
}
