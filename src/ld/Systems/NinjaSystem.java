package ld.Systems;

import ld.Components.Ninja;
import ld.Components.Position;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class NinjaSystem extends EntityProcessingSystem
{
    @Mapper ComponentMapper<Position> pm;
    @Mapper ComponentMapper<Ninja> nm;

    @SuppressWarnings("unchecked")
    public NinjaSystem()
    {
        super(Aspect.getAspectForAll(Position.class, Ninja.class));
    }

    @Override
    protected void process(Entity e)
    {
    }

}