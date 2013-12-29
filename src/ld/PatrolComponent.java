package ld;

import com.artemis.Component;
import java.util.List;
import java.util.ArrayList;

public class PatrolComponent extends Component
{
    public int indexCurrentWaypoint;
    public MapLocation nextWaypoint;
    public int numWaypoints;
    public List<MapLocation> waypoints;

    public PatrolComponent(List<MapLocation> waypoints)
    {
        indexCurrentWaypoint = -1;
        numWaypoints = waypoints.size();
        nextWaypoint = waypoints.get(0);
        this.waypoints = waypoints;
    }
}