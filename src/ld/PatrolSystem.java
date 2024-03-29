package ld;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class PatrolSystem extends EntityProcessingSystem
{
    @Mapper ComponentMapper<Position> pm;
    @Mapper ComponentMapper<PatrolComponent> patm;
    @Mapper ComponentMapper<Speed> sm;
    @Mapper ComponentMapper<Path> pathm;

    BestFirstSearch bfs;

    @SuppressWarnings("unchecked")
    public PatrolSystem(BestFirstSearch bfs)
    {
        super(Aspect.getAspectForAll(Position.class, PatrolComponent.class));
        this.bfs = bfs;
    }

    @Override
    protected void process(Entity e)
    {
        // Monitor the current position and add Path components as we hit each waypoint
        Position p = pm.get(e);
        PatrolComponent pat = patm.get(e);

        if(p.xTile == pat.nextWaypoint.xTile &&
           p.yTile == pat.nextWaypoint.yTile)
        {
            // Get the next waypoint
            pat.indexCurrentWaypoint++;
            if(pat.indexCurrentWaypoint == pat.numWaypoints)
                pat.indexCurrentWaypoint = 0;
            pat.nextWaypoint = pat.waypoints.get((pat.indexCurrentWaypoint+1)%pat.numWaypoints);

            // Update the path component
            Path path = new Path(bfs.getPath(new MapLocation(p.xTile, p.yTile), pat.nextWaypoint));
            path.indexNextTile = 1;
            e.addComponent(path);
            e.changedInWorld();
        }
    }
}