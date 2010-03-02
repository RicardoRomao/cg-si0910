package battleshipwarfare.Elementspackage;

/**
 * Enumerator that represents the possible status of a element.
 * @author RNR
 */
@SuppressWarnings("serial")
public enum ElementStatus {
    /**
     * The element has no hitted points.
     */
    ALIVE,
    /**
     * The element has hitted points but stll has unhitted points.
     */
    HITTED,
    /**
     * All the element points has been hitted.
     */
    SUNK
}
