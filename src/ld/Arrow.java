package ld;

import com.artemis.Component;
import com.artemis.Entity;
import java.util.List;
import java.util.ArrayList;

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