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

    public static final String HEAD_BACKG = "images/HeaderBackGround.png";
    public static final String FOOT_BACKG = "images/FooterBackGround.png";
    public static final int BTN_QUIT = 1;
    public static final String BTN_QUIT_IMG = "images/RB.png";
    public static final String BTN_QUIT_IMG_OVER = "images/RBO.png";
    public static final String BTN_QUIT_IMG_DOWN = "images/RBD.png";
    public static final int BTN_RESTART = 2;
    public static final String BTN_RESTART_IMG = "images/PB.png";
    public static final String BTN_RESTART_IMG_OVER = "images/PBO.png";
    public static final String BTN_RESTART_IMG_DOWN = "images/PBD.png";
    public static final int BTN_HELP = 3;
    public static final String BTN_HELP_IMG = "images/GB.png";
    public static final String BTN_HELP_IMG_OVER = "images/GBO.png";
    public static final String BTN_HELP_IMG_DOWN = "images/GBD.png";

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
}