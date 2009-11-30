/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Engine;

import Player.IAPlayer;
import Player.IPlayer;
import Player.Player;
import Engine.Settings.GameStatus;

/**
 *
 * @author RNR
 */
public class Battleships {

    public static void main(String[] args){

        printTittle();

        Game _game = new Game();

        IPlayer humanPlayer = new Player();
        _game._players[0] = humanPlayer;
        IPlayer compPlayer = new IAPlayer();
        _game._players[1] = compPlayer;
        _game.init();
        while(_game._status != GameStatus.Ended){
            _game.play();
        }
        
    }

    private static void printTittle(){
        printLine();
        System.out.println("        -   BATTLESHIPS    -");
        printLine();
    }
    private static void printLine(){
        for(int i = 0; i < 35; i ++){
            System.out.print("-");
        }
        System.out.println();
    }

}
