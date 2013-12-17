package ld;

import java.util.List;

public class Infiltrate implements NinjaActivity
{
    List<MapLocation> waypoints;
    MoveDirection facing;
    MapLocation currentLoc;
    int lastWaypoint;

    public Infiltrate(List<MapLocation> waypoints)
    {
        this.waypoints = waypoints;
        if(currentLoc == null)
        {
            currentLoc = waypoints.get(0);
            lastWaypoint = 0;
        }
        setNextWaypoint();
    }

    private void setNextWaypoint()
    {
        MapLocation l0 = waypoints.get(lastWaypoint);
        MapLocation l1 = waypoints.get((lastWaypoint+1)%waypoints.size());

        if(l0.xTile == l1.xTile) // are we going NORTH/SOUTH?
        {
            if(l1.yTile > l0.yTile)
                facing = MoveDirection.SOUTH;
            else
                facing = MoveDirection.NORTH;
        }
        else
        {
            if(l1.xTile > l0.xTile)
                facing = MoveDirection.EAST;
            else
                facing = MoveDirection.WEST;
        }
    }

    @Override
    public MoveDirection getMoveDirection()
    {
        return facing;
    }

    @Override
    public NinjaActivity newMapLocation(MapLocation l)
    {
        currentLoc = l;
        int nextWaypoint = (lastWaypoint+1)%waypoints.size();
        MapLocation destination = waypoints.get(nextWaypoint);

        if(destination.xTile == currentLoc.xTile &&
           destination.yTile == currentLoc.yTile)
        {
            if(nextWaypoint == waypoints.size()-1)
            {
                facing = MoveDirection.NONE;
                return new Assasinate();
            }
            else
            {
                lastWaypoint = (lastWaypoint+1)%waypoints.size();
                setNextWaypoint();
            }
        }

        return this;
    }

}
