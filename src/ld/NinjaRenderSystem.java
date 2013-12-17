package ld;

import org.newdawn.slick.Image;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ImmutableBag;

public class NinjaRenderSystem extends EntitySystem
{
    @Mapper ComponentMapper<Position> pm;

    Image imageNinja;

    @SuppressWarnings("unchecked")
    public NinjaRenderSystem(Image imageNinja)
    {
        super(Aspect.getAspectForAll(Position.class, NinjaState.class));
        this.imageNinja = imageNinja;
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

            imageNinja.draw(x,y,LudumDareGame.SCALE);
        }
    }

    @Override
    protected boolean checkProcessing()
    {
        return true;
    }

}
