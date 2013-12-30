package ld;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class GuardSystem extends EntityProcessingSystem
{
    @Mapper ComponentMapper<Position> pm;
    @Mapper ComponentMapper<Guard> gsm;

    @SuppressWarnings("unchecked")
    public GuardSystem()
    {
        super(Aspect.getAspectForAll(Position.class, Guard.class));
    }

    @Override
    protected void process(Entity e)
    {
        // Check if the guard has seen a ninja
        // If it has, shoot the ninja!

        // Check if the guard heard a noise
        // If it has, investigate the noise
    }

}