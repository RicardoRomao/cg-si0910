/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package battleshipwarfare;

import battleshipwarfare.Gamepackage.Game;
import battleshipwarfare.PlayerPackage.*;
import java.util.Scanner;

/**
 *
 * @author nuno.sousa
 */
public class Main {    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try{
        System.out.println("Welcome to BattleShipWarfare");
        Game game = new Game();
        System.out.println("What's your name?");
        Scanner s = new Scanner(System.in);
        String name = s.next();
        IPlayer human = new HumanPlayer(name);
        game.addPlayer(human);
        game.Start();
        System.out.println("O vencedor é " + game.getWinner().getPlayer().getName());
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

}
