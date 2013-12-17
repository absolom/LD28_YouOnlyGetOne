package ld;

import org.newdawn.slick.Image;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ImmutableBag;

public class LordRenderSystem extends EntitySystem
{
    @Mapper ComponentMapper<Position> pm;

    Image imageLord;

    @SuppressWarnings("unchecked")
    public LordRenderSystem(Image imageLord)
    {
        super(Aspect.getAspectForAll(Position.class, LordState.class));
        this.imageLord = imageLord;
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

            imageLord.draw(x,y,LudumDareGame.SCALE);
        }
    }

    @Override
    protected boolean checkProcessing()
    {
        return true;
    }

}
