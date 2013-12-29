package ld;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class SpeedSystem extends EntityProcessingSystem
{
    @Mapper ComponentMapper<Speed> sm;

    @SuppressWarnings("unchecked")
    public SpeedSystem()
    {
        super(Aspect.getAspectForAll(Speed.class));
    }

    @Override
    protected void process(Entity e)
    {
        Speed s = sm.get(e);

        s.shouldMove = false;

        if(s.timeSpentWaiting++ >= s.timeToWait)
        {
            s.shouldMove = true;
            s.timeSpentWaiting = 0;
        }
        else
            s.shouldMove = false;
    }
}