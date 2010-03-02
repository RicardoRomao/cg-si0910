package battleshipwarfare.Boardpackage;

import battleshipwarfare.Elementspackage.IElement;
import javax.media.j3d.Shape3D;

/**
 * Definition of a game board.
 * @author RNR
 */
public interface IBoard {
    /**
     * The default number of Rows.
     */
    final int DEFAULT_ROWS = 10;
    /**
     * The default number of Cols.
     */
    final int DEFAULT_COLS = 10;

    /**
     * Returns the end point of the board.
     * @return Point Representing the end of the board.
     */
    public Point getEndPoint();
    /**
     * Adds an element to the board.
     * @param elem IElement Element to be added
     * @param withAdjacent boolean that indicates if it is possible to add
     * elements adjacent to others or not.
     * @return boolean indicating if the element was succesfully added.
     */
    public boolean addElement(IElement elem, boolean withAdjacent);
    /**
     * Returns the element present in the specified Point.
     * @param p Point to be checked.
     * @return The element present in the specified point.
     */
    public IElement shoot(Point p);
    /**
     * Draws the board in the console.
     * @param own boolean indicating if the board is of current player or not.
     */
    public void drawInConsole(boolean own);
    /**
     * Determines if a point is in the board bounds or not.
     * @param p Point to be checked.
     * @return boolean indicating if the point is in bounds.
     */
    public boolean isInBounds(Point p);

    /**
     * Gets a Shape3D representing the current board.
     * @return Shape3D representing the current board.
     */
    public Shape3D getShape();
    /**
     * Gets a Shape3D representing the element is the specified point.
     * @param p Point to be checked.
     * @param own boolean indicating if the board is of current player or not.
     * @return Shape3D representing the element in <b>p</b> position.
     */
    public Shape3D getElementShape(Point p, boolean own);
}
