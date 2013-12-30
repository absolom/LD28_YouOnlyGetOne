package ld.Components;

import ld.Map.MapLocation;
import ld.Map.MoveDirection;

import com.artemis.Component;
import com.artemis.Entity;

public class Arrow extends Component
{
    public MapLocation startLoc;
    public MoveDirection dir;
    public Entity target;

    public Arrow(MapLocation start, MoveDirection dir, Entity target)
    {
        startLoc = start;
        this.dir = dir;
        this.target = target;
    }
}