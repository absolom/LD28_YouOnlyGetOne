package ld.Systems;

import ld.Components.Path;
import ld.Components.Position;
import ld.Components.Speed;
import ld.Map.MapLocation;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class MoveToSystem extends EntityProcessingSystem
{
    @Mapper ComponentMapper<Position> pm;
    @Mapper ComponentMapper<Path> pathm;
    @Mapper ComponentMapper<Speed> sm;

    @SuppressWarnings("unchecked")
    public MoveToSystem()
    {
        super(Aspect.getAspectForAll(Position.class, Path.class, Speed.class));
    }

    @Override
    protected void process(Entity e)
    {
        Position p = pm.get(e);
        Path path = pathm.get(e);
        Speed s = sm.get(e);

        if(s.shouldMove)
        {
            MapLocation l = path.tiles.get(path.indexNextTile);

            p.xTileLast = p.xTile;
            p.yTileLast = p.yTile;

            p.xTile = l.xTile;
            p.yTile = l.yTile;

            path.indexNextTile++;

            if(path.indexNextTile == path.length)
            {
                e.removeComponent(path);
                e.changedInWorld();

                // TODO: Notify the AI systems that the move is finished or have them poll
            }
        }
    }
}