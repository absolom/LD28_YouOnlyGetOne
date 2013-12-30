package ld.Map;

public enum MoveDirection
{
    NORTH,
    SOUTH,
    EAST,
    WEST,
    NONE;

    public static MoveDirection getDirectionTo(MapLocation start, MapLocation end)
    {
        double dX = end.xTile - start.xTile;
        double dY = end.yTile - start.yTile;

        if(Math.abs(dX) > Math.abs(dY))
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
