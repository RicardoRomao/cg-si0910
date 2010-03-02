package battleshipwarfare.Gamepackage;

/**
 * Enumerator that represents the possible player state
 * @author RNR
 */
public enum GamePlayerStatus {

    /**
     * Player is constructed but did not build his board
     */
    NOTREADY,
    /**
     * Player still has alive boats
     */
    ALIVE,
    /**
     * Player has no more boats
     */
    DEAD
}
