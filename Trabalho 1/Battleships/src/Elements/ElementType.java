/*
 * Enumera os tipos de barcos e o seu tamanho
 * - Submarine (1 cano) 		- 4 uni.
 * - Patrol_Boat (2 canos)		- 3 uni.
 * - Destroyer (3 canos)		- 2 uni.
 * - Battleship (4 canos)		- 1 uni.
 * - Aircraft (5 canos em T)    	- 1 uni.

 */

package Elements;

/**
 *
 * @author RNR
 */
@SuppressWarnings("serial")
public enum ElementType {
    WATER, SUBMARINE , PATROL_BOAT, DESTROYER, BATTLESHIP, AIRCRAFT;
}
