package Engine;

import java.awt.Point;

public interface IConstants {

    static final int subSize = 1;
    static final int patrolSize = 2;
    static final int destroyerSize = 3;
    static final int battleshipSize = 4;
    static final int aircraftCarrierSize = 5;

    static final Point NORTH = new Point(0,1);
    static final Point SOUTH = new Point(0,-1);
    static final Point EAST = new Point(-1,0);
    static final Point WEST = new Point(1,0);

}
