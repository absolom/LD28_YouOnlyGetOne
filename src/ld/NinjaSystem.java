package ld;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class NinjaSystem extends EntityProcessingSystem
{
    @Mapper ComponentMapper<Position> pm;
    @Mapper ComponentMapper<NinjaState> gsm;

    @SuppressWarnings("unchecked")
    public NinjaSystem()
    {
        super(Aspect.getAspectForAll(Position.class, NinjaState.class));
    }

    @Override
    protected void process(Entity e)
    {
        Position p = pm.get(e);
        NinjaState s = gsm.get(e);

        if(s.timeSpentWaiting++ >= s.waitTimeBeforeMove)
        {
            NinjaActivity activityCurr = s.activity.peekFirst();
            MoveDirection md = activityCurr.getMoveDirection();

            switch(md)
            {
                case NORTH:
                {
                    p.yTile -= 1;
                    break;
                }
                case SOUTH:
                {
                    p.yTile += 1;
                    break;
                }
                case EAST:
                {
                    p.xTile += 1;
                    break;
                }
                case WEST:
                {
                    p.xTile -= 1;
                    break;
                }
                case NONE:
                {
                    break;
                }
            }

            NinjaActivity activityNew = activityCurr.newMapLocation(new MapLocation(p.xTile, p.yTile));
            if(activityNew == null)
                s.activity.removeFirst();
            else if(activityNew != activityCurr)
                s.activity.addFirst(activityNew);

            s.timeSpentWaiting = 0;
        }
    }

}