package ld.Systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

import ld.Components.*;
import ld.Map.*;

public class GhostSystem extends EntityProcessingSystem
{
    @Mapper ComponentMapper<Position> pm;
    @Mapper ComponentMapper<Ghost> gm;
    @Mapper ComponentMapper<Speed> sm;

    TileMap map;
    boolean shouldMove;
    boolean canMove;
    MoveDirection moveDir;

    @SuppressWarnings("unchecked")
    public GhostSystem()
    {
        super(Aspect.getAspectForAll(Position.class, Ghost.class, Speed.class));
        shouldMove = false;
        canMove = true;
    }

    public void setMap(TileMap map)
    {
        this.map = map;
    }

    public void movingTowards(MoveDirection dir)
    {
        shouldMove = true;
        moveDir = dir;
    }

    public void stopMoving()
    {
        shouldMove = false;
    }

    public void tapGuard()
    {

    }

    @Override
    protected void process(Entity e)
    {
        Position p = pm.get(e);
        Ghost g = gm.get(e);
        Speed s = sm.get(e);

        canMove = s.shouldMove ? true : canMove;

        // Move the ghost if requested
        if(shouldMove && canMove)
        {
            MapLocation l = new MapLocation(p.xTile, p.yTile);
            l = l.move(moveDir);
            if(map.isPassable(l))
            {
                p.xTile = l.xTile;
                p.yTile = l.yTile;
                canMove = false;
                s.timeSpentWaiting = 0;
            }
        }

        // Use a ghost ability if requested
    }
}