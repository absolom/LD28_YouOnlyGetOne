package ld;

public class MapLocation
{
    public int xTile;
    public int yTile;

    public MapLocation(int x, int y)
    {
        xTile = x;
        yTile = y;
    }

    public MapLocation move(MoveDirection d)
    {
        switch(d)
        {
            case NORTH:
                return new MapLocation(xTile, yTile-1);
            case EAST:
                return new MapLocation(xTile+1, yTile);
            case SOUTH:
                return new MapLocation(xTile, yTile+1);
            case WEST:
                return new MapLocation(xTile-1, yTile);
            default:
            case NONE:
                return new MapLocation(xTile, yTile);
        }
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if(obj.getClass() != MapLocation.class)
            return false;
        
        MapLocation o = (MapLocation)obj;

        return o.xTile == xTile && o.yTile == yTile;
    }
}
