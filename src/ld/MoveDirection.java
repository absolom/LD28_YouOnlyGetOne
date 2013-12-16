package ld;

public enum MoveDirection
{
    NORTH,
    SOUTH,
    EAST,
    WEST,
    NONE;

    static MoveDirection getDirectionTo(MapLocation start, MapLocation end)
    {
        double dX = end.xTile - start.xTile;
        double dY = end.yTile - start.yTile;

        if(dX > dY)
        {
            if(dX < 0)
                return WEST;
            else
                return EAST;
        }
        else
        {
            if(dY < 0)
                return NORTH;
            else
                return SOUTH;
        }
    }
}
