package ld.Systems;

import ld.Components.Arrow;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class ArrowSystem extends EntityProcessingSystem
{
    @Mapper ComponentMapper<Arrow> am;

    @SuppressWarnings("unchecked")
    public ArrowSystem()
    {
        super(Aspect.getAspectForAll(Arrow.class));
    }

    @Override
    protected void process(Entity e)
    {
        Arrow a = am.get(e);
        // animate the arrow
        // animate the blood
        // animate the death of the guard
        a.target.deleteFromWorld();
        e.deleteFromWorld();
    }

}