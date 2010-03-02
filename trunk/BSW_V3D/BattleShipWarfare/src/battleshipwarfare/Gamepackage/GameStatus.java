package battleshipwarfare.Gamepackage;

/**
 * Enumerator that represents the possible Game state
 * @author RNR
 */
@SuppressWarnings("serial")
public enum GameStatus {
    /**
     * Initial state.<br>
     * Waiting for players to be added to the game
     */
    WAITING_FOR_PLAYER,
    /**
     * Waiting for boards to be build
     */
    WAITING_FOR_BOATS,
    /**
     * Ready to start
     */
    READY,
    /**
     * Game running
     */
    RUNNING,
    /**
     * Game over.
     */
    ENDED
}
