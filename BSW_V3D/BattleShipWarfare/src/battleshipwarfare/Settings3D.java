/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package battleshipwarfare;

/**
 *
 * @author nuno.sousa
 */
public class Settings3D {

    private static double _boardCellMargin = 0.035;
    private static double _cellSize = 0.1;

    public static double getBoardCellMargin(){
        return _boardCellMargin;
    }
    public static double getCellSize(){
        return _cellSize;
    }
    public static double getOwnBoardStartPointX(){
        return getBoardEndPointX()*(-1);
    }
    public static double getBoardEndPointX(){
    //visto n cols terem (n-1) margens, adicionando as 2 exteriores -> (n+1) margens
        return (((GameRules.getCurrentRules().getCols() * _cellSize) +
                ((GameRules.getCurrentRules().getCols() + 1) * _boardCellMargin))/2);
    }
    public static double getBoardStartPointY(){
        return getBoardEndPointY()*(-1);
    }
    public static double getBoardEndPointY(){
        return (((GameRules.getCurrentRules().getRows() * _cellSize) +
                ((GameRules.getCurrentRules().getRows() + 1) * _boardCellMargin))/2);
    }
}