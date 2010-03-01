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

    private static double _ownBoardCellMargin = 0.035;
    private static double _ownCellSize = 0.2;

    private static double _initButtonStartY = 0.5;
    private static double _initButtonSizeY = 0.3;
    private static double _initButtonSizeX = 1;
    private static double _initButtonMargin = 0.035;

    public static double getOwnBoardCellMargin(){
        return _ownBoardCellMargin;
    }
    public static double getOwnCellSize(){
        return _ownCellSize;
    }
    public static double getOwnBoardStartPointX(){
        return getOwnBoardEndPointX()*(-1);
    }
    public static double getOwnBoardEndPointX(){
    //visto n cols terem (n-1) margens, adicionando as 2 exteriores -> (n+1) margens
        return (((GameRules.getCurrentRules().getCols() * _ownCellSize) +
                ((GameRules.getCurrentRules().getCols() + 1) * _ownBoardCellMargin))/2);
    }
    public static double getOwnBoardStartPointY(){
        return getOwnBoardEndPointY()*(-1);
    }
    public static double getOwnBoardEndPointY(){
        return (((GameRules.getCurrentRules().getRows() * _ownCellSize) +
                ((GameRules.getCurrentRules().getRows() + 1) * _ownBoardCellMargin))/2);
    }

    public static double getInitButtonStartY(){
        return _initButtonStartY;
    }
    public static double getInitButtonSizeY(){
        return _initButtonSizeY;
    }
    public static double getInitButtonSizeX(){
        return _initButtonSizeX;
    }
    public static double getInitButtonMargin(){
        return _initButtonMargin;
    }
}