package ld;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class GuardSystem extends EntityProcessingSystem
{
    @Mapper ComponentMapper<Position> pm;
    @Mapper ComponentMapper<GuardState> gsm;
    
    @SuppressWarnings("unchecked")
    public GuardSystem()
    {
        super(Aspect.getAspectForAll(Position.class, GuardState.class));
    }

    @Override
    protected void process(Entity e)
    {
        Position p = pm.get(e);
        GuardState s = gsm.get(e);

        if(s.timeSpentWaiting++ >= s.waitTimeBeforeMove)
        {
            GuardActivity activityCurr = s.activity.peekFirst();
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

            GuardActivity activityNew = activityCurr.newMapLocation(new MapLocation(p.xTile, p.yTile));
            if(activityNew == null)
                s.activity.removeFirst();
            else if(activityNew != activityCurr)
                s.activity.addFirst(activityNew);

            s.timeSpentWaiting = 0;
        }
    }

}