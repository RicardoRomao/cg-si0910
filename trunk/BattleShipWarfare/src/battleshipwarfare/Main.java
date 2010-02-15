/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package battleshipwarfare;

import battleshipwarfare.Gamepackage.Game;
import battleshipwarfare.PlayerPackage.*;

/**
 *
 * @author nuno.sousa
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Game game = new Game();
        IPlayer human = new HumanPlayer();
        game.addPlayer(human);
        IPlayer computer = new IAPlayer();
        game.addPlayer(computer);
        game.buildBoards();
        game.play();

        System.out.println("O vencedor é " + game.getWinner().getPlayer().getName());
    }

}
