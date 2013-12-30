package ld.Systems;

import ld.Components.Arrow;
import ld.Components.Guard;
import ld.Components.Path;
import ld.Components.PatrolComponent;
import ld.Components.Position;
import ld.Components.Speed;
import ld.Components.Vision;
import ld.Map.MapLocation;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class GuardSystem extends EntityProcessingSystem
{
    @Mapper ComponentMapper<Position> pm;
    @Mapper ComponentMapper<Guard> gm;
    @Mapper ComponentMapper<Vision> vm;

    @SuppressWarnings("unchecked")
    public GuardSystem()
    {
        super(Aspect.getAspectForAll(Position.class, Guard.class, Vision.class));
    }

    @Override
    protected void process(Entity e)
    {
        Position p = pm.get(e);
        Vision v = vm.get(e);

        for(int i = 0; i < v.seenNinjas.size(); i++)
        {
            Entity ninja = v.seenNinjas.get(i);
            Entity arrow = world.createEntity();

            Arrow arrowC = new Arrow(new MapLocation(p.xTile, p.yTile),
                                     v.lookDir,
                                     ninja);
            arrow.addComponent(arrowC);
            arrow.addToWorld();

            ninja.removeComponent(PatrolComponent.class);
            ninja.removeComponent(Path.class);
            ninja.removeComponent(Speed.class);
            ninja.changedInWorld();
        }

        // Check if the guard has seen a ninja
        // If it has, shoot the ninja!

        // Entity arrow = world.createEntity();

        // Check if the guard heard a noise
        // If it has, investigate the noise
    }

}