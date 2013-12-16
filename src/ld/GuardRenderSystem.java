package ld;

import org.newdawn.slick.Image;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ImmutableBag;

public class GuardRenderSystem extends EntitySystem
{
    @Mapper ComponentMapper<Position> pm;
    
    Image imageGuard;

    @SuppressWarnings("unchecked")
    public GuardRenderSystem(Image imageGuard)
    {
        super(Aspect.getAspectForAll(Position.class, GuardState.class));
        this.imageGuard = imageGuard;
    }

    @Override
    protected void processEntities(ImmutableBag<Entity> entities)
    {
        for(int i = 0; i < entities.size(); i++)
        {
            Entity e = entities.get(i);
            Position p = pm.get(e);
            
            float d = 4f * LudumDareGame.SCALE;
            float x = p.xTile*d;
            float y = p.yTile*d;
            
            imageGuard.draw(x,y,LudumDareGame.SCALE);
        }
    }

    @Override
    protected boolean checkProcessing()
    {
        return true;
    }

}
